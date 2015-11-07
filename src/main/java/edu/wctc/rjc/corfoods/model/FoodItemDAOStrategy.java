/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ryancorbin
 */
public interface FoodItemDAOStrategy {

    void deleteFoodItem(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws Exception;

    List<FoodItem> getAllFoodItems() throws Exception;

    FoodItem getOneFoodItem(String productId) throws Exception;

    void insertFoodItem(FoodItem food) throws Exception;

    void updateFoodItem(FoodItem food) throws Exception;

    public void updateItemInventory(Integer inventoryid, Integer inventory) throws SQLException, Exception;
   
}
