/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public interface ICategoryController{
    Category createCategory(Category newCategory);
    Category getCategoryByID( String categoryID);
    Category updateCategory(Category updatedCategory);
    boolean deleteCategory(String categoryID);
    List<Category> getAllCategorys();
}
