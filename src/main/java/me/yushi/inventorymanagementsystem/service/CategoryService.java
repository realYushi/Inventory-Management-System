/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;
import me.yushi.inventorymanagementsystem.model.ICategory;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService,IMapper<ICategoryDto, ICategory>{
    private ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository=categoryRepository;
    }

    
    @Override
    public ICategoryDto createCategory(ICategoryDto newCategoryDto) {
        ICategory category=toModel(newCategoryDto);
        return toDto(categoryRepository.createCategory(category));
        
    }

    @Override
    public ICategoryDto getCategoryByID(int categoryID) {
        ICategory category=categoryRepository.readCategory(categoryID);
        return toDto(category);
    }

    @Override
    public ICategoryDto updateCategory(ICategoryDto updatedCategoryDto) {
        ICategory newCategory=toModel(updatedCategoryDto);
        ICategory category=categoryRepository.updateCategory(newCategory);
        return toDto(category);
    }

    @Override
    public boolean deleteCategory(int categoryID) {
        return categoryRepository.deleteCategory(categoryID);
    }

    @Override
    public List<ICategoryDto> getAllCategorys() {
        List<ICategory> categorys=categoryRepository.getAllCategorys()
                .values().stream().collect(Collectors.toList());
        
        
    }

    @Override
    public ICategoryDto toDto(ICategory model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ICategory toModel(ICategoryDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
