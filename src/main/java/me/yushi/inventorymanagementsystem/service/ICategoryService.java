/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;

/**
 *
 * @author yushi
 */
public interface ICategoryService {
    CategoryDto createCategory(CategoryDto newCategoryDto);
    CategoryDto getCategoryByID( String categoryID);
    CategoryDto updateCategory(CategoryDto updatedCategoryDto);
    boolean deleteCategory(String categoryID);
    void save();

    List<CategoryDto> getAllCategorys();
}
