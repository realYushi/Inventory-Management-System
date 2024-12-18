/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

/**
 *
 * @author yushi
 */
public interface IProduct {
    String getProductID();
    
    String getUnit();
    void setUnit(String unit);

    String getName();
    void setName(String name);

    int getQuantity();
    void setQuantity(int quantity);

    double getPrice();
    void setPrice(double price);

}
