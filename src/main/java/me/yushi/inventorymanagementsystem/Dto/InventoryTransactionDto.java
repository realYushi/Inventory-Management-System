/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

import java.util.Date;

/**
 *
 * @author yushi
 */
public class InventoryTransactionDto implements IInventoryTransactionDto {

    private final String transactionID;
    private final String productID;
    private final int quantity;
    private final Date date;
    private final TransactionType transactionType;
    private final double price;

    private InventoryTransactionDto(Builder builder) {
        this.transactionID = builder.transactionID;
        this.productID = builder.productID;
        this.quantity = builder.quantity;
        this.date = builder.date;
        this.transactionType = builder.transactionType;
        this.price = builder.price;
    }

    public static class Builder {

        private String transactionID;
        private String productID;
        private int quantity;
        private Date date;
        private TransactionType transactionType;
        private double price;

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder transactionID(String transactionID) {
            this.transactionID = transactionID;
            return this;
        }

        public Builder productID(String productID) {
            this.productID = productID;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }
        public Builder price(double price){
            this.price=price;
            return this;
        }

        public InventoryTransactionDto build() {
            return new InventoryTransactionDto(this);
        }

    }

    @Override
    public String getTransactionID() {
        return this.transactionID;
    }

    @Override
    public String getProductID() {
        return this.productID;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

}
