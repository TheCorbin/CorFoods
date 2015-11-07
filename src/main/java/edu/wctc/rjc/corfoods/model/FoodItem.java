/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.model;

import java.util.Objects;

/**
 *
 * @author ryancorbin
 */
public class FoodItem {
    private Integer productID;
    private String productName;
    private String productDescription;
    private String imageURL;
    private Float productPrice;
    private Integer productInventory;

    public FoodItem() {
    }

    public FoodItem(Integer productID) {
        this.productID = productID;
    }

    public FoodItem(Integer productID, String productName, String productDescription, String imageURL, Float productPrice, Integer productInventory) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.imageURL = imageURL;
        this.productPrice = productPrice;
        this.productInventory = productInventory;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FoodItem other = (FoodItem) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "foodItem{" + "productID=" + productID + ", productName=" + productName + ", productDescription=" + productDescription + ", imageURL=" + imageURL + ", productPrice=" + productPrice + '}';
    }
    
}
