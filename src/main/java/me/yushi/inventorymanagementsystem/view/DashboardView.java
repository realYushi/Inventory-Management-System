package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

public class DashboardView extends Panel {

    private DashBoardController controller;
    private WindowBasedTextGUI textGUI;
    private Label salesLabel;
    private Label costLabel;
    private Label lowStockLabel;
    private Panel lowStockInfoPanel;

    public DashboardView(InventoryTransactionRepository transactionRepository, ProductRepository productRepository, WindowBasedTextGUI textGUI) {
        this.controller = new DashBoardController(transactionRepository, productRepository);
        this.textGUI = textGUI;
        setupUI();
        updateSummaries();
    }

    private void setupUI() {
        this.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        // Financial Summary
        Panel financialSummaryPanel = new Panel(new GridLayout(2));
        financialSummaryPanel.addComponent(new Label("Total Sales:"));
        salesLabel = new Label("");
        financialSummaryPanel.addComponent(salesLabel);

        financialSummaryPanel.addComponent(new Label("Total Cost:"));
        costLabel = new Label("");
        financialSummaryPanel.addComponent(costLabel);

        this.addComponent(financialSummaryPanel);

        // Inventory Summary
        Panel inventorySummaryPanel = new Panel(new GridLayout(2));

        inventorySummaryPanel.addComponent(new Label("Low Stock Items:"));
        lowStockLabel = new Label("");
        inventorySummaryPanel.addComponent(lowStockLabel);

        this.addComponent(inventorySummaryPanel);

        // Low Stock Info Panel
        lowStockInfoPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        lowStockInfoPanel.addComponent(new Label("Low Stock Items Details:"));
        this.addComponent(lowStockInfoPanel);
    }

    private void updateSummaries() {
        FinancialSummary financialSummary = controller.getFinancialSummary();
        InventorySummary inventorySummary = controller.getInventorySummary();

        salesLabel.setText(String.format("%.2f $", financialSummary.getTotalSales()));
        costLabel.setText(String.format("%.2f $", financialSummary.getTotalCost()));
        lowStockLabel.setText(String.valueOf(inventorySummary.getLowStrockProducts().size()) + " items");
        lowStockInfoPanel.removeAllComponents();
        Table lowStockTable = new Table<>("ID", "Name", "Quantity");

        for (Product product : inventorySummary.getLowStrockProducts()) {

            lowStockTable.getTableModel().addRow(product.getProductID(), product.getName(), product.getQuantity());

        }
        lowStockInfoPanel.addComponent(lowStockTable);
    }
}
