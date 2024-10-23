/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService, IMapper<CategoryDto, Category> {
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    // Create a new category, save it to the repository, and return the created category
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        Category category= TransactionUtil.executeTransaction(em -> {
            return repository.createCategory(toModel(newCategoryDto), em);
        });
        if (category == null) {
            System.out.println("Failed to create category: " + newCategoryDto.getCategoryName());
            return null;
        }
        return newCategoryDto;
    }

    @Override
    // Get a category by its ID
    public CategoryDto getCategoryByID(String categoryID) {
        Category category = TransactionUtil.executeTransaction(em -> {
            return repository.readCategory(categoryID, em);
        });
        if(category == null) {
            System.out.println("No category found with ID: " + categoryID);
            return null;
        }
        return toDto(category);
    }

    @Override
    // Update a category, save it to the repository, and return the updated category
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        Category newCategory= TransactionUtil.executeTransaction(em -> {
            return repository.updateCategory(toModel(updatedCategoryDto), em);
        });
        if (newCategory== null) {
            System.out.println("Failed to update category: " + updatedCategoryDto.getCategoryName());
            return null;
        }
        return toDto(newCategory);
    }

    @Override
    // Delete a category by its ID
    public boolean deleteCategory(String categoryID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteCategory(categoryID, em);
        });
    }

    @Override
    public List<CategoryDto> getAllCategorys() {
        List<Category> categories = TransactionUtil.executeTransaction(em -> {
            return repository.getAllCategories(em);
        });
        List<CategoryDto> categoryDtos = categories.stream().map(this::toDto).collect(Collectors.toList());
        return categoryDtos;

    }

    @Override
    public CategoryDto toDto(Category model) {
        // Convert a category model to a category DTO, and return the DTO
        return new CategoryDto.Builder()
                .categoryID(model.getCategoryID())
                .categoryName(model.getCategoryName())
                .build();
    }

    @Override
    public Category toModel(CategoryDto dto) {
        // Convert a category DTO to a category model, and return the model
        return new Category(dto.getCategoryName(), dto.getCategoryID());
    }


}
