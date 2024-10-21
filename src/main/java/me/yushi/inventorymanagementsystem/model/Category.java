/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.UUID;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author yushi
 */
@Entity
@Table(name = "Category")
public class Category implements ICategory {
    @Id
    private String categoryID;
    @Column(name = "categoryName", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<IProduct> products = new ArrayList<>();
    public Category() {
    }

    public Category(String categoryName, String categoryID) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryID = (categoryID == null ? UUID.randomUUID().toString() : categoryID);
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
        this.categoryName = categoryName;
    }

    @Override
    public List<IProduct> getProducts() {
        return this.products;
    }
    

}
