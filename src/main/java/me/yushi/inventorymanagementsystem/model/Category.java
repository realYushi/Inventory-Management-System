/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;

/**
 *
 * @author yushi
 */
public class Category implements ICategory {

    private int categoryID;
    private String categoryName;

    public Category(ICategoryDto categoryDto) {
        this.categoryID = categoryDto.getCategoryID();
        this.categoryName = categoryDto.getCategoryName();
    }

    @Override
    public int getCategoryID() {
        return this.categoryID;
    }

    @Override
    public void setCategoryID(int categoryID) {
        this.categoryID=categoryID;
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
