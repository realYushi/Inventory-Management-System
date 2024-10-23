/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.service.CategoryService;

/**
 *
 * @author yushi
 */
public class CategoryController implements ICategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService ) {
        this.categoryService = categoryService;
    }

    @Override
    // Create a new category
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        if(newCategoryDto == null) {
            System.out.println("CategoryDto is null");
            return null;
        }
        return categoryService.createCategory(newCategoryDto);

    }

    @Override
    // Get a category by its ID
    public CategoryDto getCategoryByID(String categoryID) {
        if(categoryID == null) {
            System.out.println("CategoryID is null");
            return null;
        }
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    // Update a category
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        if (updatedCategoryDto == null) {
            System.out.println("CategoryDto is null");
            return null;
            
        }
        return categoryService.updateCategory(updatedCategoryDto);
    }

    @Override
    // Delete a category
    public boolean deleteCategory(String categoryID) {
        if(categoryID == null) {
            System.out.println("CategoryID is null");
            return false;
        }
        return categoryService.deleteCategory(categoryID);
    }

    @Override
    // Get all categories
    public List<CategoryDto> getAllCategorys() {
        if(categoryService == null) {
            System.out.println("CategoryService is null");
            return null;
        }
        return categoryService.getAllCategorys();
    }

}
