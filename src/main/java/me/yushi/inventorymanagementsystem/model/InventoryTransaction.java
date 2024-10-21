/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author yushi
 */
@Entity
public class InventoryTransaction implements IInventoryTransaction {
 @Id
    private String transactionID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;
    private int quantity;
    private Date date;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private double price;

    public InventoryTransaction(String transactionID, Product product, int quantity, Date date, TransactionType transactionType, double price) {
        this.transactionID = (transactionID==null?UUID.randomUUID().toString():transactionID);
        this.product = product;
        this.quantity = quantity;
        this.date = date;
        this.transactionType =transactionType; 
        this.price=price;
    }

    @Override
    public String getTransactionID() {
        return this.transactionID;
    }


    @Override
    public String getProductID() {
        return this.product.getProductID();
    }


    @Override
    public int getQuantity() {
        return this.quantity;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Date getDate() {
        return this.date;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price=price;
    }

}
