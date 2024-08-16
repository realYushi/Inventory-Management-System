/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.ICategory;

/**
 *
 * @author yushi
 */
public class CategoryRepository implements ICategoryRepository{
    private Map<Integer, ICategory> categoryMap;
    private IFileHandler<ICategory> categoryFileHandler;

    public CategoryRepository(String filePath) throws IOException {
        categoryFileHandler = new FileHandler<>(ICategory.class, filePath);
        this.categoryMap = categoryFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(c -> c.getCategoryID(), c -> c));
    }

    @Override
    public ICategory createCategory(ICategory newCategory) {
        categoryMap.put(newCategory.getCategoryID(), newCategory);
        return categoryMap.get(newCategory.getCategoryID());
    }

    @Override
    public ICategory readCategory(int categoryID) {
        return categoryMap.get(categoryID);
    }

    @Override
    public ICategory updateCategory(ICategory newCategory) {
        categoryMap.put(newCategory.getCategoryID(), newCategory);
        return categoryMap.get(newCategory.getCategoryID());
        
    }

    @Override
    public boolean deleteCategory(int categoryID) {
        categoryMap.remove(categoryID);
        return !categoryMap.containsKey(categoryID);
    }

    @Override
    public Map<Integer, ICategory> getAllCategorys() {
        return categoryMap;
    }

    @Override
    public void save() {
        categoryFileHandler.writeToFile(categoryMap);
    }
    
}
