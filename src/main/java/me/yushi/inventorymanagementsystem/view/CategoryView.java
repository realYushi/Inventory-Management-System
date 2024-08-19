/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.table.Table;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;
import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.ICategoryController;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;

/**
 *
 * @author yushi
 */
public class CategoryView extends Panel {

    private ICategoryController controller;
    private Table<String> categoryTable;
    private WindowBasedTextGUI textGUI;
    

    public CategoryView(ICategoryRepository repository, WindowBasedTextGUI textGUI) {
        this.controller = new CategoryController(repository);
        this.textGUI = textGUI;
        setupUI();
        loadCategories();

    }

    private void setupUI() {
        this.addComponent(new Label("Category Management"));

        // Create a table to display categories
        categoryTable = new Table<>("ID", "Name");
        this.addComponent(categoryTable);

        // Button to refresh the category list
        Button refreshButton = new Button("Refresh", () -> loadCategories());
        this.addComponent(refreshButton);

        // Button to add a new category
        Button addCategoryButton = new Button("Add Category", () -> addCategory());
        this.addComponent(addCategoryButton);
    }

    private void loadCategories() {
        categoryTable.getTableModel().clear();
        List<CategoryDto> categories = controller.getAllCategorys();
        for (ICategoryDto category : categories) {
            categoryTable.getTableModel().addRow(
                    String.valueOf(category.getCategoryID()),
                    category.getCategoryName()
            );
        }
    }

    private void addCategory() {
        String categoryName = TextInputDialog.showDialog(textGUI, "Add Category", "Category Name:", "");
        String categoryID = TextInputDialog.showDialog(textGUI, "Add Category", "Category ID:", "");
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            CategoryDto category = new CategoryDto.Builder().categoryName(categoryName).categoryID(Integer.parseInt(categoryID)).build();
            ICategoryDto newCategory = controller.createCategory(category);
            
            if (newCategory != null) {
                loadCategories();
            }
            MessageDialog.showMessageDialog(textGUI, "Success", "Category added successfully!",
                    com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add category.",
                    com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK);
        }
    }
}
