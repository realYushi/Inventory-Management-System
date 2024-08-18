/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.ICategory;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService,IMapper<ICategoryDto, ICategory>{
    private ICategoryRepository repository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.repository=categoryRepository;
    }

    
    @Override
    public ICategoryDto createCategory(ICategoryDto newCategoryDto) {
        ICategory category=toModel(newCategoryDto);
        return toDto(repository.createCategory(category));
        
    }

    @Override
    public ICategoryDto getCategoryByID(int categoryID) {
        ICategory category=repository.readCategory(categoryID);
        return toDto(category);
    }

    @Override
    public ICategoryDto updateCategory(ICategoryDto updatedCategoryDto) {
        ICategory newCategory=toModel(updatedCategoryDto);
        ICategory category=repository.updateCategory(newCategory);
        return toDto(category);
    }

    @Override
    public boolean deleteCategory(int categoryID) {
        return repository.deleteCategory(categoryID);
    }

    @Override
    public List<ICategoryDto> getAllCategorys() {
        List<ICategory> categorys=repository.getAllCategorys()
                .values().stream().collect(Collectors.toList());
        return categorys.stream().map(category->this.toDto(category)).collect(Collectors.toList());
        
        
    }

    @Override
    public ICategoryDto toDto(ICategory model) {
        return new CategoryDto.Builder()
                .categoryID(model.getCategoryID())
                .categoryName(model.getCategoryName())
                .build();
    }

    @Override
    public ICategory toModel(ICategoryDto dto) {
        return new Category(dto.getCategoryID(), dto.getCategoryName());
    }
    
    
}
