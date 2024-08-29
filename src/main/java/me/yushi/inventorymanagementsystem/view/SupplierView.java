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

public class SupplierView extends Panel {

    private ISupplierController controller;
    private Table<String> supplierTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public SupplierView(IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        this.controller = new SupplierController(unitOfWork);
        this.textGUI = textGUI;
        setupUI();
        loadSuppliers();
        addTableSelectionListener();
    }

    private void setupUI() {
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(new Label("Supplier Management").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        supplierTable = new Table<>(" ", "ID", "Name"); 
        mainPanel.addComponent(supplierTable);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Button refreshButton = new Button("Refresh", this::loadSuppliers);
        buttonPanel.addComponent(refreshButton);

        Button addSupplierButton = new Button("Add Supplier", this::addSupplier);
        buttonPanel.addComponent(addSupplierButton);

        Button updateSupplierButton = new Button("Update Supplier", this::updateSupplier);
        buttonPanel.addComponent(updateSupplierButton);

        Button deleteSupplierButton = new Button("Delete Supplier", this::deleteSupplier);
        buttonPanel.addComponent(deleteSupplierButton);

        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadSuppliers() {
        supplierTable.getTableModel().clear();
        List<SupplierDto> suppliers = controller.getAllSuppliers();
        if (suppliers.size()==0) {
            return;
            
        }
        for (SupplierDto supplier : suppliers) {
            supplierTable.getTableModel().addRow("", supplier.getSupplierID(), supplier.getSupplierName() );
        }
        selectedRow= -1;
    }

    private void addSupplier() {
        String supplierName = TextInputDialog.showDialog(textGUI, "Add Supplier", "Supplier Name:", "");
        if (supplierName != null && !supplierName.trim().isEmpty()) {
            SupplierDto supplier = new SupplierDto.Builder()
                    .supplierName(supplierName)
                    .build();
            SupplierDto newSupplier = controller.createSupplier(supplier);
            if (newSupplier != null) {
                loadSuppliers();
                MessageDialog.showMessageDialog(textGUI, "Success", "Supplier added successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add supplier.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
        selectedRow= -1;
    }

    private void updateSupplier() {
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No supplier selected.", MessageDialogButton.OK);
            return;
        }
        String supplierID = supplierTable.getTableModel().getRow(selectedRow).get(1);
        String supplierName = supplierTable.getTableModel().getRow(selectedRow).get(2);
        String newSupplierName = TextInputDialog.showDialog(textGUI, "Update Supplier", "New Supplier Name:", supplierName);
        if (newSupplierName != null && !newSupplierName.trim().isEmpty()) {
            SupplierDto updatedSupplier = new SupplierDto.Builder()
                    .supplierID(supplierID)
                    .supplierName(newSupplierName)
                    .build();
            SupplierDto result = controller.updateSupplier(updatedSupplier);
            if (result != null) {
                loadSuppliers();
                MessageDialog.showMessageDialog(textGUI, "Success", "Supplier updated successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update supplier.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void deleteSupplier() {
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No supplier selected.", MessageDialogButton.OK);
            return;
        }
        String supplierID = supplierTable.getTableModel().getRow(selectedRow).get(1);
        boolean result = controller.deleteSupplier(supplierID);
        if (result) {
            loadSuppliers();
            MessageDialog.showMessageDialog(textGUI, "Success", "Supplier deleted successfully!", MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete supplier.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void addTableSelectionListener() {
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