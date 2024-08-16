/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.ICategory;

/**
 *
 * @author yushi
 */
public interface ICategoryRepository {
    ICategory createCategory(ICategory newCategory);
    ICategory readCategory(int categoryID);
    ICategory updateCategory(ICategory newCategory);
    boolean deleteCategory(int categoryID);

    Map<Integer,ICategory> getAllCategorys();
    void save();
}
