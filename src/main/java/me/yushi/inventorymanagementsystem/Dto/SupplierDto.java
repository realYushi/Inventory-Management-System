/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

/**
 *
 * @author yushi
 */
public class SupplierDto implements ISupplierDto {

    private final int supplierID;
    private final String supplierName;

    private SupplierDto(Builder builder) {
        this.supplierID = builder.supplierID;
        this.supplierName = builder.supplierName;

    }

    public static class Builder {

        private int supplierID;
        private String supplierName;

        public Builder supplierID(int supplierID) {
            this.supplierID = supplierID;
            return this;
        }

        public Builder supplierName(String supplierName) {
            this.supplierName = supplierName;
            return this;
        }

        public SupplierDto build() {
            return new SupplierDto(this);
        }

    }

    @Override
    public int getSupplierID() {
        return this.supplierID;
    }

    @Override
    public String getSupplierName() {
        return this.supplierName;
    }

}
