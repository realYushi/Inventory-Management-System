package me.yushi.inventorymanagementsystem.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.jdesktop.swingx.JXDatePicker;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    private static final String[] COLUMN_NAMES = { "TransactionID", "Product", "Quantity", "Date", "Type" };

    private List<InventoryTransaction> transactions;
    private Map<String, Product> products;
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private InventoryTransactionController transactionController;
    private ProductController productController;

    public TransactionsPanel(InventoryTransactionController transactionController, ProductController productController) {
        this.transactionController = transactionController;
        this.productController = productController;
        initialUI();
         this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshData(); // Refresh data each time the panel is shown
            }
        });
        
    }

    private void initialUI() {
        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
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

        refreshData();
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

    private void showDeleteConfirmationDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_TRANSACTION_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String transactionID = tableModel.getValueAt(selectedRow, 0).toString();
            transactionController.deleteInventoryTransaction(transactionID);
            refreshData();
        }
    }

    private void showCreateTransactionDialog() {
        JComboBox<String> productCombo = createProductComboBox();
        JTextField quantityField = new JTextField(10);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        JXDatePicker datePicker = new JXDatePicker();
        datePicker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
        JPanel panel = createTransactionDialogPanel(productCombo, quantityField, datePicker, typeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_TRANSACTION_TITLE,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateTransaction(productCombo, quantityField, datePicker, typeCombo);
        }
    }

    private void showUpdateTransactionDialog() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_TRANSACTION_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String transactionID = tableModel.getValueAt(selectedRow, 0).toString();
        String productName = tableModel.getValueAt(selectedRow, 1).toString();
        String dataString = tableModel.getValueAt(selectedRow, 3).toString();
        int quantity = (int) tableModel.getValueAt(selectedRow, 2);
        Date date;
        try {
            date = dateFormat.parse(dataString);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error parsing date.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        TransactionType type = (TransactionType) tableModel.getValueAt(selectedRow, 4);

        JComboBox<String> productCombo = createProductComboBox();
        productCombo.setSelectedItem(productName);
        JTextField quantityField = new JTextField(String.valueOf(quantity), 10);
        JXDatePicker datePicker = new JXDatePicker();
        datePicker.setFormats(dateFormat);
        datePicker.setDate(date);
        JComboBox<TransactionType> typeCombo = new JComboBox<>(TransactionType.values());
        typeCombo.setSelectedItem(type);

        JPanel panel = createTransactionDialogPanel(productCombo, quantityField, datePicker, typeCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_TRANSACTION_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateTransaction(selectedRow, productCombo, quantityField, datePicker, typeCombo, transactionID);
        }
    }

    private JPanel createTransactionDialogPanel(JComboBox<String> productCombo, JTextField quantityField,
            JXDatePicker datePicker, JComboBox<TransactionType> typeCombo) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Product:"), "wrap");
        panel.add(productCombo, "wrap");
        panel.add(new JLabel("Quantity:"), "wrap");
        panel.add(quantityField, "wrap");
        panel.add(new JLabel("Date:"), "wrap");
        panel.add(datePicker, "wrap");
        panel.add(new JLabel("Type:"), "wrap");
        panel.add(typeCombo, "wrap");
        return panel;
    }

    private JComboBox<String> createProductComboBox() {
        JComboBox<String> productComboBox = new JComboBox<>();
        for (Product product : products.values()) {
            productComboBox.addItem(product.getName());
        }
        return productComboBox;
    }

    private void handleCreateTransaction(JComboBox<String> productCombo, JTextField quantityField, JXDatePicker datePicker,
            JComboBox<TransactionType> typeCombo) {
        String productID = findProductIDByName((String) productCombo.getSelectedItem());
        Product product = productController.getProductByID(productID);
        InventoryTransaction newTransaction = new InventoryTransaction("", product,
                Integer.parseInt(quantityField.getText()), datePicker.getDate(), (TransactionType) typeCombo.getSelectedItem(), product.getPrice());
        transactionController.createInventoryTransaction(newTransaction);
        refreshData();
    }

    private void handleUpdateTransaction(int selectedRow, JComboBox<String> productCombo, JTextField quantityField,
            JXDatePicker datePicker, JComboBox<TransactionType> typeCombo, String transactionID) {
        String productID = findProductIDByName((String) productCombo.getSelectedItem());
        Product product = productController.getProductByID(productID);

        InventoryTransaction updatedTransaction = new InventoryTransaction(transactionID, product,
                Integer.parseInt(quantityField.getText()), datePicker.getDate(), (TransactionType) typeCombo.getSelectedItem(), product.getPrice());
        transactionController.updateInventoryTransaction(updatedTransaction);
        refreshData();
    }

    private String findProductIDByName(String productName) {
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            if (entry.getValue().getName().equals(productName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String findProductNameById(String productID) {
        Product product = products.get(productID);
        return (product != null) ? product.getName() : "Unknown";
    }

    private void refreshData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        transactions = transactionController.getAllInventoryTransations();
        products = productController.getAllProducts().stream()
                .collect(Collectors.toMap(Product::getProductID, p -> p));

        tableModel.setRowCount(0);
        for (InventoryTransaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getTransactionID(),
                    findProductNameById(transaction.getProductID()),
                    transaction.getQuantity(),
                    dateFormat.format(transaction.getDate()),
                    transaction.getTransactionType()
            });
        }
    }
}
