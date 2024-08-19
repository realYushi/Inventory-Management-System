/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public interface ICategoryRepository {
    Category createCategory(Category newCategory);
    Category readCategory(int categoryID);
    Category updateCategory(Category newCategory);
    boolean deleteCategory(int categoryID);

    Map<Integer,Category> getAllCategorys();
    void save();
}
