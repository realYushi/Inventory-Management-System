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
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

/**
 *
 * @author yushi
 */
public class ProductView extends Panel {

    private ProductController controller;
    private Table<String> ProductTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public ProductView(ProductRepository repository, WindowBasedTextGUI textGUI) {
        this.controller = new ProductController(repository);
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
        mainPanel.addComponent(new Label("Product Management")
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Create a table to display categories
        ProductTable = new Table<>(" ", "ID", "Name");
        mainPanel.addComponent(ProductTable);

        // Create a horizontal panel for buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Button to refresh the Product list
        Button refreshButton = new Button("Refresh", () -> loadCategories());
        buttonPanel.addComponent(refreshButton);

        // Button to add a new Product
        Button addProductButton = new Button("Add Product", () -> addProduct());
        buttonPanel.addComponent(addProductButton);

        // Button to update a Product
        Button updateProductButton = new Button("Update Product", () -> updateProduct());
        buttonPanel.addComponent(updateProductButton);

        // Button to delete a Product
        Button deleteProductButton = new Button("Delete Product", () -> deleteProduct());
        buttonPanel.addComponent(deleteProductButton);

        // Add the button panel to the main panel
        mainPanel.addComponent(buttonPanel);

        // Add the main panel to the current panel
        this.addComponent(mainPanel);
    }

    private void loadCategories() {
        ProductTable.getTableModel().clear();
        List<ProductDto> categories = controller.getAllProducts();
        for (IProductDto Product : categories) {
            ProductTable.getTableModel().addRow("",
                    Product.getName());

        }
    }

    private void addProduct() {
        String ProductName = TextInputDialog.showDialog(textGUI, "Add Product", "Product Name:", "");
        if (ProductName != null && !ProductName.trim().isEmpty()) {
            ProductDto Product = new ProductDto.Builder().name(ProductName).build();
            IProductDto newProduct = controller.createProduct(Product);

            if (newProduct != null) {
                loadCategories();
            }
            MessageDialog.showMessageDialog(textGUI, "Success", "Product added successfully!",
                    com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add Product.",
                    com.googlecode.lanterna.gui2.dialogs.MessageDialogButton.OK);
        }
    }

    private void updateProduct() {
        // Prompt the user to select a Product from the table
        selectedRow = ProductTable.getSelectedRow();
        if (selectedRow == -1) { // Check if no row is selected
            MessageDialog.showMessageDialog(textGUI, "Error", "No Product selected.", MessageDialogButton.OK);
            selectedRow = -1;
            return;
        }

        // Get the selected Product ID and name
        String ProductID = ProductTable.getTableModel().getRow(selectedRow).get(1);
        String ProductName = ProductTable.getTableModel().getRow(selectedRow).get(2);

        // Prompt the user to input new values for the Product
        String newProductName = TextInputDialog.showDialog(textGUI, "Update Product", "New Product Name:",
                ProductName);

        if (newProductName != null && !newProductName.trim().isEmpty()) {
            // Create a new ProductDto with the updated values
            ProductDto updatedProduct = new ProductDto.Builder()
                    .productID(ProductID)
                    .name(newProductName)
                    .build();

            // Use the controller to update the Product
            ProductDto result = controller.updateProduct(updatedProduct);

            if (result != null) {
                loadCategories();
                MessageDialog.showMessageDialog(textGUI, "Success", "Product updated successfully!",
                        MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update Product.",
                        MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
        selectedRow = -1;

    }

    private void deleteProduct() {
        // Prompt the user to select a Product from the table
        selectedRow = ProductTable.getSelectedRow();
        if (selectedRow == -1) { // Check if no row is selected
            MessageDialog.showMessageDialog(textGUI, "Error", "No Product selected.", MessageDialogButton.OK);
            selectedRow = -1;
            return;
        }
        String ProductID = ProductTable.getTableModel().getRow(selectedRow).get(1);
        Boolean result = controller.deleteProduct(ProductID);

        if (result != null) {
            loadCategories();
            MessageDialog.showMessageDialog(textGUI, "Success", "Product delete successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete Product.",
                    MessageDialogButton.OK);
        }
        selectedRow = -1;

    }

    private void addTableSelectionListener() {
        ProductTable.setSelectAction(() -> {
            int selectedRow = ProductTable.getSelectedRow();
            if (selectedRow != -1) {
                // Clear previous selection
                for (int row = 0; row < ProductTable.getTableModel().getRowCount(); row++) {
                    ProductTable.getTableModel().setCell(0, row, "");
                }
                // Mark the selected row with a star
                ProductTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

}
