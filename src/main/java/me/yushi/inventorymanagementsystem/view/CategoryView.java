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
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
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
    private int selectedRow = -1;

    public CategoryView(ICategoryRepository repository, WindowBasedTextGUI textGUI) {
        this.controller = new CategoryController(repository);
        this.textGUI = textGUI;
        setupUI();
        loadCategories();


    }

    private void setupUI() {
        categoryTable=new Table<>("Name","ID");
        this.addComponent(new Label("Category Management"));

        // Create a table to display categories
        this.addComponent(categoryTable);

        // Button to refresh the category list
        Button refreshButton = new Button("Refresh", () -> loadCategories());
        this.addComponent(refreshButton);

        // Button to add a new category
        Button addCategoryButton = new Button("Add Category", () -> addCategory());
        this.addComponent(addCategoryButton);
        // Button to update an existing category
        Button updateCategoryButton = new Button("Update Category", () -> updateCategory());
        this.addComponent(updateCategoryButton);
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

    private void updateCategory() {
        // Prompt the user to select a category from the table
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow < 0) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }

        // Get the selected category ID and name
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(0);
        String categoryName = categoryTable.getTableModel().getRow(selectedRow).get(1);

        // Prompt the user to input new values for the category
        String newCategoryName = TextInputDialog.showDialog(textGUI, "Update Category", "New Category Name:", categoryName);
        String newCategoryID = TextInputDialog.showDialog(textGUI, "Update Category", "New Category ID:", categoryID);

        if (newCategoryName != null && !newCategoryName.trim().isEmpty() && newCategoryID != null && !newCategoryID.trim().isEmpty()) {
            // Create a new CategoryDto with the updated values
            CategoryDto updatedCategory = new CategoryDto.Builder()
                    .categoryID(Integer.parseInt(newCategoryID))
                    .categoryName(newCategoryName)
                    .build();

            // Use the controller to update the category
            CategoryDto result = controller.updateCategory(updatedCategory);

            if (result != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Category updated successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update category.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
    }
}
