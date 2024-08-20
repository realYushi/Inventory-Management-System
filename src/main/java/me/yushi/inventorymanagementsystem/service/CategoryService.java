/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService,IMapper<CategoryDto, Category>{
    private ICategoryRepository repository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.repository=categoryRepository;
    }

    
    @Override
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        Category category=toModel(newCategoryDto);
        CategoryDto categoryDto=toDto(repository.createCategory(category));
        this.save();
        return categoryDto;
        
    }

    @Override
    public CategoryDto getCategoryByID(String categoryID) {
        Category category=repository.readCategory(categoryID);
        return toDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        Category newCategory=toModel(updatedCategoryDto);
        Category category=repository.updateCategory(newCategory);
        CategoryDto categoryDto=toDto(category);
        this.save();
        return categoryDto;
    }

    @Override
    public boolean deleteCategory(String categoryID) {
        boolean result =repository.deleteCategory(categoryID);
        this.save();
        return result; 
    }

    @Override
    public List<CategoryDto> getAllCategorys() {
        List<Category> categorys=repository.getAllCategorys()
                .values().stream().collect(Collectors.toList());
        return categorys.stream().map(category->this.toDto(category)).collect(Collectors.toList());
        
        
    }

    @Override
    public CategoryDto toDto(Category model) {
        return new CategoryDto.Builder()
                .categoryID(model.getCategoryID())
                .categoryName(model.getCategoryName())
                .build();
    }

    @Override
    public Category toModel(CategoryDto dto) {
        return new Category( dto.getCategoryName(),dto.getCategoryID(),dto.getSupplierID());
    }

    @Override
    public void save() {
        repository.save();
    }


    
    
}
