package me.yushi.inventorymanagementsystem.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import net.miginfocom.swing.MigLayout;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction.TransactionType;

public class TransactionsPanel extends JPanel {
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this transaction?";
    private static final String DELETE_TRANSACTION_TITLE = "Delete Transaction";
    private static final String SELECT_TRANSACTION_TO_DELETE = "Please select a Transaction to delete.";
    private static final String SELECT_TRANSACTION_TO_UPDATE = "Please select a Transaction to update.";
    private static final String CREATE_NEW_TRANSACTION_TITLE = "Create New Transaction";
    private static final String UPDATE_TRANSACTION_TITLE = "Update Transaction";
    private static final String[] COLUMN_NAMES = { "TransationID", "Product", "Quantity", "Date", "Type", "Price" };

    private List<InventoryTransaction> transactions;
    private Map<String, Product> products;
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private InventoryTransactionController transactionController;
    private ProductController productController;

    public TransactionsPanel(InventoryTransactionController transactionController,
            ProductController productController) {
        this.transactionController = transactionController;
        this.productController = productController;

        this.transactions = transactionController.getAllInventoryTransations();
        this.products = productController.getAllProducts().stream()
                .collect(Collectors.toMap(Product::getProductID, p -> p));

        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));
        Object[][] data = new Object[transactions.size()][COLUMN_NAMES.length];
        for (int i = 0; i < transactions.size(); i++) {
            InventoryTransaction transaction = transactions.get(i);
            data[i] = new Object[] { transaction.getTransactionID(), transaction.getProductID(),
                    transaction.getQuantity(), transaction.getDate(), transaction.getTransactionType(),
                    transaction.getPrice() };
        }
        Object[][] transformedData = transformData(data);
        tableModel = new DefaultTableModel(transformedData, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        this.add(scrollPane, "cell 0 0, grow");

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, "cell 0 1, grow");
    }

    private Object[][] transformData(Object[][] data) {
        Object[][] transformedData = new Object[data.length][COLUMN_NAMES.length];
        for (int i = 0; i < data.length; i++) {
            transformedData[i][0] = data[i][0]; // Transaction ID
            transformedData[i][1] = findProductNameById((String) data[i][1]); // Product Name
            transformedData[i][2] = data[i][2]; // Quantity
            transformedData[i][3] = data[i][3]; // Date
            transformedData[i][4] = data[i][4]; // Type
            transformedData[i][5] = data[i][5]; // Price
        }
        return transformedData;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new MigLayout("fill", "[grow][grow][grow]", "[grow]"));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton, "cell 0 0, grow");
        buttonPanel.add(updateButton, "cell 1 0, grow");
        buttonPanel.add(deleteButton, "cell 2 0, grow");

        addButton.addActionListener(e -> showCreateTransactionDialog());
        deleteButton.addActionListener(e -> showDeleteConfirmationDialog());
        updateButton.addActionListener(e -> showUpdateTransactionDialog());

        return buttonPanel;
    }

    private String findProductNameById(String productID) {
        Product product = products.get(productID);
        return (product != null) ? product.getName() : "Unknown";
    }

    private void showDeleteConfirmationDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_TRANSACTION_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String productID = tableModel.getValueAt(selectedRow, 0).toString();
            transactionController.deleteInventoryTransaction(productID);
            tableModel.removeRow(selectedRow);
        }
    }

    private JPanel createTransactionDialogPanel(JComboBox<String> productCombo, JTextField quantityField,
            JTextField dateField, JComboBox<TransactionType> typeCombo, JTextField priceField) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Product:"), "wrap");
        panel.add(productCombo, "wrap");
        panel.add(new JLabel("Quantity:"), "wrap");
        panel.add(quantityField, "wrap");
        panel.add(new JLabel("Date:"), "wrap");
        panel.add(dateField, "wrap");
        panel.add(new JLabel("Type:"), "wrap");
        panel.add(typeCombo, "wrap");
        panel.add(new JLabel("Price:"), "wrap");
        panel.add(priceField, "wrap");
        return panel;
    }

    private void showCreateTransactionDialog() {
        JComboBox<String> productCombo = createProductComboBox();
        JTextField quantityField = new JTextField(10);
        JTextField dateField = new JTextField(10);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        JTextField priceField = new JTextField(10);

        JPanel panel = createTransactionDialogPanel(productCombo, quantityField, dateField, typeCombo, priceField);

        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_TRANSACTION_TITLE,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateTransaction(productCombo, quantityField, dateField, typeCombo, priceField);
        }
    }

    private JComboBox<String> createProductComboBox() {
        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : products.values()) {
            productComboBox.addItem(product.getName());
        }
        return productComboBox;
    }

    private void handleCreateTransaction(JComboBox<String> productCombo, JTextField quantityField, JTextField dateField,
            JComboBox<TransactionType> typeCombo, JTextField priceField) {
        String productID = findProductIDByName((String) productCombo.getSelectedItem());
        Product product = productController.getProductByID(productID);
        InventoryTransaction newTransaction = new InventoryTransaction("", product,
                Integer.parseInt(quantityField.getText()), new Date(), (TransactionType) typeCombo.getSelectedItem(),
                Double.parseDouble(priceField.getText()));
        transactionController.createInventoryTransaction(newTransaction);

        tableModel.addRow(new Object[] { newTransaction.getTransactionID(),
                findProductNameById(newTransaction.getProductID()), newTransaction.getQuantity(),
                newTransaction.getDate(), newTransaction.getTransactionType(), newTransaction.getPrice() });
    }

    private void showUpdateTransactionDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        String transtionID = tableModel.getValueAt(selectedRow, 0).toString();
        String productName = tableModel.getValueAt(selectedRow, 1).toString();
        int quantity = (int) tableModel.getValueAt(selectedRow, 2);
        Date date = (Date) tableModel.getValueAt(selectedRow, 3);
        TransactionType type = (TransactionType) tableModel.getValueAt(selectedRow, 4);
        double price = (double) tableModel.getValueAt(selectedRow, 5);

        JComboBox<String> productCombo = createProductComboBox();
        productCombo.setSelectedItem(productName);
        JTextField quantityField = new JTextField(String.valueOf(quantity), 10);
        JTextField dateField = new JTextField(date.toString(), 10);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setSelectedItem(type);
        JTextField priceField = new JTextField(String.valueOf(price), 10);

        JPanel panel = createTransactionDialogPanel(productCombo, quantityField, dateField, typeCombo, priceField);

        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_TRANSACTION_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateTransaction(selectedRow, productCombo, quantityField, dateField, typeCombo, priceField,
                    transtionID);
        }
    }

    private void handleUpdateTransaction(int selectedRow, JComboBox<String> productCombo, JTextField quantityField,
            JTextField dateField, JComboBox<TransactionType> typeCombo, JTextField priceField, String transationID) {
        String productID = findProductIDByName((String) productCombo.getSelectedItem());
        Product product = productController.getProductByID(productID);

        InventoryTransaction updatedTransaction = new InventoryTransaction("", product,
                Integer.parseInt(quantityField.getText()), new Date(), (TransactionType) typeCombo.getSelectedItem(),
                Double.parseDouble(priceField.getText()));
        transactionController.updateInventoryTransaction(updatedTransaction);

        tableModel.setValueAt(findProductNameById(updatedTransaction.getProductID()), selectedRow, 0);
        tableModel.setValueAt(updatedTransaction.getQuantity(), selectedRow, 1);
        tableModel.setValueAt(updatedTransaction.getDate(), selectedRow, 2);
        tableModel.setValueAt(updatedTransaction.getTransactionType(), selectedRow, 3);
        tableModel.setValueAt(updatedTransaction.getPrice(), selectedRow, 4);
    }

    private String findProductIDByName(String productName) {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            if (entry.getValue().getName().equals(productName)) {
                return entry.getKey();
            }
        }
        return null;
    }
}