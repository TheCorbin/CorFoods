/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ryancorbin
 */
public class FoodItemDAO implements FoodItemDAOStrategy{
    private DbStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    public FoodItemDAO(DbStrategy db, String driverClass, String url, String userName, String password){
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    
   @Override
   public final List<FoodItem> getAllFoodItems() throws Exception{
        List<FoodItem> foodItemList = new ArrayList();
        
        db.openConnection(driverClass, url, userName, password);
        
        List<Map<String,Object>> rawList = db.findAllRecords("foodItem");
        
        for(Map<String, Object> rawRec : rawList){
            Object obj = rawRec.get("food_id");
            Integer productId = obj == null ? 0 : Integer.parseInt(obj.toString());
            
            obj = rawRec.get("food_name");
            String productName = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_description");
            String productDescription = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_image_URL");
            String imageURL = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_price");
            Float productPrice = obj == null ? 0 : Float.parseFloat(obj.toString());
            
            obj = rawRec.get("food_inventory");
            Integer productInventory = obj == null ? 0 : Integer.parseInt(obj.toString());
            
            FoodItem food = new FoodItem(productId, productName, productDescription, imageURL, productPrice, productInventory);
            foodItemList.add(food);
        }
        db.closeConnection();
        return foodItemList;
    }
        
     
    @Override
   public final FoodItem getOneFoodItem(String productId) throws Exception{
       db.openConnection(driverClass, url, userName, password);
       Map<String, Object> rawRec = db.findOneRecord("foodItem", "food_id", productId);
       
            Object obj = rawRec.get("food_id");
            Integer productID = obj == null ? 0 : Integer.parseInt(obj.toString());
            
            obj = rawRec.get("food_name");
            String productName = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_description");
            String productDescription = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_image_URL");
            String imageURL = obj == null ? "" : obj.toString();
            
            obj = rawRec.get("food_price");
            Float productPrice = obj == null ? 0 : Float.parseFloat(obj.toString());
            
            obj = rawRec.get("food_inventory");
            Integer productInventory = obj == null ? 0 : Integer.parseInt(obj.toString());
       
       FoodItem food = new FoodItem(productID, productName, productDescription, imageURL, productPrice, productInventory);
       
       db.closeConnection();
       return food;    
   }  
   
    @Override
   public final void deleteFoodItem(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws Exception{
       db.openConnection(driverClass, url, userName, password);
       db.deleteById(tableName, primaryKeyFieldName, primaryKeyValue);
       db.closeConnection();
   }  
   
    @Override
    public final void updateFoodItem(FoodItem food) throws Exception{
   
       db.openConnection(driverClass, url, userName, password);
      
        List colValues = new ArrayList();
       Integer productId = food.getProductID();
       colValues.add(food.getProductID()); 
       colValues.add(food.getProductName());
       colValues.add(food.getProductDescription());
       colValues.add(food.getImageURL());
       colValues.add(food.getProductPrice());
       
       db.updateById("foodItem", Arrays.asList("food_id", "food_name","food_description", "food_image_URL", "food_price"), colValues, "food_id",  productId);
       db.closeConnection();
   }
   
   
    @Override
    public final void insertFoodItem(FoodItem food) throws Exception{
       db.openConnection(driverClass, url, userName, password);
       
       List colValues = new ArrayList();
       
       colValues.add(food.getProductName());
       colValues.add(food.getProductDescription());
       colValues.add(food.getImageURL());
       colValues.add(food.getProductPrice());
       
       db.insertRecord("foodItem", Arrays.asList("food_name","food_description", "food_image_URL", "food_price", "food_inventory"), colValues);
       db.closeConnection();
   }  
    
    
   @Override
    public void updateItemInventory(Integer inventoryid, Integer inventory) throws SQLException, Exception{
        db.openConnection(driverClass, url, userName, password);
        db.updateInventoryById("foodItem", "food_inventory", inventory, "food_id", inventoryid);
        db.closeConnection();
        
    }
}
