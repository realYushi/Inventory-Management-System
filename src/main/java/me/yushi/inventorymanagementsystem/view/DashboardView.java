package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

public class DashboardView extends Panel {

    private DashBoardController controller;
    private Label salesLabel;
    private Label costLabel;
    private Label netProfitLabel;
    private Label grossMarginPercentageLabel;
    private Label lowStockLabel;
    private Panel lowStockInfoPanel;

    public DashboardView(IUnitOfWork unitOfWork) {
        this.controller = new DashBoardController(unitOfWork);
        setupUI();
        updateSummaries();
    }

    private void setupUI() {
        // financial summary panel
        this.setLayoutManager(new LinearLayout(Direction.VERTICAL).setSpacing(1));
        Panel financialSummaryPanel = new Panel(new GridLayout(2).setHorizontalSpacing(10));
        // total sales
        financialSummaryPanel.addComponent(new Label("Total Sales:"));
        salesLabel = new Label("");
        financialSummaryPanel.addComponent(salesLabel);
        // total cost
        financialSummaryPanel.addComponent(new Label("Total Cost:"));
        costLabel = new Label("");
        financialSummaryPanel.addComponent(costLabel);
        // net profit
        financialSummaryPanel.addComponent(new Label("Net Profit:"));
        netProfitLabel = new Label("");
        financialSummaryPanel.addComponent(netProfitLabel);
        // gross margin percentage
        financialSummaryPanel.addComponent(new Label("Gross Margin Percentage:"));
        grossMarginPercentageLabel = new Label("");
        financialSummaryPanel.addComponent(grossMarginPercentageLabel);
        // Add financial summary panel
        this.addComponent(financialSummaryPanel);
        this.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        // Inventory Summary panel
        Panel inventorySummaryPanel = new Panel(new GridLayout(2));
        // Low Stock Items
        inventorySummaryPanel.addComponent(new Label("Low Stock Items:"));
        lowStockLabel = new Label("");
        inventorySummaryPanel.addComponent(lowStockLabel);
        // Add Inventory Summary Panel
        this.addComponent(inventorySummaryPanel);

        // Low Stock Info Panel
        lowStockInfoPanel = new Panel(new LinearLayout(Direction.VERTICAL).setSpacing(1));
        lowStockInfoPanel.addComponent(new Label("Low Stock Items Details:"));
        this.addComponent(lowStockInfoPanel);
    }

    // Update summaries
    private void updateSummaries() {
        try {
            FinancialSummary financialSummary = controller.getFinancialSummary();
            InventorySummary inventorySummary = controller.getInventorySummary();

            // Update financial labels
            salesLabel.setText(String.format("%.2f $", financialSummary.getTotalSales()));
            costLabel.setText(String.format("%.2f $", financialSummary.getTotalCost()));
            netProfitLabel.setText(String.format("%.2f $", financialSummary.getNetProfit()));
            grossMarginPercentageLabel.setText(String.format("%.2f %%", financialSummary.getGrossMarginPercentage()));

            // Update inventory labels
            lowStockLabel.setText(inventorySummary.getLowStrockProducts().size() + " items");
            // Update low stock table
            lowStockInfoPanel.removeAllComponents();
            Table<String> lowStockTable = new Table<>("ID", "Name", "Quantity");
            for (Product product : inventorySummary.getLowStrockProducts()) {
                lowStockTable.getTableModel().addRow(product.getProductID(), product.getName(),
                        String.valueOf(product.getQuantity()));
            }
            lowStockInfoPanel.addComponent(lowStockTable);
        } catch (Exception e) {
            this.addComponent(new Label("Error loading data."));
        }
    }
}