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
public class CategoryView extends Panel {

    private ICategoryController controller;
    private Table<String> categoryTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public CategoryView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new CategoryController(unitOfWork);
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
        categoryTable = new Table<>(" ", "ID", "Name", "Supplier");

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
        if(categories.size()==0){
            return;
        }
        for (ICategoryDto category : categories) {
            SupplierDto supplier = controller.getSupplier(category.getSupplierID());
            String supplierName = supplier != null ? supplier.getSupplierName() : "N/A";
            categoryTable.getTableModel().addRow("", category.getCategoryID(), category.getCategoryName(), supplierName);
        }
        selectedRow=-1;
    }

    private void addCategory() {
        String categoryName = TextInputDialog.showDialog(textGUI, "Add Category", "Category Name:", "");
        String supplierID = selectSupplier(); // Get supplier ID from the selection dialog
        if (categoryName != null && !categoryName.trim().isEmpty() && supplierID != null) {
            CategoryDto category = new CategoryDto.Builder()
                    .categoryName(categoryName)
                    .supplierID(supplierID)
                    .build();
            ICategoryDto newCategory = controller.createCategory(category);
            if (newCategory != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Category added successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add category.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input or no supplier selected.", MessageDialogButton.OK);
        }
        selectedRow=-1;
    }

    private void updateCategory() {
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }
        String categoryID = categoryTable.getTableModel().getRow(selectedRow).get(1);
        String categoryName = categoryTable.getTableModel().getRow(selectedRow).get(2);
        String newCategoryName = TextInputDialog.showDialog(textGUI, "Update Category", "New Category Name:", categoryName);
        String newSupplierID = selectSupplier(); // Allow changing the supplier
        if (newCategoryName != null && !newCategoryName.trim().isEmpty() && newSupplierID != null) {
            CategoryDto updatedCategory = new CategoryDto.Builder()
                    .categoryID(categoryID)
                    .categoryName(newCategoryName)
                    .supplierID(newSupplierID)
                    .build();
            CategoryDto result = controller.updateCategory(updatedCategory);
            if (result != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Category updated successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update category.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input or no supplier selected.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void deleteCategory() {
        // Prompt the user to select a category from the table
        if (selectedRow == -1) { // Check if no row is selected
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
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
        List<SupplierDto> suppliers = controller.getAllSupplier();
        if (suppliers.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Suppliers", "There are no suppliers available.", MessageDialogButton.OK);
            return null;
        }
        String[] supplierNames = suppliers.stream().map(SupplierDto::getSupplierName).toArray(String[]::new);
        String selectedSupplierName = ListSelectDialog.showDialog(textGUI, "Select Supplier", "Choose a supplier:", supplierNames);
        if (selectedSupplierName != null) {
            
            return suppliers.stream()
                            .filter(s -> s.getSupplierName().equals(selectedSupplierName))
                            .findFirst()
                            .map(SupplierDto::getSupplierID)
                            .orElse(null);
        }
        return null;
    }

}
