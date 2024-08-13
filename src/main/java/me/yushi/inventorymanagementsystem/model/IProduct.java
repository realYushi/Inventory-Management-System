/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;

/**
 *
 * @author yushi
 */
public interface IProduct {
    int getProductID();
    void setProductID(int producitonID);
    
    String getUnit();
    void setUnit(String unit);

    String getName();
    void setName(String name);

    int getCategoryID();
    void setCategoryID(int categoryID);

    int getQuantity();
    void setQuantity(int quantity);

    double getPrice();
    void setPrice(double price);

    Date getExpirationDate();
    void setExpirationDate(Date expirationDate);
}
