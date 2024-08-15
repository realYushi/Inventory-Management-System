/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

/**
 *
 * @author yushi
 */
public class Supplier implements ISupplier {

    private int supplierID;
    private String supplierName;

    public Supplier(int supplierID, String supplierName) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    @Override
    public int getSupplierID() {
        return this.supplierID;
    }

    @Override
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    @Override
    public String getSupplierName() {
        return this.supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}
