/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author yushi
 */
@Entity
@Table(name = "Product")
public class Product implements IProduct {
    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private String categoryID;
    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private String supplierID;
    @Id
    private String productID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "unit", nullable = false)
    private String unit;
    @Column(name = "price", nullable = false)
    private double price;

    public Product() {
    }

    public Product(String productID, String name, String categoryID, String supplierID, int quantity, String unit,
            double price) {
        this.categoryID= categoryID;
        this.supplierID= supplierID;
        this.productID = (productID == null ? UUID.randomUUID().toString() : productID);
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
    }

    @Override
    public String getProductID() {
        return this.productID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCategoryID() {
        return this.categoryID;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getSupplierID() {
        return this.supplierID;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

}
