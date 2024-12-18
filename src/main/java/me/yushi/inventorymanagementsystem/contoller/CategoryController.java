package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.service.CategoryService;

/**
 *
 * @author yushi
 */
public class CategoryController implements ICategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Business logic for CRUD operations
    @Override
    public Category createCategory(Category newCategory) {
        if (newCategory == null) {
            System.out.println("Category is null");
            return null;
        }
        return categoryService.createCategory(newCategory);

    }

    @Override
    public Category getCategoryByID(String categoryID) {
        if (categoryID == null) {
            System.out.println("CategoryID is null");
            return null;
        }
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    public Category updateCategory(Category updatedCategory) {
        if (updatedCategory == null) {
            System.out.println("Category is null");
            return null;
        }
        return categoryService.updateCategory(updatedCategory);
    }

    @Override
    public boolean deleteCategory(String categoryID) {
        if (categoryID == null) {
            System.out.println("CategoryID is null");
            return false;
        }
        return categoryService.deleteCategory(categoryID);
    }

    @Override
    public List<Category> getAllCategorys() {
        if (categoryService == null) {
            System.out.println("CategoryService is null");
            return null;
        }
        return categoryService.getAllCategorys();
    }

    @Override
    public boolean haveLinkedProduct(String categoryID) {
        return categoryService.haveLinkedProduct(categoryID);
    }

}
