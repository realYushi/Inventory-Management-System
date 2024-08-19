/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import me.yushi.inventorymanagementsystem.service.ICategoryService;

/**
 *
 * @author yushi
 */
public class CategoryController implements ICategoryController{
    private ICategoryService categoryService;

    public CategoryController(ICategoryRepository repository) {
        this.categoryService=new CategoryService(repository);
    }
    

    @Override
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);

    }

    @Override
    public CategoryDto getCategoryByID(int categoryID) {
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        return categoryService.updateCategory(updatedCategoryDto);
    }

    @Override
    public boolean deleteCategory(int categoryID) {
        return categoryService.deleteCategory(categoryID);
    }

    @Override
    public List<CategoryDto> getAllCategorys() {
        return categoryService.getAllCategorys();
    }
    
}
