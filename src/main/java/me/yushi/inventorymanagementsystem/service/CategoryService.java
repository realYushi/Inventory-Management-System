/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService {
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    // Business logic for CRUD operations on categories
    @Override
    public Category createCategory(Category newCategory) {
        Category category = TransactionUtil.executeTransaction(em -> {
            return repository.createCategory((newCategory), em);
        });
        if (category == null) {
            System.out.println("Failed to create category: " + newCategory.getCategoryName());
            return null;
        }
        return category;
    }

    @Override
    public Category getCategoryByID(String categoryID) {
        Category category = TransactionUtil.executeTransaction(em -> {
            return repository.readCategory(categoryID, em);
        });
        if (category == null) {
            System.out.println("No category found with ID: " + categoryID);
            return null;
        }
        return category;
    }

    @Override
    public Category updateCategory(Category updatedCategory) {
        Category newCategory = TransactionUtil.executeTransaction(em -> {
            return repository.updateCategory((updatedCategory), em);
        });
        if (newCategory == null) {
            System.out.println("Failed to update category: " + updatedCategory.getCategoryName());
            return null;
        }
        return newCategory;
    }

    @Override
    public boolean deleteCategory(String categoryID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteCategory(categoryID, em);
        });
    }

    @Override
    public List<Category> getAllCategorys() {
        List<Category> categories = TransactionUtil.executeTransaction(em -> {
            return repository.getAllCategories(em);
        });
        return categories;

    }

    @Override
    public boolean haveLinkedProduct(String categoryID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.haveLinkedProduct(categoryID, em);
        });
    }
}
