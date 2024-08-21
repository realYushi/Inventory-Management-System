package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
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
        this.setLayoutManager(new LinearLayout(Direction.VERTICAL).setSpacing(1)); // Set spacing for better layout

        // Financial Summary
        Panel financialSummaryPanel = new Panel(new GridLayout(2).setHorizontalSpacing(10)); // Set horizontal spacing
        financialSummaryPanel.addComponent(new Label("Total Sales:"));// Bold label for clarity
        salesLabel = new Label("");
        financialSummaryPanel.addComponent(salesLabel);


        financialSummaryPanel.addComponent(new Label("Total Cost:")); // Bold label for clarity
        
        costLabel = new Label("");
        financialSummaryPanel.addComponent(costLabel);

        this.addComponent(financialSummaryPanel);
        this.addComponent(new EmptySpace(new TerminalSize(0, 1))); // Add space between sections

        // Inventory Summary
        Panel inventorySummaryPanel = new Panel(new GridLayout(2)); // Consistent spacing
        inventorySummaryPanel.addComponent(new Label("Low Stock Items:").addStyle(SGR.BOLD)); // Bold label
        lowStockLabel = new Label("");
        inventorySummaryPanel.addComponent(lowStockLabel);

        this.addComponent(inventorySummaryPanel);

        // Low Stock Info Panel
        lowStockInfoPanel = new Panel(new LinearLayout(Direction.VERTICAL).setSpacing(1)); // Set spacing
        lowStockInfoPanel.addComponent(new Label("Low Stock Items Details:").addStyle(SGR.BOLD)); // Bold label
        this.addComponent(lowStockInfoPanel);
    }

    private void updateSummaries() {
        try {
            FinancialSummary financialSummary = controller.getFinancialSummary();
            InventorySummary inventorySummary = controller.getInventorySummary();

            salesLabel.setText(String.format("%.2f $", financialSummary.getTotalSales()));
            costLabel.setText(String.format("%.2f $", financialSummary.getTotalCost()));
            lowStockLabel.setText(inventorySummary.getLowStrockProducts().size() + " items");

            lowStockInfoPanel.removeAllComponents();
            Table<String> lowStockTable = new Table<>("ID", "Name", "Quantity");

            for (Product product : inventorySummary.getLowStrockProducts()) {
                lowStockTable.getTableModel().addRow(product.getProductID(), product.getName(), String.valueOf(product.getQuantity()));
            }
            lowStockInfoPanel.addComponent(lowStockTable);
        } catch (Exception e) {
            this.addComponent(new Label("Error loading data."));
        }
    }
}