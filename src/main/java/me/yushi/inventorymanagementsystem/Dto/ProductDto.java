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
public class ProductDto implements IProductDto {

    private final int productID;
    private final String name;
    private final int categoryID;
    private final int quantity;
    private final String unit;
    private final double price;
    private final Date expirationDate;

    private ProductDto(Builder builder) {
        this.productID = builder.productID;
        this.name = builder.name;
        this.categoryID = builder.categoryID;
        this.quantity = builder.quantity;
        this.unit = builder.unit;
        this.price = builder.price;
        this.expirationDate = builder.expirationDate;
    }

    public static class Builder {

        private int productID;
        private String name;
        private int categoryID;
        private int quantity;
        private String unit;
        private double price;
        private Date expirationDate;

        public Builder productID(int productID) {
            this.productID = productID;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder categoryID(int categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder expirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }

    }

    @Override
    public int getProductID() {
        return this.productID;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCategoryID() {
        return this.categoryID;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

}
