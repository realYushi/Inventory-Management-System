package me.yushi.inventorymanagementsystem.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.model.Supplier;
import net.miginfocom.swing.MigLayout;

public class SuppliersPanel extends JPanel {
    // Constants, messages and variables
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this supplier?";
    private static final String DELETE_SUPPLIER_TITLE = "Delete Supplier";
    private static final String SELECT_SUPPLIER_TO_DELETE = "Please select a Supplier to delete.";
    private static final String SELECT_SUPPLIER_TO_UPDATE = "Please select a Supplier to update.";
    private static final String CREATE_NEW_SUPPLIER_TITLE = "Create New Supplier";
    private static final String UPDATE_SUPPLIER_TITLE = "Update Supplier";
    private static final String[] COLUMN_NAMES = { "Supplier ID", "Supplier Name" };

    private List<Supplier> suppliers;
    private SupplierController supplierController;
    private DefaultTableModel tableModel;
    private JTable supplierTable;

    public SuppliersPanel(SupplierController supplierController) {
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
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
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

        addButton.addActionListener(e -> showCreateSupplierDialog());
        deleteButton.addActionListener(e -> showDeleteConfirmationDialog());
        updateButton.addActionListener(e -> showUpdateSupplierDialog());

        return buttonPanel;
    }

    // Show a confirmation dialog before deleting a supplier
    private void showDeleteConfirmationDialog() {
        // Get the selected row
        int selectedRow = supplierTable.getSelectedRow();
        // If no row is selected, show an error message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_SUPPLIER_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the supplier ID from the selected row
        String supplierID = tableModel.getValueAt(selectedRow, 0).toString();
        // Check if the supplier is linked to any products
        if (supplierController.haveLinkedProduct(supplierID)) {
            JOptionPane.showMessageDialog(this, "Cannot delete supplier with linked products.", ERROR_MESSAGE,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Show a confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_SUPPLIER_TITLE,
                JOptionPane.YES_NO_OPTION);
        // If the user confirms the deletion, delete the supplier and refresh the data
        if (result == JOptionPane.YES_OPTION) {
            supplierController.deleteSupplier(supplierID);
            refreshData();
        }
    }

    private void showCreateSupplierDialog() {
        JTextField supplierIDField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        // Create a panel with the input fields
        JPanel panel = createSupplierDialogPanel(supplierIDField, nameField);
        // Show a dialog with the panel, and handle the input
        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_SUPPLIER_TITLE,
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateSupplier(supplierIDField, nameField);
        }
    }

    // Create a panel with input fields for supplier ID and name
    private JPanel createSupplierDialogPanel(JTextField supplierIDField, JTextField nameField) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Name:"), "wrap");
        panel.add(nameField, "wrap");
        return panel;
    }

    // Handle the input from the create supplier dialog
    private void handleCreateSupplier(JTextField supplierIDField, JTextField nameField) {
        if (!validateSupplierFields(nameField)) {
            return;
        }
        // New supplier object, no supplierID, will be generated by the model
        Supplier newSupplier = new Supplier("", nameField.getText());
        supplierController.createSupplier(newSupplier);
        refreshData();
    }

    private void showUpdateSupplierDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        // If no row is selected, show an error message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_SUPPLIER_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Get the supplier ID and name from the selected row
        String supplierID = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        JTextField supplierIDField = new JTextField(supplierID, 10);
        JTextField nameField = new JTextField(name, 10);
        JPanel panel = createSupplierDialogPanel(supplierIDField, nameField);
        // Show a dialog with the panel, and handle the input
        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_SUPPLIER_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateSupplier(selectedRow, supplierIDField, nameField);
        }
    }

    // Handle the input from the update supplier dialog
    private void handleUpdateSupplier(int selectedRow, JTextField supplierIDField, JTextField nameField) {
        if (!validateSupplierFields(nameField)) {
            return;
        }
        Supplier updatedSupplier = new Supplier(supplierIDField.getText(), nameField.getText());
        supplierController.updateSupplier(updatedSupplier);
        refreshData();
    }

    public void refreshData() {
        this.suppliers = supplierController.getAllSuppliers();
        // Clear the table and add the suppliers to the table model
        tableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[] { supplier.getSupplierID(), supplier.getSupplierName() });
        }
    }

    private boolean validateSupplierFields(JTextField nameField) {
        // Check if the field is empty
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}