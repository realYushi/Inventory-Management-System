/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public interface ICategoryRepository {
    Category createCategory(Category newCategory,EntityManager em);
    Category readCategory(String categoryID,EntityManager em);
    Category updateCategory(Category newCategory,EntityManager em);
    boolean deleteCategory(String categoryID,EntityManager em);
    List<Category> getAllCategories(EntityManager em);
    boolean haveLinkedProduct(String categoryID,EntityManager em);
}
