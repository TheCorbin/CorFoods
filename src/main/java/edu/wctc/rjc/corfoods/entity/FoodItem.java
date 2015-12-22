/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ryancorbin
 */
@Entity
@Table(name = "foodItem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoodItem.findAll", query = "SELECT f FROM FoodItem f"),
    @NamedQuery(name = "FoodItem.findByFoodId", query = "SELECT f FROM FoodItem f WHERE f.foodId = :foodId"),
    @NamedQuery(name = "FoodItem.findByFoodName", query = "SELECT f FROM FoodItem f WHERE f.foodName = :foodName"),
    @NamedQuery(name = "FoodItem.findByFoodDescription", query = "SELECT f FROM FoodItem f WHERE f.foodDescription = :foodDescription"),
    @NamedQuery(name = "FoodItem.findByFoodimageURL", query = "SELECT f FROM FoodItem f WHERE f.foodimageURL = :foodimageURL"),
    @NamedQuery(name = "FoodItem.findByFoodPrice", query = "SELECT f FROM FoodItem f WHERE f.foodPrice = :foodPrice"),
    @NamedQuery(name = "FoodItem.findByFoodInventory", query = "SELECT f FROM FoodItem f WHERE f.foodInventory = :foodInventory")})
public class FoodItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "food_id")
    private Integer foodId;
    @Size(max = 90)
    @Column(name = "food_name")
    private String foodName;
    @Size(max = 500)
    @Column(name = "food_description")
    private String foodDescription;
    @Size(max = 200)
    @Column(name = "food_image_URL")
    private String foodimageURL;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "food_price")
    private Float foodPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "food_inventory")
    private int foodInventory;

    public FoodItem() {
    }

    public FoodItem(Integer foodId) {
        this.foodId = foodId;
    }

    public FoodItem(Integer foodId, int foodInventory) {
        this.foodId = foodId;
        this.foodInventory = foodInventory;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodimageURL() {
        return foodimageURL;
    }

    public void setFoodimageURL(String foodimageURL) {
        this.foodimageURL = foodimageURL;
    }

    public Float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodInventory() {
        return foodInventory;
    }

    public void setFoodInventory(int foodInventory) {
        this.foodInventory = foodInventory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodId != null ? foodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoodItem)) {
            return false;
        }
        FoodItem other = (FoodItem) object;
        if ((this.foodId == null && other.foodId != null) || (this.foodId != null && !this.foodId.equals(other.foodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.rjc.corfoods.entity.FoodItem[ foodId=" + foodId + " ]";
    }
    
}
