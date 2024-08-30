package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.gui2.table.Table;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

/**
 * Product management view using Lanterna framework.
 *
 * @author yushi
 */
public class ProductView extends Panel implements ISelectedble {

    private ProductController controller;
    private Table<String> productTable;
    private WindowBasedTextGUI textGUI;
    // The index of the selected row in the product table, -1 if no row is selected
    private int selectedRow = -1;

    public ProductView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new ProductController(unitOfWork);
        this.textGUI = textGUI;
        // Setup the UI
        setupUI();
        // Load the products
        loadProducts();
        // Add table selection listener
        addTableSelectionListener();
    }

    private void setupUI() {
        // Main panel
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        mainPanel.addComponent(new Label("Product Management")
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Product table
        productTable = new Table<>(" ", "ID", "Name", "Category", "Quantity", "Unit", "Price");
        mainPanel.addComponent(productTable);
        // Button panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        // refresh button
        Button refreshButton = new Button("Refresh", this::loadProducts);
        buttonPanel.addComponent(refreshButton);
        // Add product button
        Button addProductButton = new Button("Add Product", this::addProduct);
        buttonPanel.addComponent(addProductButton);
        // Update product button
        Button updateProductButton = new Button("Update Product", this::updateProduct);
        buttonPanel.addComponent(updateProductButton);
        // Delete product button
        Button deleteProductButton = new Button("Delete Product", this::deleteProduct);
        buttonPanel.addComponent(deleteProductButton);
        // Add button panel to main panel
        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadProducts() {
        // Clear the table
        productTable.getTableModel().clear();
        // Get all products
        List<ProductDto> products = controller.getAllProducts();
        // If there are no products, return
        if (products.isEmpty()) {
            return;
        }
        // Add products to the table
        for (IProductDto product : products) {
            CategoryDto category = controller.getCategoryById(product.getCategoryID());
            // If the category does not exist, delete the product
            if (category == null) {
                controller.deleteProduct(product.getProductID());
                continue;
            }
            String categoryName = category != null ? category.getCategoryName() : "N/A";
            String price = String.valueOf(product.getPrice()) + " $";
            productTable.getTableModel().addRow("",
                    product.getProductID(),
                    product.getName(),
                    categoryName,
                    String.valueOf(product.getQuantity()),
                    product.getUnit(),
                    price);
        }
        // Clear the selection marker
        selectedRow = -1;
    }

    private void addProduct() {
        // Get product details
        String productName = TextInputDialog.showDialog(textGUI, "Add Product", "Product Name:", "");
        if (productName == null || productName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product name cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String productUnit = TextInputDialog.showDialog(textGUI, "Add Product", "Product Unit:", "");
        if (productUnit == null || productName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product unit cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String productPrice = TextInputDialog.showDialog(textGUI, "Add Product", "Product Price:", "");
        if (productPrice == null || productPrice.trim().isEmpty()
                || !productPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product price cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String categoryID = selectCategory();
        if (categoryID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }
        // Create the product
        ProductDto product = new ProductDto.Builder()
                .name(productName)
                .quantity(0)
                .price(Double.parseDouble(productPrice))
                .unit(productUnit)
                .categoryID(categoryID)
                .build();
        IProductDto newProduct = controller.createProduct(product);
        if (newProduct != null) {
            loadProducts();
            MessageDialog.showMessageDialog(textGUI, "Success", "Product added successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add product.", MessageDialogButton.OK);
        }
        // Clear the selection marker
        selectedRow = -1;
    }

    private void updateProduct() {
        // Get the selected product
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        // Get the product ID, form the selected row
        String productID = productTable.getTableModel().getRow(selectedRow).get(1);

        // Get the new product details
        String newProductName = TextInputDialog.showDialog(textGUI, "Add Product", "Product Name:", "");
        if (newProductName == null || newProductName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product name cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String newProductUnit = TextInputDialog.showDialog(textGUI, "Add Product", "Product Unit:", "");
        if (newProductUnit == null || newProductUnit.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product unit cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String newProductPrice = TextInputDialog.showDialog(textGUI, "Add Product", "Product Price:", "");
        if (newProductPrice == null || newProductPrice.trim().isEmpty()
                || !newProductPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Product price cannot be empty.", MessageDialogButton.OK);
            return;
        }
        String newCategoryID = selectCategory();
        if (newCategoryID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No category selected.", MessageDialogButton.OK);
            return;
        }
        // Update the product
        ProductDto updatedProduct = new ProductDto.Builder()
                .name(newProductName)
                .price(Double.parseDouble(newProductPrice))
                .unit(newProductUnit)
                .categoryID(newCategoryID)
                .productID(productID)
                .build();
        ProductDto result = controller.updateProduct(updatedProduct);
        if (result != null) {
            loadProducts();
            MessageDialog.showMessageDialog(textGUI, "Success", "Product updated successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update product.", MessageDialogButton.OK);
        }
        // Clear the selection marker
        selectedRow = -1;
    }

    private void deleteProduct() {
        // check if a product is selected
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        // Get the product ID from the selected row
        String productID = productTable.getTableModel().getRow(selectedRow).get(1);
        Boolean result = controller.deleteProduct(productID);
        if (result != null) {
            loadProducts();
            MessageDialog.showMessageDialog(textGUI, "Success", "Product deleted successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete product.", MessageDialogButton.OK);
        }
        // Clear the selection marker
        selectedRow = -1;
    }

    @Override
    public void addTableSelectionListener() {
        productTable.setSelectAction(() -> {
            selectedRow = productTable.getSelectedRow();
            // Clear the selection marker from all rows
            for (int row = 0; row < productTable.getTableModel().getRowCount(); row++) {
                productTable.getTableModel().setCell(0, row, "");
            }
            if (selectedRow >= 0 && selectedRow < productTable.getTableModel().getRowCount()) {
                // Set the selection marker on the selected row
                productTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

    private String selectCategory() {
        // Get all categories
        List<CategoryDto> categoryDtos = controller.getAllCategory();
        if (categoryDtos.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Category", "There are no categories available.",
                    MessageDialogButton.OK);
            return null;
        }
        // Get the category names
        String[] categoryNames = categoryDtos.stream().map(CategoryDto::getCategoryName).toArray(String[]::new);
        // Show the category selection dialog
        String selectedCategoryName = ListSelectDialog.showDialog(textGUI, "Select Category", "Choose a Category:",
                categoryNames);
        // Get the category ID
        if (selectedCategoryName != null) {
            return categoryDtos.stream()
                    .filter(s -> s.getCategoryName().equals(selectedCategoryName))
                    .findFirst()
                    .map(CategoryDto::getCategoryID)
                    .orElse(null);
        }
        return null;
    }

}
