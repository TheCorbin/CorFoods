/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ryancorbin
 */
public class MySqlDbStrategy implements DbStrategy {
    private Connection conn;
    
    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws Exception{
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void openConnection(DataSource ds) throws Exception{
        conn = ds.getConnection();
    }
    
    @Override
    public void closeConnection() throws SQLException{
        conn.close();
    }
    
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException{
       String sql = "SELECT * FROM " + tableName;
       List<Map<String, Object>> recordList = new ArrayList<>();
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       ResultSetMetaData metaData = rs.getMetaData();
       int columnCount = metaData.getColumnCount();
       
       while(rs.next()){
           Map<String,Object> record = new HashMap<>();
           for (int i=1; i <=columnCount; i++){
               record.put(metaData.getColumnName(i), rs.getObject(i));
           }
           recordList.add(record);
       }
       return recordList;
    }
    
    @Override
    public Map<String, Object> findOneRecord(String tableName, String primaryKeyField, Object primaryKeyValue) throws SQLException, Exception{
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
	final Map record=new HashMap();
        
        try {
            stmt = conn.createStatement();
            String sql2;
            if(primaryKeyValue instanceof String){
                sql2 = "= '" + primaryKeyValue + "'";}
            else {
                sql2 = "=" + primaryKeyValue;
            }

        
            String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyField + sql2;
        
            rs = stmt.executeQuery(sql);
            metaData = rs.getMetaData();
            metaData.getColumnCount();
            final int fields=metaData.getColumnCount();
        
            if(rs.next() ) {
                for( int i=1; i <= fields; i++ ) {
                    record.put( metaData.getColumnName(i), rs.getObject(i) );
                    }
            }
        } catch (SQLException sqle){
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
        return record;
    }
    
    @Override
    public void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException, Exception{
        
        PreparedStatement pstmt = null;
        
        try{
            pstmt = buildDeleteStatement(conn, tableName, primaryKeyFieldName);
            
            if(primaryKeyFieldName != null){
                pstmt.setObject(1, primaryKeyValue);
            }
            
            pstmt.executeUpdate();
        } catch (SQLException sqle){
            throw sqle;
        } catch (Exception e){
            throw e;
        }
    }
    
    
    @Override
    public void updateById(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue) throws SQLException{
        PreparedStatement pstmt = null;
        
        try{
            pstmt = buildUpdateStatement(conn, tableName, colDescriptors, whereField);
            
            final Iterator i = colValues.iterator();
            int index = 1;
            boolean doWhereValueFlag = false;
            Object obj = null;
            
            while(i.hasNext() || doWhereValueFlag){
                if(!doWhereValueFlag){obj = i.next();}
                if (obj != null) pstmt.setObject(index++, obj);
              
                if (doWhereValueFlag){break;}
                
                if(!i.hasNext()){
                    doWhereValueFlag = true;
                    obj = whereValue;
                }
                
            }
            System.out.println(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException sqle){
            throw sqle;
        } catch (Exception e){
            throw e;
        }
    }
        
    @Override
    public void insertRecord(String tableName, List namesForFields, List valuesForFields) throws SQLException, Exception{
        PreparedStatement pstmt = null;
        
        
        try{
            pstmt = buildInsertStatement(conn, tableName, namesForFields);
            
            final Iterator i = valuesForFields.iterator();
            int index = 1;
            while (i.hasNext() ){
                final Object obj = i.next();
                pstmt.setObject(index++, obj);
            }
            
            pstmt.executeUpdate();
        } catch (SQLException sqle){
            throw sqle;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public void updateInventoryById(String tableName, String setRow, Integer inventory, String whereRow, Integer inventoryid) throws SQLException, Exception{
        String sql = new String("Update " + tableName + " Set " + setRow + "='" + inventory +"' WHERE " + whereRow + "='"+ inventoryid + "';");
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException sqle){
            throw sqle;
        } catch (Exception e) {
            throw e;
        } 
    }
     
       
     
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colDescriptors, String whereField) throws SQLException{
        StringBuffer sql = new StringBuffer("UPDATE ");
	(sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while(i.hasNext()){
            (sql.append( (String)i.next() )).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(",") ) );
        ((sql.append(" Where ")).append(whereField)).append(" = ? ") ;
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }
    
     private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors) throws SQLException{
        StringBuffer sql = new StringBuffer("INSERT INTO ");
	(sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while(i.hasNext()){
            (sql.append( (String)i.next() )).append(",");
        }

        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(",") ) + ") VALUES (" );
        for (Object colDescriptor : colDescriptors) {
            sql.append("?, ");
        }
        final String finalSQL=(sql.toString()).substring(0,(sql.toString()).lastIndexOf(", ")) + ")";
        return conn_loc.prepareStatement(finalSQL);
    }
    
    private PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String whereField) throws SQLException {
        StringBuffer sql = new StringBuffer("Delete From ");
        sql.append(tableName);
        
        if(whereField != null){
            sql.append(" WHERE ");
            (sql.append( whereField )).append(" = ?");
        }
        
        final String finalSQL=sql.toString();
        
        return conn_loc.prepareStatement(finalSQL);
    }
    
//    public static void main(String[] args) throws Exception {
//        MySqlDbStrategy db = new MySqlDbStrategy();
//        
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/corFoods", "root", "adminC");
//        
//        System.out.println("All Records Before action");
//        List<Map<String,Object>> records = db.findAllRecords("foodItem");
//        for(Map record : records) {
//            System.out.println(record);
//        }      
//        
//        ArrayList<String> colDescriptions = new ArrayList<>();
//        colDescriptions.add("product_name");
//        
//        ArrayList<String> colValues = new ArrayList<>();
//        colValues.add("Matzah Bread");
//        
//        db.updateById("foodItem", colDescriptions, colValues, "product_id", "4");
        
//        ArrayList<String> colDescriptions = new ArrayList<>();
//        colDescriptions.add("product_name");
//        colDescriptions.add("prouct_description");
//        colDescriptions.add("image_URL");
//        colDescriptions.add("product_price");
//        
//        ArrayList<String> colValues = new ArrayList<>();
//        colValues.add("Challah Bread");
//        colValues.add("The best Jewish bread in the history of Jewish bread");
//        colValues.add("/challahLoaf");
//        colValues.add("5.93");
        
//        db.insertRecord("foodItem", colDescriptions, colValues);
          
//        db.deleteById("foodItem", "product_id", 1);
//          Map<String, Object> temp = db.findOneRecord("author", "author_id", 7);
//        
//        System.out.println("\nAll author records after action:");
//        records = db.findAllRecords("foodItem");
//        for(Map record : records) {
//            System.out.println(record);
//        }
//            
        // DON'T FORGET TO CLOSE THE CONNECTION WHEN YOU ARE DONE!!!
//        db.closeConnection();
//    }  
}
