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

import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.model.Supplier;
import net.miginfocom.swing.MigLayout;

public class SuppliersPanel extends JPanel {
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this supplier?";
    private static final String DELETE_SUPPLIER_TITLE = "Delete Supplier";
    private static final String SELECT_SUPPLIER_TO_DELETE = "Please select a Supplier to delete.";
    private static final String SELECT_SUPPLIER_TO_UPDATE = "Please select a Supplier to update.";
    private static final String CREATE_NEW_SUPPLIER_TITLE = "Create New Supplier";
    private static final String UPDATE_SUPPLIER_TITLE = "Update Supplier";

    private List<Supplier> suppliers;
    private SupplierController supplierController;
    private DefaultTableModel tableModel;
    private JTable supplierTable;

    public SuppliersPanel(SupplierController supplierController) {
        this.supplierController = supplierController;
        this.suppliers = supplierController.getAllSuppliers();

        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));

        String[] columnNames = { "Supplier ID", "Supplier Name" };
        Object[][] data = new Object[suppliers.size()][2];
        for (int i = 0; i < suppliers.size(); i++) {
            data[i][0] = suppliers.get(i).getSupplierID();
            data[i][1] = suppliers.get(i).getSupplierName();
        }
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        this.add(scrollPane, "cell 0 0, grow");

        JPanel buttonPanel = createButtonPanel();
        this.add(buttonPanel, "cell 0 1, grow");
    }

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

    private void showDeleteConfirmationDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_SUPPLIER_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String supplierID = tableModel.getValueAt(selectedRow, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_SUPPLIER_TITLE, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            supplierController.deleteSupplier(supplierID);
            refreshData();
        }
    }

    private void showCreateSupplierDialog() {
        JTextField supplierIDField = new JTextField(10);
        JTextField nameField = new JTextField(10);

        JPanel panel = createSupplierDialogPanel(supplierIDField, nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_SUPPLIER_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateSupplier(supplierIDField, nameField);
        }
    }

    private JPanel createSupplierDialogPanel(JTextField supplierIDField, JTextField nameField) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Name:"), "wrap");
        panel.add(nameField, "wrap");
        return panel;
    }

    private void handleCreateSupplier(JTextField supplierIDField, JTextField nameField) {
        Supplier newSupplier = new Supplier("", nameField.getText());
        supplierController.createSupplier(newSupplier);
        refreshData();

        
    }

    private void showUpdateSupplierDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_SUPPLIER_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String supplierID = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();

        JTextField supplierIDField = new JTextField(supplierID, 10);
        JTextField nameField = new JTextField(name, 10);

        JPanel panel = createSupplierDialogPanel(supplierIDField, nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_SUPPLIER_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateSupplier(selectedRow, supplierIDField, nameField);
        }
    }

    private void handleUpdateSupplier(int selectedRow, JTextField supplierIDField, JTextField nameField) {
        Supplier updatedSupplier = new Supplier(supplierIDField.getText(), nameField.getText());

        supplierController.updateSupplier(updatedSupplier);
        refreshData();

        
    }
    public void refreshData() {
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        tableModel.setRowCount(0);

        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[] { supplier.getSupplierID(), supplier.getSupplierName() });
        }
    }
}
