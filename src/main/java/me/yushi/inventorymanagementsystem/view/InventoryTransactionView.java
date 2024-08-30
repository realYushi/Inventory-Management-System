package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.gui2.table.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

public class InventoryTransactionView extends Panel implements ISelectedble{

    private InventoryTransactionController controller;
    private Table<String> transactionTable;
    private WindowBasedTextGUI textGUI;
    // The index of the selected row in the table, -1 if no row is selected
    private int selectedRow = -1;
    // A map that stores the transaction ID for each row in the table
    private Map<Integer, String> transactionIdMap = new HashMap<>();

    public InventoryTransactionView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new InventoryTransactionController(unitOfWork);
        this.textGUI = textGUI;
        // Setup the UI components
        setupUI();
        loadTransactions();
        addTableSelectionListener();
    }

    private void setupUI() {
        // Create the main panel
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Add a label to the main panel
        mainPanel.addComponent(new Label("Inventory Transactions")
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Create a table to display the transactions
        transactionTable = new Table<>(" ", "Product ID", "Product Name", "Quantity", "Date", "Type", "Price");
        mainPanel.addComponent(transactionTable);

        // Create a panel to hold the buttons
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Add a refresh button to the button panel
        Button refreshButton = new Button("Refresh", this::loadTransactions);
        buttonPanel.addComponent(refreshButton);

        // Add an "Add Transaction" button to the button panel
        Button addTransactionButton = new Button("Add Transaction", this::addTransaction);
        buttonPanel.addComponent(addTransactionButton);

        // Add an "Update Transaction" button to the button panel
        Button updateTransactionButton = new Button("Update Transaction", this::updateTransaction);
        buttonPanel.addComponent(updateTransactionButton);

        // Add a "Delete Transaction" button to the button panel
        Button deleteTransactionButton = new Button("Delete Transaction", this::deleteTransaction);
        buttonPanel.addComponent(deleteTransactionButton);

        // Add the button panel to the main panel
        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadTransactions() {
        // Clear the table
        transactionTable.getTableModel().clear();
        // Clear the transaction ID map
        int rowIndex = 0;
        // Create a date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        // Get all transactions from the controller
        List<InventoryTransactionDto> transactions = controller.getAllInventoryTransations();
        // If there are no transactions, return
        if (transactions.isEmpty()) {
            return;
        }
        // Add each transaction to the table
        for (InventoryTransactionDto transaction : transactions) {
            String productID = transaction.getProductID();
            ProductDto product = controller.getProduct(productID);
            // If the product does not exist, delete the transaction
            if (product == null) {
                controller.deleteInventoryTransaction(transaction.getTransactionID());
                continue;
            }
            // Get the product name
            String productName = product.getName();
            String formattedDate = dateFormat.format(transaction.getDate());
            // Add the transaction to the table
            transactionTable.getTableModel().addRow("",
                    transaction.getProductID(),
                    productName,
                    String.valueOf(transaction.getQuantity()),
                    formattedDate,
                    transaction.getTransactionType().toString(),
                    String.format("%.2f $", transaction.getPrice()));
            // Add the transaction ID to the map
            transactionIdMap.put(rowIndex, transaction.getTransactionID());
            // Increment the row index
            rowIndex++;
        }
        // Reset the selected row
        selectedRow = -1;
    }

    private void addTransaction() {
        // Select a product
        String productID = selectProduct();
        if (productID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        // Select a transaction type
        TransactionType type = selectTransactionType();
        if (type == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction type selected.", MessageDialogButton.OK);
            return;
        }

        String quantity = TextInputDialog.showDialog(textGUI, "Add Transaction", "Quantity:", "");
        if (quantity == null || quantity.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Quantity is required.", MessageDialogButton.OK);
            return;

        }
        Date newDate = requestDateInput();

        int parsedQuantity = Integer.parseInt(quantity);
        // add transaction
        InventoryTransactionDto transaction = new InventoryTransactionDto.Builder()
                .date(newDate)
                .productID(productID)
                .quantity(parsedQuantity)
                .price(controller.getProduct(productID).getPrice() * parsedQuantity)
                .transactionType(type)
                .build();
        InventoryTransactionDto newTransaction = controller.createInventoryTransaction(transaction);
        if (newTransaction != null) {
            loadTransactions();
            MessageDialog.showMessageDialog(textGUI, "Success", "Transaction added successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add transaction.", MessageDialogButton.OK);
        }

        // Reset the selected row
        selectedRow = -1;
    }

    private void updateTransaction() {
        // Check if a transaction is selected
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionIdMap.get(selectedRow);
        String newQuantity = TextInputDialog.showDialog(textGUI, "Update Transaction", "New Quantity:", "");
        // Check if the quantity is empty
        if (newQuantity == null || newQuantity.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Quantity is required.", MessageDialogButton.OK);
            return;
        }
        // Select a transaction type
        TransactionType newType = selectTransactionType();
        if (newType == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction type selected.", MessageDialogButton.OK);
            return;
        }
        double price = controller.getInventoryTransactionByID(transactionID).getPrice();
        Date newDate = requestDateInput();
        int parsedQuantity = Integer.parseInt(newQuantity);
        // Update the transaction
        InventoryTransactionDto updatedTransaction = new InventoryTransactionDto.Builder()
                .date(newDate)
                .productID(controller.getInventoryTransactionByID(transactionID).getProductID())
                .transactionID(transactionID)
                .quantity(parsedQuantity)
                .price(price * parsedQuantity)
                .transactionType(newType)
                .build();
        InventoryTransactionDto result = controller.updateInventoryTransaction(updatedTransaction);
        if (result != null) {
            loadTransactions();
            MessageDialog.showMessageDialog(textGUI, "Success", "Transaction updated successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update transaction.",
                    MessageDialogButton.OK);
        }
        // Reset the selected row
        selectedRow = -1;
    }

    private void deleteTransaction() {
        // Check if a transaction is selected
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionIdMap.get(selectedRow);
        // Delete the transaction
        boolean result = controller.deleteInventoryTransaction(transactionID);
        // Show a message dialog based on the result
        if (result) {
            loadTransactions();
            MessageDialog.showMessageDialog(textGUI, "Success", "Transaction deleted successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete transaction.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    @Override
    public void addTableSelectionListener() {
        transactionTable.setSelectAction(() -> {
            selectedRow = transactionTable.getSelectedRow();
            // Clear the selection marker from all rows
            for (int row = 0; row < transactionTable.getTableModel().getRowCount(); row++) {
                transactionTable.getTableModel().setCell(0, row, "");
            }
            if (selectedRow >= 0 && selectedRow < transactionTable.getTableModel().getRowCount()) {
                // Set the selection marker on the selected row
                transactionTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

    private String selectProduct() {
        List<ProductDto> productDtos = controller.getAllProduct();
        if (productDtos.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Product", "There are no products available.",
                    MessageDialogButton.OK);
            return null;
        }
        String[] productNames = productDtos.stream().map(ProductDto::getName).toArray(String[]::new);
        String selectedProductName = ListSelectDialog.showDialog(textGUI, "Select Product", "Choose a Product:",
                productNames);
        if (selectedProductName != null) {
            return productDtos.stream()
                    .filter(p -> p.getName().equals(selectedProductName))
                    .findFirst()
                    .map(ProductDto::getProductID)
                    .orElse(null);
        }
        return null;
    }

    private InventoryTransactionDto.TransactionType selectTransactionType() {
        String[] options = { "SALE", "PURCHASE", "SPOILAGE" };
        String choice = ListSelectDialog.showDialog(textGUI, "Select Transaction Type", "Choose a Type:", options);
        if (choice != null) {
            return TransactionType.valueOf(choice);
        }
        return null;
    }

    private Date requestDateInput() {
        String datePattern = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false); // Set lenient to false to strictly parse dates

        while (true) {
            String dateInput = TextInputDialog.showDialog(textGUI, "Update Transaction", "Enter Date (dd-MM-yyyy):",
                    "");
            if (dateInput == null || dateInput.trim().isEmpty()) {
                MessageDialog.showMessageDialog(textGUI, "Error", "No date entered.", MessageDialogButton.OK);
                return null;
            }
            try {
                return dateFormat.parse(dateInput.trim());
            } catch (ParseException e) {
                MessageDialog.showMessageDialog(textGUI, "Error", "Invalid date format. Please use dd-MM-yyyy.",
                        MessageDialogButton.OK);
            }
        }
    }
}
