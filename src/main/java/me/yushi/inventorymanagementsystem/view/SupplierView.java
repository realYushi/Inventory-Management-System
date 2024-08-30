package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.gui2.table.Table;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.contoller.ISupplierController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

public class SupplierView extends Panel implements ISelectedble{

    private ISupplierController controller;
    private Table<String> supplierTable;
    private WindowBasedTextGUI textGUI;
    // The index of the selected row in the table
    private int selectedRow = -1;

    public SupplierView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new SupplierController(unitOfWork);
        this.textGUI = textGUI;
        // Setup the UI
        setupUI();
        // Load the suppliers
        loadSuppliers();
        // Add the table selection listener
        addTableSelectionListener();
    }

    private void setupUI() {
        // Create the main panel
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Add a label to the main panel
        mainPanel.addComponent(new Label("Supplier Management")
                .setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        // Create the table
        supplierTable = new Table<>(" ", "ID", "Name");
        mainPanel.addComponent(supplierTable);

        // Create the button panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        // Create the buttons
        Button refreshButton = new Button("Refresh", this::loadSuppliers);
        buttonPanel.addComponent(refreshButton);

        // Add the buttons to the button panel
        Button addSupplierButton = new Button("Add Supplier", this::addSupplier);
        buttonPanel.addComponent(addSupplierButton);

        // Add the button panel to the main panel
        Button updateSupplierButton = new Button("Update Supplier", this::updateSupplier);
        buttonPanel.addComponent(updateSupplierButton);

        // Add the button panel to the main panel
        Button deleteSupplierButton = new Button("Delete Supplier", this::deleteSupplier);
        buttonPanel.addComponent(deleteSupplierButton);

        // Add the button panel to the main panel
        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadSuppliers() {
        supplierTable.getTableModel().clear();
        // Get all suppliers
        List<SupplierDto> suppliers = controller.getAllSuppliers();
        // Add the suppliers to the table
        if (suppliers.isEmpty()) {
            return;
        }
        // Add the suppliers to the table
        for (SupplierDto supplier : suppliers) {
            supplierTable.getTableModel().addRow("", supplier.getSupplierID(), supplier.getSupplierName());
        }
        selectedRow = -1;
    }

    private void addSupplier() {
        // Show a dialog to get the supplier name
        String supplierName = TextInputDialog.showDialog(textGUI, "Add Supplier", "Supplier Name:", "");
        if (supplierName == null || supplierName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid Input", MessageDialogButton.OK);
            return;

        }
        // Create a new supplier
        SupplierDto supplier = new SupplierDto.Builder()
                .supplierName(supplierName)
                .build();
        SupplierDto newSupplier = controller.createSupplier(supplier);
        if (newSupplier != null) {
            loadSuppliers();
            MessageDialog.showMessageDialog(textGUI, "Success", "Supplier added successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add supplier.", MessageDialogButton.OK);
        }
        // Clear the selection
        selectedRow = -1;
    }

    private void updateSupplier() {
        // Get the selected row
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No supplier selected.", MessageDialogButton.OK);
            return;
        }
        // Get the supplier ID form the selected row
        String supplierID = supplierTable.getTableModel().getRow(selectedRow).get(1);

        // Show a dialog to get the new supplier name
        String newSupplierName = TextInputDialog.showDialog(textGUI, "Update Supplier", "New Supplier Name:", "");
        if (newSupplierName == null || newSupplierName.trim().isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid Input", MessageDialogButton.OK);
            return;
        }
        // Create the updated supplier
        SupplierDto updatedSupplier = new SupplierDto.Builder()
                .supplierID(supplierID)
                .supplierName(newSupplierName)
                .build();
        SupplierDto result = controller.updateSupplier(updatedSupplier);
        if (result != null) {
            loadSuppliers();
            MessageDialog.showMessageDialog(textGUI, "Success", "Supplier updated successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update supplier.", MessageDialogButton.OK);
        }
        // Clear the selection
        selectedRow = -1;
    }

    private void deleteSupplier() {
        // Get the selected row
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No supplier selected.", MessageDialogButton.OK);
            return;
        }
        // Get the supplier ID form the selected row
        String supplierID = supplierTable.getTableModel().getRow(selectedRow).get(1);
        boolean result = controller.deleteSupplier(supplierID);
        if (result) {
            loadSuppliers();
            MessageDialog.showMessageDialog(textGUI, "Success", "Supplier deleted successfully!",
                    MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete supplier.", MessageDialogButton.OK);
        }
        // Clear the selection
        selectedRow = -1;
    }

    @Override
    public void addTableSelectionListener() {
        supplierTable.setSelectAction(() -> {
            selectedRow = supplierTable.getSelectedRow();
            // Clear the selection marker from all rows
            for (int row = 0; row < supplierTable.getTableModel().getRowCount(); row++) {
                supplierTable.getTableModel().setCell(0, row, "");
            }
            if (selectedRow >= 0 && selectedRow < supplierTable.getTableModel().getRowCount()) {
                // Set the selection marker on the selected row
                supplierTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }
}