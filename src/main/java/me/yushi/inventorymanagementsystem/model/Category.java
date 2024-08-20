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
public class Category implements ICategory {

    private String categoryID;
    private String categoryName;
    private String supplierID;

    public Category(String categoryName,String categoryID,String supplierID) {
        this.categoryName = categoryName;
        this.categoryID=categoryID;
        this.supplierID=supplierID;
        this.categoryID= (categoryID==null?UUID.randomUUID().toString():categoryID);
    }

    @Override
    public String getCategoryID() {
        return this.categoryID;
    }


    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public void setCategoryName(String categoryName) {
        this.categoryName=categoryName;
    }

    @Override
    public String getSupplierID() {
        return this.supplierID;
    }

    @Override
    public void setSupplierID(String suppierID) {
        this.supplierID=suppierID;
    }

}
