/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.List;

/**
 *
 * @author yushi
 */
public interface ICategory {
    String getCategoryID();
    String getCategoryName();
    void setCategoryName(String categoryName);
    List<IProduct> getProducts();
}
