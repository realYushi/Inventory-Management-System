/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.UUID;

/**
 *
 * @author yushi
 */
public class Supplier implements ISupplier {

    private String supplierID;
    private String supplierName;

    public Supplier(String supplierID,String supplierName) {
        this.supplierID=supplierID;
        this.supplierID = (this.supplierID==null?UUID.randomUUID().toString():supplierID);
        this.supplierName = supplierName;
    }

    @Override
    public String getSupplierID() {
        return this.supplierID;
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
