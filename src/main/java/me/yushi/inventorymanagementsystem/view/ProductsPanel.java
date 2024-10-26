package me.yushi.inventorymanagementsystem.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import net.miginfocom.swing.MigLayout;

public class ProductsPanel extends JPanel {
    // Constants, messages and variables
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this product?";
    private static final String DELETE_PRODUCT_TITLE = "Delete Product";
    private static final String SELECT_PRODUCT_TO_DELETE = "Please select a product to delete.";
    private static final String SELECT_PRODUCT_TO_UPDATE = "Please select a product to update.";
    private static final String CREATE_NEW_PRODUCT_TITLE = "Create New Product";
    private static final String UPDATE_PRODUCT_TITLE = "Update Product";
    private static final String[] COLUMN_NAMES = { "Product ID", "Name", "Category", "Supplier", "Quantity", "Unit",
            "Price" };

    private List<Product> products;
    private ProductController productController;
    private CategoryController categoryController;
    private SupplierController supplierController;
    private Map<String, Category> categories;
    private Map<String, Supplier> suppliers;
    private DefaultTableModel tableModel;
    private JTable productTable;

    public ProductsPanel(ProductController productController, CategoryController categoryController,
            SupplierController supplierController) {
        this.categoryController = categoryController;
        this.productController = productController;
        this.supplierController = supplierController;
        initialUI();
        // Add a listener to refresh data each time the panel is shown
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshData();
            }
        });
    }

    // Initialize the UI components, layout and add event listeners
    private void initialUI() {
        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));

        // Set up the model, make the table non-editable and add it to a scroll pane
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Create a table with the table model
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        this.add(scrollPane, "cell 0 0, grow");

        // Create a button panel and add it to the main panel
        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, "cell 0 1, grow");
    }

    // Create a button panel with Add, Update and Delete buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("fill", "[grow][grow][grow]", "[grow]"));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton, "cell 0 0, grow");
        buttonPanel.add(updateButton, "cell 1 0, grow");
        buttonPanel.add(deleteButton, "cell 2 0, grow");

        addButton.addActionListener(e -> showCreateProductDialog());
        deleteButton.addActionListener(e -> showDeleteConfirmationDialog());
        updateButton.addActionListener(e -> showUpdateProductDialog());

        return buttonPanel;
    }

    // Show a confirmation dialog before deleting a product
    private void showDeleteConfirmationDialog() {
        // Get the selected row
        int selectedRow = productTable.getSelectedRow();
        // If no row is selected, show an error message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_PRODUCT_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the product ID from the selected row
        String productID = tableModel.getValueAt(selectedRow, 0).toString();
        // Show a confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_PRODUCT_TITLE,
                JOptionPane.YES_NO_OPTION);
        // If the user confirms the deletion, delete the product and refresh the data
        if (result == JOptionPane.YES_OPTION) {
            productController.deleteProduct(productID);
            refreshData();
        }
    }

    private void showCreateProductDialog() {
        JTextField productIDField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JTextField unitField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JComboBox<String> categoryComboBox = createCategoryComboBox();
        JComboBox<String> supplierComboBox = createSupplierComboBox();

        // Create a panel with the input fields
        JPanel panel = createProductDialogPanel(productIDField, nameField, quantityField, unitField, priceField,
                categoryComboBox, supplierComboBox);
        // Show a dialog with the panel, and handle the input
        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_PRODUCT_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateProduct(productIDField, nameField, quantityField, unitField, priceField, categoryComboBox,
                    supplierComboBox);
        }
    }

    private String findCategoryNameById(String categoryId) {
        Category category = categories.get(categoryId);
        return (category != null) ? category.getCategoryName() : "Unknown";
    }

    private String findSupplierNameById(String supplierId) {
        Supplier supplier = suppliers.get(supplierId);
        return (supplier != null) ? supplier.getSupplierName() : "Unknown";
    }

    private JComboBox<String> createCategoryComboBox() {
        JComboBox<String> categoryComboBox = new JComboBox<>();
        for (Category category : categories.values()) {
            categoryComboBox.addItem(category.getCategoryName());
        }
        return categoryComboBox;
    }

    private JComboBox<String> createSupplierComboBox() {
        JComboBox<String> supplierComboBox = new JComboBox<>();
        for (Supplier supplier : suppliers.values()) {
            supplierComboBox.addItem(supplier.getSupplierName());
        }
        return supplierComboBox;
    }

    // Create a panel with input fields for product details
    private JPanel createProductDialogPanel(JTextField productIDField, JTextField nameField, JTextField quantityField,
            JTextField unitField, JTextField priceField, JComboBox<String> categoryComboBox,
            JComboBox<String> supplierComboBox) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Name:"), "wrap");
        panel.add(nameField, "wrap");
        panel.add(new JLabel("Category:"), "wrap");
        panel.add(categoryComboBox, "wrap");
        panel.add(new JLabel("Supplier:"), "wrap");
        panel.add(supplierComboBox, "wrap");
        panel.add(new JLabel("Quantity:"), "wrap");
        panel.add(quantityField, "wrap");
        panel.add(new JLabel("Unit:"), "wrap");
        panel.add(unitField, "wrap");
        panel.add(new JLabel("Price:"), "wrap");
        panel.add(priceField, "wrap");
        return panel;
    }

    // Handle the input from the create product dialog
    private void handleCreateProduct(JTextField productIDField, JTextField nameField, JTextField quantityField,
            JTextField unitField, JTextField priceField, JComboBox<String> categoryComboBox,
            JComboBox<String> supplierComboBox) {
        if (!validateProductFields(nameField, quantityField, unitField, priceField)) {
            return;
        }
        String selectedCategoryName = (String) categoryComboBox.getSelectedItem();
        String selectedSupplierName = (String) supplierComboBox.getSelectedItem();

        Category category = categories.values().stream().filter(c -> c.getCategoryName().equals(selectedCategoryName))
                .findFirst().orElse(null);
        Supplier supplier = suppliers.values().stream().filter(s -> s.getSupplierName().equals(selectedSupplierName))
                .findFirst().orElse(null);
        Product newProduct = new Product("", nameField.getText(), category, supplier,
                Integer.parseInt(quantityField.getText()), unitField.getText(),
                Double.parseDouble(priceField.getText()));

        productController.createProduct(newProduct);
        refreshData();
    }

    private void showUpdateProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        // If no row is selected, show an error message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_PRODUCT_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the product details from the selected row
        String productID = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        String categoryName = tableModel.getValueAt(selectedRow, 2).toString();
        String supplierName = tableModel.getValueAt(selectedRow, 3).toString();
        String quantity = tableModel.getValueAt(selectedRow, 4).toString();
        String unit = tableModel.getValueAt(selectedRow, 5).toString();
        String price = tableModel.getValueAt(selectedRow, 6).toString();

        JTextField productIDField = new JTextField(productID, 10);
        JTextField nameField = new JTextField(name, 10);
        JTextField quantityField = new JTextField(quantity, 10);
        JTextField unitField = new JTextField(unit, 10);
        JTextField priceField = new JTextField(price, 10);
        JComboBox<String> categoryComboBox = createCategoryComboBox();
        JComboBox<String> supplierComboBox = createSupplierComboBox();
        categoryComboBox.setSelectedItem(categoryName);
        supplierComboBox.setSelectedItem(supplierName);

        // Create a panel with the input fields
        JPanel panel = createProductDialogPanel(productIDField, nameField, quantityField, unitField, priceField,
                categoryComboBox, supplierComboBox);
        // Show a dialog with the panel, and handle the input
        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_PRODUCT_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateProduct(selectedRow, productIDField, nameField, quantityField, unitField, priceField,
                    categoryComboBox, supplierComboBox);
        }
    }

    // Handle the input from the update product dialog
    private void handleUpdateProduct(int selectedRow, JTextField productIDField, JTextField nameField,
            JTextField quantityField, JTextField unitField, JTextField priceField, JComboBox<String> categoryComboBox,
            JComboBox<String> supplierComboBox) {
        if (!validateProductFields(nameField, quantityField, unitField, priceField)) {
            return;
        }
        String selectedCategoryName = (String) categoryComboBox.getSelectedItem();
        String selectedSupplierName = (String) supplierComboBox.getSelectedItem();

        Category category = categories.values().stream().filter(c -> c.getCategoryName().equals(selectedCategoryName))
                .findFirst().orElse(null);
        Supplier supplier = suppliers.values().stream().filter(s -> s.getSupplierName().equals(selectedSupplierName))
                .findFirst().orElse(null);
        Product updatedProduct = new Product(productIDField.getText(), nameField.getText(), category, supplier,
                Integer.parseInt(quantityField.getText()), unitField.getText(),
                Double.parseDouble(priceField.getText()));

        productController.updateProduct(updatedProduct);
        refreshData();
    }

    public void refreshData() {
        this.products = productController.getAllProducts();
        this.categories = categoryController.getAllCategorys().stream()
                .collect(Collectors.toMap(Category::getCategoryID, category -> category));
        this.suppliers = supplierController.getAllSuppliers().stream()
                .collect(Collectors.toMap(Supplier::getSupplierID, supplier -> supplier));

        // Clear the table and add the products to the table model
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[] { product.getProductID(), product.getName(),
                    findCategoryNameById(product.getCategory().getCategoryID()),
                    findSupplierNameById(product.getSupplier().getSupplierID()), product.getQuantity(),
                    product.getUnit(), product.getPrice() });
        }
    }

    private boolean validateProductFields(JTextField nameField, JTextField quantityField, JTextField unitField,
            JTextField priceField) {
        // Check if the name field is empty
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if the quantity field is a valid integer and not negative
        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be negative.", ERROR_MESSAGE,
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid integer.", ERROR_MESSAGE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if the unit field is empty
        if (unitField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Unit cannot be empty.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check if the price field is a valid number and not negative
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative.", ERROR_MESSAGE,
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number.", ERROR_MESSAGE,
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}