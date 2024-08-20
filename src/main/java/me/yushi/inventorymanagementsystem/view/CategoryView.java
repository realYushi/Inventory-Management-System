/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
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
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class CategoryView extends Panel {

    private ICategoryController controller;
    private Table<String> categoryTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public CategoryView(CategoryRepository categoryRepository, SupplierRepository supplierRepository, WindowBasedTextGUI textGUI) {
        this.controller = new CategoryController(categoryRepository, supplierRepository);
        this.textGUI = textGUI;
        setupUI();
        loadCategories();
        addTableSelectionListener();
    }

    private void setupUI() {
        // Create a vertical panel to hold all components
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Add a label for the title
        mainPanel.addComponent(new Label("Category Management").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Create a table to display categories
        categoryTable = new Table<>(" ",  "Name", "Supplier");

        mainPanel.addComponent(categoryTable);

        // Create a horizontal panel for buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Button to refresh the category list
        Button refreshButton = new Button("Refresh", () -> loadCategories());
        buttonPanel.addComponent(refreshButton);

        // Button to add a new category
        Button addCategoryButton = new Button("Add Category", () -> addCategory());
        buttonPanel.addComponent(addCategoryButton);

        // Button to update a category
        Button updateCategoryButton = new Button("Update Category", () -> updateCategory());
        buttonPanel.addComponent(updateCategoryButton);

        // Button to delete a category
        Button deleteCategoryButton = new Button("Delete Category", () -> deleteCategory());
        buttonPanel.addComponent(deleteCategoryButton);

        // Add the button panel to the main panel
        mainPanel.addComponent(buttonPanel);

        // Add the main panel to the current panel
        this.addComponent(mainPanel);
    }

    private void loadCategories() {
        categoryTable.getTableModel().clear();
        List<CategoryDto> categories = controller.getAllCategorys();
        for (ICategoryDto category : categories) {
            String supplierName= controller.getSupplier(category.getSupplierID()).getSupplierName().toString();
            categoryTable.getTableModel().addRow("",category.getCategoryName(), supplierName);
        }
    }

    private void addCategory() {
        String categoryName = TextInputDialog.showDialog(textGUI, "Add Category", "Category Name:", "");
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            CategoryDto category = new CategoryDto.Builder().categoryName(categoryName).build();
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
        selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) { // Check if no row is selected
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            selectedRow = -1;
            return;
        }

        // Get the selected category ID and name
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(1);
        String categoryName = categoryTable.getTableModel().getRow(selectedRow).get(2);

        // Prompt the user to input new values for the category
        String newCategoryName = TextInputDialog.showDialog(textGUI, "Update Category", "New Category Name:",
                categoryName);

        if (newCategoryName != null && !newCategoryName.trim().isEmpty()) {
            // Create a new CategoryDto with the updated values
            CategoryDto updatedCategory = new CategoryDto.Builder()
                    .categoryID(categoryID)
                    .categoryName(newCategoryName)
                    .build();

            // Use the controller to update the category
            CategoryDto result = controller.updateCategory(updatedCategory);

            if (result != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Category updated successfully!",
                        MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update category.",
                        MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
        selectedRow = -1;

    }

    private void deleteCategory() {
        // Prompt the user to select a category from the table
        selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) { // Check if no row is selected
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            selectedRow = -1;
            return;
        }
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(1);
        Boolean result = controller.deleteCategory(categoryID);

        if (result != null) {
            loadCategories();
            MessageDialog.showMessageDialog(textGUI, "Success", "Category delete successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete category.",
                    MessageDialogButton.OK);
        }
        selectedRow = -1;

    }

    private void addTableSelectionListener() {
        categoryTable.setSelectAction(() -> {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                // Clear previous selection
                for (int row = 0; row < categoryTable.getTableModel().getRowCount(); row++) {
                    categoryTable.getTableModel().setCell(0, row, "");
                }
                // Mark the selected row with a star
                categoryTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

}
