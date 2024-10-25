package me.yushi.inventorymanagementsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import net.miginfocom.swing.MigLayout;

public class TransactionsPanel extends JPanel {
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this transaction?";
    private static final String DELETE_TRANSACTION_TITLE = "Delete Transaction";
    private static final String SELECT_TRANSACTION_TO_DELETE = "Please select a Transaction to delete.";
    private static final String SELECT_TRANSACTION_TO_UPDATE = "Please select a Transaction to update.";
    private static final String CREATE_NEW_TRANSACTION_TITLE = "Create New Transaction";
    private static final String UPDATE_TRANSACTION_TITLE = "Update Transaction";
    private static final String[] COLUMN_NAMES = {
        "Product", "Quantity", "Date", "Type", "Price"
    };
        
    private List<InventoryTransactionDto> transactions;
    private Map<String,ProductDto> products;
    private DefaultTableModel tableModel;
    private JTable transactionTable;

    public TransactionsPanel(List<InventoryTransactionDto> transactions, List<ProductDto> products) {
        this.transactions = transactions;
        this.products = products.stream().collect(Collectors.toMap(ProductDto::getProductID, p -> p));
        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));
        Object[][] data = new Object[transactions.size()][COLUMN_NAMES.length];
        for (int i = 0; i < transactions.size(); i++) {
            InventoryTransactionDto transaction = transactions.get(i);
            data[i] = new Object[]{
                transaction.getProductID(),
                transaction.getQuantity(),
                transaction.getDate(),
                transaction.getTransactionType(),
                transaction.getPrice()
            };
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
        Object[][] transformedData = new Object[data.length][7];
        for (int i = 0; i < data.length; i++) {
            transformedData[i][0] = findProductNameById((String)(data[i][0])); // Product Name 
            transformedData[i][1] = data[i][1]; // quantity 
            transformedData[i][2] = data[i][2]; // Date 
            transformedData[i][3] = data[i][3]; // Type 
            transformedData[i][4] = data[i][4]; // Price 
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
        ProductDto product= products.get(productID);
        return (product!= null) ? product.getName() : "Unknown";
    }
    private void showDeleteConfirmationDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_TRANSACTION_TITLE, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private JPanel createTransactionDialogPanel(JComboBox<String> productCombo,
            JTextField quantityField, JTextField dateField, JComboBox<TransactionType> typeCombo, 
            JTextField priceField) {
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
        JTextField productIDField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JTextField dateField = new JTextField(10);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        JComboBox<String> productCombo = createProductComboBox();
        JTextField priceField = new JTextField(10);

        JPanel panel = createTransactionDialogPanel(productCombo, 
            quantityField, dateField, typeCombo, priceField);

        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_TRANSACTION_TITLE, 
            JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateTransaction(productIDField, quantityField, 
                dateField, typeCombo, priceField);
        }
    }
    private JComboBox<String> createProductComboBox() {
        JComboBox<String> categoryComboBox = new JComboBox<>();
        for (ProductDto product: products.values()) {
            categoryComboBox.addItem(product.getName());
        }
        return categoryComboBox;
    }

    private void handleCreateTransaction(JTextField productIDField,
            JTextField quantityField, JTextField dateField, JComboBox<TransactionType> typeCombo,
            JTextField priceField) {
        tableModel.addRow(new Object[] {
            productIDField.getText(),
            Integer.parseInt(quantityField.getText()),
            new Date(),
            typeCombo.getSelectedItem(),
            Double.parseDouble(priceField.getText())
        });
    }

    private void showUpdateTransactionDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_UPDATE, ERROR_MESSAGE, 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField productIDField = new JTextField(tableModel.getValueAt(selectedRow, 0).toString(), 10);
        JTextField quantityField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString(), 10);
        JTextField dateField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString(), 10);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        JComboBox<String> productCombo = createProductComboBox();
        typeCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
        JTextField priceField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString(), 10);

        JPanel panel = createTransactionDialogPanel(productCombo,
            quantityField, dateField, typeCombo, priceField);

        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_TRANSACTION_TITLE,
            JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateTransaction(selectedRow, productIDField,
                quantityField, dateField, typeCombo, priceField);
        }
    }

    private void handleUpdateTransaction(int selectedRow,
            JTextField productIDField, JTextField quantityField, JTextField dateField,
            JComboBox<TransactionType> typeCombo, JTextField priceField) {
        tableModel.setValueAt(productIDField.getText(), selectedRow, 0);
        tableModel.setValueAt(Integer.parseInt(quantityField.getText()), selectedRow, 1);
        tableModel.setValueAt(new Date(), selectedRow, 2);
        tableModel.setValueAt(typeCombo.getSelectedItem(), selectedRow, 3);
        tableModel.setValueAt(Double.parseDouble(priceField.getText()), selectedRow, 4);
    }
}
