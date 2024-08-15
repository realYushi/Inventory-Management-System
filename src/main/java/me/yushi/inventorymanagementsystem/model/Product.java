/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;

/**
 *
 * @author yushi
 */
public class Product implements IProduct {

    private int productID;
    private String name;
    private int categoryID;
    private int quantity;
    private String unit;
    private double price;
    private Date expirationDate;

    public Product(int productID,String name, int categoryID,int quantity, String unit, double price, Date expirationDate) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    @Override
    public int getProductID() {
        return this.productID;
    }

    @Override
    public void setProductID(int producitonID) {
        this.productID=producitonID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public int getCategoryID() {
        return this.categoryID;
    }

    @Override
    public void setCategoryID(int categoryID) {
        this.categoryID=categoryID;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price=price;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate=expirationDate;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit=unit;
    }

}
