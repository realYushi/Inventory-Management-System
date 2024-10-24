package me.yushi.inventorymanagementsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.model.Product;
import net.miginfocom.swing.MigLayout;

public class ProductsPanel extends JPanel {
    // private List<ProductDto> products;
    private Map<String, CategoryDto> categories;
    private Map<String, SupplierDto> suppliers;
    private DefaultTableModel tableModel;
    private JTable productTable;

    public ProductsPanel(List<CategoryDto> categories, List<SupplierDto> suppliers) {
        this.categories = categories.stream()
                .collect(Collectors.toMap(CategoryDto::getCategoryID, category -> category));
        this.suppliers = suppliers.stream().collect(Collectors.toMap(SupplierDto::getSupplierID, supplier -> supplier));

        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));

        String[] columnNames = { "Product ID", "Name", "Category", "Supplier", "Quantity", "Unit", "Price" };
        // Object[][] data=new Object[products.size()][7];
        // for(int i=0;i<products.size();i++){
        // ProductDto product = products.get(i);
        // data[i][0] = product.getProductID();
        // data[i][1] = product.getName();
        // data[i][2] = findCategoryNameById(product.getCategoryID());
        // data[i][3] = findSupplierNameById(product.getSupplierID());
        // data[i][4] = product.getQuantity();
        // data[i][5] = product.getUnit();
        // data[i][6] = product.getPrice();
        // }
        Object data[][] = {
                { 1, "Product 1", "1", "1", 10, "kg", 100 },
                { 2, "Product 2", "2", "2", 20, "kg", 200 },
        };
        Object[][] transformedData = new Object[data.length][7];
        for (int i = 0; i < data.length; i++) {
            transformedData[i][0] = data[i][0]; // Product ID
            transformedData[i][1] = data[i][1]; // Product Name
            transformedData[i][2] = findCategoryNameById((String) data[i][2]); // Category Name
            transformedData[i][3] = findSupplierNameById((String) data[i][3]); // Supplier Name
            transformedData[i][4] = data[i][4]; // Quantity
            transformedData[i][5] = data[i][5]; // Unit
            transformedData[i][6] = data[i][6]; // Price
        }
        tableModel = new DefaultTableModel(transformedData, columnNames);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        this.add(scrollPane, "cell 0 0, grow");

        JPanel buttonPanel = new JPanel(new MigLayout("fill", "[grow][grow][grow]", "[grow]"));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton, "cell 0 0, grow");
        buttonPanel.add(updateButton, "cell 1 0, grow");
        buttonPanel.add(deleteButton, "cell 2 0, grow");
        this.add(buttonPanel, "cell 0 1, grow");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateProductDialog();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteConfirmationDialog();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateProductDialog();
            }
        });

    }

    // Helper method to find category name by ID
    private String findCategoryNameById(String categoryId) {
        CategoryDto category = categories.get(categoryId);
        return (category != null) ? category.getCategoryName() : "Unknown";
    }

    // Helper method to find supplier name by ID
    private String findSupplierNameById(String supplierId) {
        SupplierDto supplier = suppliers.get(supplierId);
        return (supplier != null) ? supplier.getSupplierName() : "Unknown";
    }

    private void showDeleteConfirmationDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productID = tableModel.getValueAt(selectedRow, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?",
                "Delete Product", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Delete the product from the backend
            // productController.deleteProduct(productID);

            // Remove the product from the table
            tableModel.removeRow(selectedRow);
        }
    }

    private void showCreateProductDialog() {

        JTextField productIDField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JTextField unitField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JComboBox<String> categoryComboBox = new JComboBox<>();
        JComboBox<String> supplierComboBox = new JComboBox<>();
        for (CategoryDto category : categories.values()) {
            categoryComboBox.addItem(category.getCategoryName());
        }
        for (SupplierDto supplier : suppliers.values()) {
            supplierComboBox.addItem(supplier.getSupplierName());
        }
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Product ID:"), "wrap");
        panel.add(productIDField, "wrap");
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
        int result = JOptionPane.showConfirmDialog(this, panel, "Create New Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Get selected category and supplier
            String selectedCategoryName = (String) categoryComboBox.getSelectedItem();
            String selectedSupplierName = (String) supplierComboBox.getSelectedItem();

            // Find corresponding IDs for the selected names
            String selectedCategoryID = categories.values().stream()
                    .filter(category -> category.getCategoryName().equals(selectedCategoryName))
                    .map(CategoryDto::getCategoryID)
                    .findFirst()
                    .orElse(null);

            String selectedSupplierID = suppliers.values().stream()
                    .filter(supplier -> supplier.getSupplierName().equals(selectedSupplierName))
                    .map(SupplierDto::getSupplierID)
                    .findFirst()
                    .orElse(null);
            // Add the new product to the backend
            Product newProduct = new Product(
                    productIDField.getText(),
                    nameField.getText(),
                    selectedCategoryID,
                    selectedSupplierID,
                    Integer.parseInt(quantityField.getText()),
                    unitField.getText(),
                    Double.parseDouble(priceField.getText()));
            // productController.addProduct(newProduct);

            // Add the new product to the table
            tableModel.addRow(new Object[] {
                    newProduct.getProductID(),
                    newProduct.getName(),
                    selectedCategoryName,
                    selectedSupplierName,
                    newProduct.getQuantity(),
                    newProduct.getUnit(),
                    newProduct.getPrice()
            });
        }

    }

    private void showUpdateProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected product's data from the table
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
        JComboBox<String> categoryComboBox = new JComboBox<>();
        JComboBox<String> supplierComboBox = new JComboBox<>();
        for (CategoryDto category : categories.values()) {
            categoryComboBox.addItem(category.getCategoryName());
        }
        for (SupplierDto supplier : suppliers.values()) {
            supplierComboBox.addItem(supplier.getSupplierName());
        }
        categoryComboBox.setSelectedItem(categoryName);
        supplierComboBox.setSelectedItem(supplierName);

        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Product ID:"), "wrap");
        panel.add(productIDField, "wrap");
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

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Get selected category and supplier
            String selectedCategoryName = (String) categoryComboBox.getSelectedItem();
            String selectedSupplierName = (String) supplierComboBox.getSelectedItem();

            // Find corresponding IDs for the selected names
            String selectedCategoryID = categories.values().stream()
                    .filter(category -> category.getCategoryName().equals(selectedCategoryName))
                    .map(CategoryDto::getCategoryID)
                    .findFirst()
                    .orElse(null);

            String selectedSupplierID = suppliers.values().stream()
                    .filter(supplier -> supplier.getSupplierName().equals(selectedSupplierName))
                    .map(SupplierDto::getSupplierID)
                    .findFirst()
                    .orElse(null);

            // Update the product in the backend
            Product updatedProduct = new Product(
                    productIDField.getText(),
                    nameField.getText(),
                    selectedCategoryID,
                    selectedSupplierID,
                    Integer.parseInt(quantityField.getText()),
                    unitField.getText(),
                    Double.parseDouble(priceField.getText()));
            // productController.updateProduct(updatedProduct);

            // Update the table with the new data
            tableModel.setValueAt(updatedProduct.getProductID(), selectedRow, 0);
            tableModel.setValueAt(updatedProduct.getName(), selectedRow, 1);
            tableModel.setValueAt(selectedCategoryName, selectedRow, 2);
            tableModel.setValueAt(selectedSupplierName, selectedRow, 3);
            tableModel.setValueAt(updatedProduct.getQuantity(), selectedRow, 4);
            tableModel.setValueAt(updatedProduct.getUnit(), selectedRow, 5);
            tableModel.setValueAt(updatedProduct.getPrice(), selectedRow, 6);
        }
    }
}
