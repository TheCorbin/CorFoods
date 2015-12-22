/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ryancorbin
 */
@Entity
@Table(name = "orderItems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o"),
    @NamedQuery(name = "OrderItem.findByOrderItemId", query = "SELECT o FROM OrderItem o WHERE o.orderItemId = :orderItemId"),
    @NamedQuery(name = "OrderItem.findByOrderId", query = "SELECT o FROM OrderItem o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderItem.findByFoodName", query = "SELECT o FROM OrderItem o WHERE o.foodName = :foodName"),
    @NamedQuery(name = "OrderItem.findByFoodItemAmount", query = "SELECT o FROM OrderItem o WHERE o.foodItemAmount = :foodItemAmount")})
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OrderItemId")
    private Integer orderItemId;
    
    
    
    @Size(max = 90)
    @Column(name = "food_name")
    private String foodName;
    
    @JoinColumn(name = "OrderId", referencedColumnName = "OrderId")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Order orderId;
    
    @Column(name = "foodItemAmount")
    private Integer foodItemAmount;

    public OrderItem() {
    }

    public OrderItem(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderItem(Integer orderItemId, Order orderId) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodItemAmount() {
        return foodItemAmount;
    }

    public void setFoodItemAmount(Integer foodItemAmount) {
        this.foodItemAmount = foodItemAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderItemId != null ? orderItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) object;
        if ((this.foodName == null && other.foodName != null) || (this.foodName != null && !this.foodName.equals(other.foodName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.wctc.rjc.corfoods.entity.OrderItem[ orderItemId=" + orderItemId + " ]";
    }
    
}
