/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;

/**
 *
 * @author yushi
 */
public interface ICategoryController{
    CategoryDto createCategory(CategoryDto newCategoryDto);
    CategoryDto getCategoryByID( String categoryID);
    CategoryDto updateCategory(CategoryDto updatedCategoryDto);
    boolean deleteCategory(String categoryID);

    List<CategoryDto> getAllCategorys();
}
