/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public interface ICategoryService {
    Category createCategory(Category newCategory);
    Category getCategoryByID( String categoryID);
    Category updateCategory(Category updatedCategory);
    boolean deleteCategory(String categoryID);
    List<Category> getAllCategorys();
    boolean haveLinkedProduct(String categoryID);
}
