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
import com.googlecode.lanterna.gui2.dialogs.ListSelectDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.table.Table;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ICategoryDto;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.ICategoryController;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

/**
 *
 * @author yushi
 */
public class CategoryView extends Panel implements ISelectedble{
    private ICategoryController controller;
    private Table<String> categoryTable;
    private WindowBasedTextGUI textGUI;
    // Store the index of the selected row in the table, -1 means no row is selected
    private int selectedRow = -1;

    public CategoryView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        // Initialize the controller and textGUI
        this.controller = new CategoryController(unitOfWork);
        this.textGUI = textGUI;
        // Setup the UI components
        setupUI();
        // Load the categories from the database
        loadCategories();
        // listen for table selection events
        addTableSelectionListener();
    }

    private void setupUI() {
        // Create a vertical panel to hold all components
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Add a label for the title
        mainPanel.addComponent(new Label("Category Management")
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Create a table to display categories
        categoryTable = new Table<>(" ", "ID", "Name", "Supplier");
        mainPanel.addComponent(categoryTable);

        // Create a horizontal panel for buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Button to refresh the category list
        Button refreshButton = new Button("Refresh", this::loadCategories);
        buttonPanel.addComponent(refreshButton);

        // Button to add a new category
        Button addCategoryButton = new Button("Add Category", this::addCategory);
        buttonPanel.addComponent(addCategoryButton);

        // Button to update a category
        Button updateCategoryButton = new Button("Update Category", this::updateCategory);
        buttonPanel.addComponent(updateCategoryButton);

        // Button to delete a category
        Button deleteCategoryButton = new Button("Delete Category", this::deleteCategory);
        buttonPanel.addComponent(deleteCategoryButton);

        // Add the button panel to the main panel
        mainPanel.addComponent(buttonPanel);

        // Add the main panel to the current panel
        this.addComponent(mainPanel);
    }

    private void loadCategories() {
        // Clear the table before loading new data
        categoryTable.getTableModel().clear();
        // Get all categories from the database
        List<CategoryDto> categories = controller.getAllCategorys();
        // If there are no categories, skip the loop
        if (categories.isEmpty()) {
            return;
        }
        // Add each category to the table
        for (ICategoryDto category : categories) {
            SupplierDto supplier = controller.getSupplier(category.getSupplierID());
            // If the supplier does not exist, delete the category
            if (supplier == null) {
                controller.deleteCategory(category.getCategoryID());
                continue;
            }
            // Get the supplier name
            String supplierName = supplier.getSupplierName();
            // Add the category to the table
            categoryTable.getTableModel().addRow("", category.getCategoryID(), category.getCategoryName(),
                    supplierName);
        }
        // Reset the selected row index
        selectedRow = -1;
    }

    private void addCategory() {
        // Prompt the user to select a supplier
        String supplierID = selectSupplier();
        if (supplierID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No supplier selected.", MessageDialogButton.OK);
            return;

        }
        // Prompt the user to enter a category name
        String categoryName = TextInputDialog.showDialog(textGUI, "Add Category", "Category Name:", "");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No Category name input.", MessageDialogButton.OK);
            return;
        }

        // Create a new category object
        CategoryDto category = new CategoryDto.Builder()
                .categoryName(categoryName)
                .supplierID(supplierID)
                .build();
        ICategoryDto newCategory = controller.createCategory(category);
        if (newCategory != null) {
            loadCategories();
            MessageDialog.showMessageDialog(textGUI, "Success", "Category added successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add category.", MessageDialogButton.OK);
        }

        // Reset the selected row index
        selectedRow = -1;
    }

    private void updateCategory() {
        // Prompt the user to select a category from the table
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }
        // Get the category ID and name from the selected row
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(1);
        String categoryName = categoryTable.getTableModel().getRow(selectedRow).get(2);
        // Prompt the user to enter a new category name
        String newCategoryName = TextInputDialog.showDialog(textGUI, "Update Category", "New Category Name:",
                categoryName);
        // Prompt the user to select a supplier
        String newSupplierID = selectSupplier();
        // Check if the input is valid and a supplier is selected
        if (newCategoryName != null && !newCategoryName.trim().isEmpty() && newSupplierID != null) {
            // Create a new category object with the updated values
            CategoryDto updatedCategory = new CategoryDto.Builder()
                    .categoryID(categoryID)
                    .categoryName(newCategoryName)
                    .supplierID(newSupplierID)
                    .build();
            // Update the category in the database
            CategoryDto result = controller.updateCategory(updatedCategory);
            if (result != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Category updated successfully!",
                        MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update category.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input or no supplier selected.",
                    MessageDialogButton.OK);
        }
        // Reset the selected row index
        selectedRow = -1;
    }

    private void deleteCategory() {
        // Prompt the user to select a category from the table
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }
        // Get the category ID from the selected row
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(1);
        // Delete the category from the database
        Boolean result = controller.deleteCategory(categoryID);

        if (result != null) {
            loadCategories();
            MessageDialog.showMessageDialog(textGUI, "Success", "Category delete successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete category.",
                    MessageDialogButton.OK);
        }
        // Reset the selected row index
        selectedRow = -1;

    }
    @Override
    public void addTableSelectionListener() {
        categoryTable.setSelectAction(() -> {
            selectedRow = categoryTable.getSelectedRow();
            // Clear the selection marker from all rows
            for (int row = 0; row < categoryTable.getTableModel().getRowCount(); row++) {
                categoryTable.getTableModel().setCell(0, row, "");
            }
            if (selectedRow >= 0 && selectedRow < categoryTable.getTableModel().getRowCount()) {
                // Set the selection marker on the selected row
                categoryTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

    private String selectSupplier() {
        // Get all suppliers from the database
        List<SupplierDto> suppliers = controller.getAllSupplier();
        // If there are no suppliers, show a message and return null
        if (suppliers.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Suppliers", "There are no suppliers available.",
                    MessageDialogButton.OK);
            return null;
        }
        // Create an array of supplier names
        String[] supplierNames = suppliers.stream().map(SupplierDto::getSupplierName).toArray(String[]::new);
        // Show a dialog to select a supplier
        String selectedSupplierName = ListSelectDialog.showDialog(textGUI, "Select Supplier", "Choose a supplier:",
                supplierNames);
        if (selectedSupplierName != null) {
            // Find the supplier ID based on the selected supplier name
            return suppliers.stream()
                    .filter(s -> s.getSupplierName().equals(selectedSupplierName))
                    .findFirst()
                    .map(SupplierDto::getSupplierID)
                    .orElse(null);
        }
        return null;
    }

}
