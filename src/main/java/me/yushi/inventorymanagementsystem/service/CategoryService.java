/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

/**
 *
 * @author yushi
 */
public class CategoryService implements ICategoryService, IMapper<CategoryDto, Category> {
    private CategoryRepository repository;

    public CategoryService(IUnitOfWork unitOfWork) {
        this.repository = unitOfWork.getCategoryRepository();
    }

    @Override
    // Create a new category, save it to the repository, and return the created
    // category
    public CategoryDto createCategory(CategoryDto newCategoryDto) {
        Category category = toModel(newCategoryDto);
        CategoryDto categoryDto = toDto(repository.createCategory(category));
        this.save();
        return categoryDto;

    }

    @Override
    // Get a category by its ID
    public CategoryDto getCategoryByID(String categoryID) {
        if (repository.readCategory(categoryID) == null) {
            System.out.print("No category found with ID: " + categoryID);
            return null;
        }
        Category category = repository.readCategory(categoryID);
        return toDto(category);
    }

    @Override
    // Update a category, save it to the repository, and return the updated category
    public CategoryDto updateCategory(CategoryDto updatedCategoryDto) {
        Category newCategory = toModel(updatedCategoryDto);
        // Check if the category exists
        if (repository.readCategory(newCategory.getCategoryID()) == null) {
            System.out.print("No category found with ID: " + newCategory.getCategoryID());
            return null;
        }
        Category category = repository.updateCategory(newCategory);
        CategoryDto categoryDto = toDto(category);
        this.save();
        return categoryDto;
    }

    @Override
    // Delete a category by its ID
    public boolean deleteCategory(String categoryID) {
        boolean result = repository.deleteCategory(categoryID);
        this.save();
        return result;
    }

    @Override
    // Get all categories, convert them to DTOs, and return the list of DTOs
    public List<CategoryDto> getAllCategorys() {
        // Get all categories from the repository, convert them to a list, and return
        List<Category> categorys = repository.getAllCategorys()
                .values().stream().collect(Collectors.toList());
        return categorys.stream().map(category -> this.toDto(category)).collect(Collectors.toList());

    }

    @Override
    public CategoryDto toDto(Category model) {
        // Convert a category model to a category DTO, and return the DTO
        return new CategoryDto.Builder()
                .supplierID(model.getSupplierID())
                .categoryID(model.getCategoryID())
                .categoryName(model.getCategoryName())
                .build();
    }

    @Override
    public Category toModel(CategoryDto dto) {
        // Convert a category DTO to a category model, and return the model
        return new Category(dto.getCategoryName(), dto.getCategoryID(), dto.getSupplierID());
    }

    @Override
    public void save() {
        repository.save();
    }

}
