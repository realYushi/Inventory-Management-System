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

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.model.Category;
import net.miginfocom.swing.MigLayout;

public class CategoriesPanel extends JPanel {
    private static final String ERROR_MESSAGE = "Error";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this category?";
    private static final String DELETE_CATEGORY_TITLE = "Delete Category";
    private static final String SELECT_CATEGORY_TO_DELETE = "Please select a Category to delete.";
    private static final String SELECT_CATEGORY_TO_UPDATE = "Please select a Category to update.";
    private static final String CREATE_NEW_CATEGORY_TITLE = "Create New Category";
    private static final String UPDATE_CATEGORY_TITLE = "Update Category";

    private List<Category> categories;
    private CategoryController categoryController;
    private DefaultTableModel tableModel;
    private JTable categoryTable;

    public CategoriesPanel(CategoryController categoryController) {
        this.categoryController = categoryController;
        this.categories = categoryController.getAllCategorys();
        this.setLayout(new MigLayout("fill", "[grow]", "[80%][20%]"));

        String[] columnNames = { "Category ID", "Category Name" };
        Object[][] data = new Object[categories.size()][2];
        for (int i = 0; i < categories.size(); i++) {
            data[i][0] = categories.get(i).getCategoryID();
            data[i][1] = categories.get(i).getCategoryName();
        }
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        categoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(categoryTable);
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

        addButton.addActionListener(e -> showCreateCategoryDialog());
        deleteButton.addActionListener(e -> showDeleteConfirmationDialog());
        updateButton.addActionListener(e -> showUpdateCategoryDialog());

        return buttonPanel;
    }

    private void showDeleteConfirmationDialog() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_CATEGORY_TO_DELETE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoryID = tableModel.getValueAt(selectedRow, 0).toString();
        int result = JOptionPane.showConfirmDialog(this, DELETE_CONFIRMATION_MESSAGE, DELETE_CATEGORY_TITLE, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            categoryController.deleteCategory(categoryID);
            tableModel.removeRow(selectedRow);
        }
    }

    private void showCreateCategoryDialog() {
        JTextField categoryIDField = new JTextField(10);
        JTextField nameField = new JTextField(10);

        JPanel panel = createCategoryDialogPanel(categoryIDField, nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, CREATE_NEW_CATEGORY_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleCreateCategory(categoryIDField, nameField);
        }
    }

    private JPanel createCategoryDialogPanel(JTextField categoryIDField, JTextField nameField) {
        JPanel panel = new JPanel(new MigLayout());
        panel.add(new JLabel("Name:"), "wrap");
        panel.add(nameField, "wrap");
        return panel;
    }

    private void handleCreateCategory(JTextField categoryIDField, JTextField nameField) {
        Category newCategory = new Category("", nameField.getText());
        categoryController.createCategory(newCategory);

        tableModel.addRow(new Object[] {
                newCategory.getCategoryID(),
                newCategory.getCategoryName()
        });
    }

    private void showUpdateCategoryDialog() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, SELECT_CATEGORY_TO_UPDATE, ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoryID = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();

        JTextField categoryIDField = new JTextField(categoryID, 10);
        JTextField nameField = new JTextField(name, 10);

        JPanel panel = createCategoryDialogPanel(categoryIDField, nameField);

        int result = JOptionPane.showConfirmDialog(this, panel, UPDATE_CATEGORY_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            handleUpdateCategory(selectedRow, categoryIDField, nameField);
        }
    }

    private void handleUpdateCategory(int selectedRow, JTextField categoryIDField, JTextField nameField) {
        Category updatedCategory = new Category(categoryIDField.getText(), nameField.getText());

        categoryController.updateCategory(updatedCategory);

        tableModel.setValueAt(updatedCategory.getCategoryID(), selectedRow, 0);
        tableModel.setValueAt(updatedCategory.getCategoryName(), selectedRow, 1);
    }
}

