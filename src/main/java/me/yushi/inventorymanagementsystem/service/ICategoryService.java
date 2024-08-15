/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;

/**
 *
 * @author yushi
 */
public interface ICategoryService {
    ICategoryDto createCategory(ICategoryDto newCategoryDto);
    ICategoryDto getCategoryByID( int categoryID);
    ICategoryDto updateCategory(ICategoryDto updatedCategoryDto);
    boolean deleteCategory(int categoryID);

    List<ICategoryDto> getAllCategorys();
}
