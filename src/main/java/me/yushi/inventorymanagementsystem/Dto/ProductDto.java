/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

/**
 *
 * @author yushi
 */
public class ProductDto implements IProductDto {

    private final String productID;
    private final String name;
    private final String categoryID;
    private final String supplierID;
    private final int quantity;
    private final String unit;
    private final double price;

    private ProductDto(Builder builder) {
        this.productID = builder.productID;
        this.name = builder.name;
        this.categoryID = builder.categoryID;
        this.quantity = builder.quantity;
        this.unit = builder.unit;
        this.price = builder.price;
        this.supplierID = builder.supplierID;
    }

    public static class Builder {

        private String productID;
        private String name;
        private String categoryID;
        private int quantity;
        private String unit;
        private double price;
        private String supplierID;

        public Builder productID(String productID) {
            this.productID = productID;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder categoryID(String categoryID) {
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
        public Builder supplierID(String supplierID) {
            this.supplierID = supplierID;
            return this;
        }


        public ProductDto build() {
            return new ProductDto(this);
        }

    }

    @Override
    public String getProductID() {
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
    public String getCategoryID() {
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
    public String getSupplierID() {
        return this.supplierID;
    }


}
