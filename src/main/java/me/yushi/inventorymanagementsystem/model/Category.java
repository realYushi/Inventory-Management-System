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

    public Category(String categoryName,String categoryID) {
        this.categoryName = categoryName;
        this.categoryID=categoryID;
        categoryID= (categoryID==""?UUID.randomUUID().toString():categoryID);
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

}
