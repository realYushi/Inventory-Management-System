/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author yushi
 */
public class InventoryTransaction implements IInventoryTransaction {

    private String transactionID;
    private String productID;
    private int quantity;
    private Date date;
    private TransactionType transactionType;
    private double price;

    public InventoryTransaction(String transactionID, String productID, int quantity, Date date, TransactionType transactionType, double price) {
        this.transactionID=transactionID;
        this.transactionID = (transactionID==null?UUID.randomUUID().toString():transactionID);
        this.productID = productID;
        this.quantity = quantity;
        this.date = date;
        this.transactionType =transactionType; 
        this.price=price;
    }

    @Override
    public String getTransactionID() {
        return this.transactionID;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public String getProductID() {
        return this.productID;
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
