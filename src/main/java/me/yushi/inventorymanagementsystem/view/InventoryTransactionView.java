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

public class InventoryTransactionView extends Panel {

    private InventoryTransactionController controller;
    private Table<String> transactionTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;
    private Map<Integer, String> transactionIdMap = new HashMap<>();

    public InventoryTransactionView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new InventoryTransactionController(unitOfWork);
        this.textGUI = textGUI;
        setupUI();
        loadTransactions();
        addTableSelectionListener();
    }

    private void setupUI() {
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(new Label("Inventory Transactions").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        transactionTable = new Table<>(" ", "Product ID", "Product Name", "Quantity", "Date", "Type", "Price");
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
        int rowIndex = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        List<InventoryTransactionDto> transactions = controller.getAllInventoryTransations();
        if (transactions.size() == -1) {
            return;
        }
        for (InventoryTransactionDto transaction : transactions) {
            String productID=transaction.getProductID();
            ProductDto product = controller.getProduct(productID);
            if(product==null){
                continue;
            }
                
            String productName = product.getName();
            String formattedDate = dateFormat.format(transaction.getDate());
            transactionTable.getTableModel().addRow("",
                    transaction.getProductID(),
                    productName,
                    String.valueOf(transaction.getQuantity()),
                    formattedDate,
                    transaction.getTransactionType().toString(),
                    String.format("%.2f $", transaction.getPrice())
            );
            transactionIdMap.put(rowIndex, transaction.getTransactionID());
            rowIndex++;
        }
        selectedRow= -1;
    }

    private void addTransaction() {
        String productID = selectProduct();
        if (productID == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }

        TransactionType type = selectTransactionType();
        if (type == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction type selected.", MessageDialogButton.OK);
            return;
        }
        String quantity = TextInputDialog.showDialog(textGUI, "Add Transaction", "Quantity:", "");
        Date newDate = requestDateInput();
        if (!quantity.trim().isEmpty()) {
            int Iquantity = Integer.parseInt(quantity);
            InventoryTransactionDto transaction = new InventoryTransactionDto.Builder()
                    .date(newDate)
                    .productID(productID)
                    .quantity(Iquantity)
                    .price(controller.getProduct(productID).getPrice() * Iquantity)
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
        selectedRow= -1;
    }

    private void updateTransaction() {
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionIdMap.get(selectedRow);
        String newQuantity = TextInputDialog.showDialog(textGUI, "Update Transaction", "New Quantity:", "");
        if (newQuantity == null || newQuantity.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Quantity is required.", MessageDialogButton.OK);
            return;
        }
        TransactionType newType = selectTransactionType();
         if (newType == null) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction type selected.", MessageDialogButton.OK);
            return;
        }
        double price = controller.getInventoryTransactionByID(transactionID).getPrice();
        Date newDate = requestDateInput();
        if (newQuantity != null && !newQuantity.trim().isEmpty()) {
            int quantity = Integer.parseInt(newQuantity);
            InventoryTransactionDto updatedTransaction = new InventoryTransactionDto.Builder()
                    .date(newDate)
                    .productID(controller.getInventoryTransactionByID(transactionID).getProductID())
                    .transactionID(transactionID)
                    .quantity(quantity)
                    .price(price * quantity)
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
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No transaction selected.", MessageDialogButton.OK);
            return;
        }
        String transactionID = transactionIdMap.get(selectedRow);
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
