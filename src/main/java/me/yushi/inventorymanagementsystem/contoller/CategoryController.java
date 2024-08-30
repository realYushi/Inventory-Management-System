/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import me.yushi.inventorymanagementsystem.service.SupplierService;

/**
 *
 * @author yushi
 */
public class CategoryController implements ICategoryController {
    private CategoryService categoryService;
    private SupplierService supplierService;

    public CategoryController(IUnitOfWork unitOfWork) {
        this.categoryService = new CategoryService(unitOfWork);
        this.supplierService = new SupplierService(unitOfWork);
    }

    @Override
    // Create a new category
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);

    }

    @Override
    // Get a category by its ID
    public CategoryDto getCategoryByID(String categoryID) {
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    // Update a category
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        return categoryService.updateCategory(updatedCategoryDto);
    }

    @Override
    // Delete a category
    public boolean deleteCategory(String categoryID) {
        return categoryService.deleteCategory(categoryID);
    }

    @Override
    // Get all categories
    public List<CategoryDto> getAllCategorys() {
        return categoryService.getAllCategorys();
    }

    // Get a supplier by its ID
    @Override
    public SupplierDto getSupplier(String supplierID) {
        return supplierService.getSupplierByID(supplierID);

    }

    // Create a new supplier
    @Override
    public List<SupplierDto> getAllSupplier() {
        return supplierService.getAllSuppliers();
    }

}
