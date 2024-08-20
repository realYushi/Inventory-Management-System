package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.gui2.table.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

public class InventoryTransactionView extends Panel {

    private InventoryTransactionController controller;
    private Table<String> transactionTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public InventoryTransactionView(InventoryTransactionRepository transactionRepository, ProductRepository productRepository, WindowBasedTextGUI textGUI) {
        this.controller = new InventoryTransactionController(transactionRepository, productRepository);
        this.textGUI = textGUI;
        setupUI();
        loadTransactions();
        addTableSelectionListener();
    }

    private void setupUI() {
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(new Label("Inventory Transactions").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        transactionTable = new Table<>(" ", "Transaction ID", "Product ID", "Product Name", "Quantity", "Date", "Type", "Price");
        mainPanel.addComponent(transactionTable);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Button refreshButton = new Button("Refresh", this::loadTransactions);
        buttonPanel.addComponent(refreshButton);

        Button addTransactionButton = new Button("Add Transaction", this::addTransaction);
        buttonPanel.addComponent(addTransactionButton);

        Button updateTransactionButton = new Button("Update Transaction", this::updateTransaction);
        buttonPanel.addComponent(updateTransactionButton);

        Button deleteTransactionButton = new Button("Delete Transaction", this::deleteTransaction);
        buttonPanel.addComponent(deleteTransactionButton);

        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadTransactions() {
        transactionTable.getTableModel().clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<InventoryTransactionDto> transactions = controller.getAllInventoryTransations();
        System.out.print(transactions.get(0).getTransactionID());
        for (InventoryTransactionDto transaction : transactions) {
            String productName = controller.getProduct(transaction.getProductID()).getName();
            String formattedDate = dateFormat.format(transaction.getDate());
            transactionTable.getTableModel().addRow("",
                    transaction.getTransactionID(),
                    transaction.getProductID(),
                    productName,
                    String.valueOf(transaction.getQuantity()),
                    formattedDate,
                    transaction.getTransactionType().toString(),
                    String.format("%.2f $", transaction.getPrice())
            );
        }
    }

    private void addTransaction() {
        String productID = selectProduct();
        if (productID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        String quantity = TextInputDialog.showDialog(textGUI, "Add Transaction", "Quantity:", "");
        TransactionType type = selectTransactionType();

        if (type == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction type selected.", MessageDialogButton.OK);
            return;
        }
        Date newDate = requestDateInput();
        if (!quantity.trim().isEmpty()) {
            InventoryTransactionDto transaction = new InventoryTransactionDto.Builder()
                    .date(newDate)
                    .productID(productID)
                    .quantity(Integer.parseInt(quantity))
                    .price(controller.getProduct(productID).getPrice())
                    .transactionType(type)
                    .build();
            InventoryTransactionDto newTransaction = controller.createInventoryTransaction(transaction);
            if (newTransaction != null) {
                loadTransactions();
                MessageDialog.showMessageDialog(textGUI, "Success", "Transaction added successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add transaction.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
    }

    private void updateTransaction() {
        selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionTable.getTableModel().getRow(selectedRow).get(1);
        String newQuantity = TextInputDialog.showDialog(textGUI, "Update Transaction", "New Quantity:", "");
        if (transactionID == null || transactionID.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Quantity is required.", MessageDialogButton.OK);
            return;
        }
        TransactionType newType = selectTransactionType();
        double price = controller.getInventoryTransactionByID(transactionID).getPrice();
        Date newDate = requestDateInput();
        if (newQuantity != null && !newQuantity.trim().isEmpty()) {
            InventoryTransactionDto updatedTransaction = new InventoryTransactionDto.Builder()
                    .date(newDate)
                    .productID(controller.getInventoryTransactionByID(transactionID).getProductID())
                    .transactionID(transactionID)
                    .quantity(Integer.parseInt(newQuantity))
                    .price(price)
                    .transactionType(newType)
                    .build();
            InventoryTransactionDto result = controller.updateInventoryTransaction(updatedTransaction);
            if (result != null) {
                loadTransactions();
                MessageDialog.showMessageDialog(textGUI, "Success", "Transaction updated successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update transaction.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void deleteTransaction() {
        selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionTable.getTableModel().getRow(selectedRow).get(0);
        boolean result = controller.deleteInventoryTransaction(transactionID);
        if (result) {
            loadTransactions();
            MessageDialog.showMessageDialog(textGUI, "Success", "Transaction deleted successfully!", MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete transaction.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void addTableSelectionListener() {
        transactionTable.setSelectAction(() -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow != -1) {
                for (int row = 0; row < transactionTable.getTableModel().getRowCount(); row++) {
                    transactionTable.getTableModel().setCell(0, row, "");
                }
                transactionTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

    private String selectProduct() {
        List<ProductDto> productDtos = controller.getAllProduct();
        if (productDtos.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Product", "There are no products available.", MessageDialogButton.OK);
            return null;
        }
        String[] productNames = productDtos.stream().map(ProductDto::getName).toArray(String[]::new);
        String selectedProductName = ListSelectDialog.showDialog(textGUI, "Select Product", "Choose a Product:", productNames);
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
        String[] options = {"SALE", "PURCHASE", "SPOILAGE"};
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
            String dateInput = TextInputDialog.showDialog(textGUI, "Update Transaction", "Enter Date (dd-MM-yyyy):", "");
            if (dateInput == null || dateInput.trim().isEmpty()) {
                MessageDialog.showMessageDialog(textGUI, "Error", "No date entered.", MessageDialogButton.OK);
                return null;
            }
            try {
                return dateFormat.parse(dateInput.trim());
            } catch (ParseException e) {
                MessageDialog.showMessageDialog(textGUI, "Error", "Invalid date format. Please use dd-MM-yyyy.", MessageDialogButton.OK);
            }
        }
    }
}
