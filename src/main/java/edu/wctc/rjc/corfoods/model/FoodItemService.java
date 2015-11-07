/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryancorbin
 */

public class FoodItemService {
    private FoodItemDAOStrategy dao;
    
    
    public FoodItemService(FoodItemDAOStrategy dao){
        this.dao = dao;
    }
    
    public final FoodItem getFoodItemById(String foodItemId) throws Exception {
        return dao.getOneFoodItem(foodItemId);
    }
    
    public final List<FoodItem> getAllFoodItems() throws Exception {
        return dao.getAllFoodItems();
    }
    
    public final void deleteFoodItemById(Object primaryKeyValue) throws Exception {
        dao.deleteFoodItem("foodItem", "food_id", primaryKeyValue);
    }
    
    public void updateFoodItem(List colValues) throws Exception{
        Integer id = Integer.parseInt(colValues.get(0).toString());
        String name = colValues.get(1).toString();
        String desc = colValues.get(2).toString();
        String URL = colValues.get(3).toString();
        Float price = Float.parseFloat(colValues.get(4).toString());
        
        FoodItem foodItem = new FoodItem(id, name , desc, URL, price, 0);
        dao.updateFoodItem(foodItem);
    }
    
    public void insertFoodItem(List colValues) throws Exception{
        String name = colValues.get(0).toString();
        String desc = colValues.get(1).toString();
        String URL = colValues.get(2).toString();
        Float price = Float.parseFloat(colValues.get(3).toString());
        Integer currentInventory = 0;
        
        FoodItem foodItem = new FoodItem(0, name , desc, URL, price, currentInventory);
        dao.insertFoodItem(foodItem);
    }
    
    public void updateInventory(String inventoryid, String inventory) throws Exception {
        dao.updateItemInventory(Integer.parseInt(inventoryid), Integer.parseInt(inventory));
    }
    
    
//    public static void main(String[] args) throws Exception {
//        FoodItemService foodItemService = new FoodItemService(new FoodItemDAO(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/corFoods", "root", "adminC"));
//            
//        List<FoodItem> foodItems = foodItemService.getAllFoodItems();
//            
//        for (FoodItem f: foodItems){
//            System.out.println(f);
//        }
//        
////        //Insert Test Code
//           ArrayList<String> colDescriptions = new ArrayList<>();
//        colDescriptions.add("product_id");
//        colDescriptions.add("product_name");
//        colDescriptions.add("product_description");
//        colDescriptions.add("image_URL");
//        colDescriptions.add("product_price");
//        ArrayList<Object> colValues = new ArrayList<>();
//        colValues.add(8);
//        colValues.add("Gremlin-Puffs");
//        colValues.add("Get Some Today!");
//        colValues.add("/Gremlins");
//        colValues.add((float)4.33);
//        
//        
//        System.out.println("");
//        foodItemService.updateFoodItem("foodItem", colDescriptions, colValues, "product_id", 8);
////        foodItemService.insertFoodItem("foodItem", colDescriptions, colValues);
//        
//        
////        //Delete Test Code
////        System.out.println("");
////        foodItemService.deleteFoodItemById(7);
//        
//        //Update Test Code
////        System.out.println("");
////        foodItemService.up
//        
//        
//        
//        foodItems = foodItemService.getAllFoodItems();
//            
//        for (FoodItem f: foodItems){
//            System.out.println(f);
//        } 
//    }

}
