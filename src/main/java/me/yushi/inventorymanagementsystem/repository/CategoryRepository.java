/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public class CategoryRepository implements ICategoryRepository {

    private Map<Integer, Category> categoryMap;
    private FileHandler<Category> categoryFileHandler;

    public CategoryRepository(FileHandler<Category> fileHandler) throws IOException {
        categoryFileHandler = fileHandler;
        this.categoryMap = categoryFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(c -> c.getCategoryID(), c -> c));
    }

    @Override
    public Category createCategory(Category newCategory) {
        categoryMap.put(newCategory.getCategoryID(), newCategory);
        return categoryMap.get(newCategory.getCategoryID());
    }

    @Override
    public Category readCategory(int categoryID) {
        return categoryMap.get(categoryID);
    }

    @Override
    public Category updateCategory(Category newCategory) {
        categoryMap.put(newCategory.getCategoryID(), newCategory);
        return categoryMap.get(newCategory.getCategoryID());

    }

    @Override
    public boolean deleteCategory(int categoryID) {
        categoryMap.remove(categoryID);
        return !categoryMap.containsKey(categoryID);
    }

    @Override
    public Map<Integer, Category> getAllCategorys() {
        return categoryMap;
    }

    @Override
    public void save() {
        categoryFileHandler.writeToFile(categoryMap);
    }

}
