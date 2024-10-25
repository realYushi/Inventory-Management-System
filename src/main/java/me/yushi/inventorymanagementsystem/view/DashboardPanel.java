package me.yushi.inventorymanagementsystem.view;

import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.util.List;

public class DashboardPanel extends JPanel {

    private static final String WRAP = "wrap";
    private static final String GROW_FILL = "[grow,fill]";
    private static final String INVENTORY_PANEL_LAYOUT = "[][]";
    private static final String FINANCIAL_PANEL_LAYOUT = "[][grow]";
    private static final String INVENTORY_PANEL_ROWS = "[]10[]10[]";
    private static final String FINANCIAL_PANEL_ROWS = "[]10[]";

    private DashBoardController controller;

    public DashboardPanel(DashBoardController controller) {
        this.controller = controller;
        setLayout(new MigLayout("", GROW_FILL, "[]20[]"));
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel inventoryPanel = createInventorySummaryPanel();
        JPanel financialPanel = createFinancialSummaryPanel();

        add(inventoryPanel, WRAP);
        add(financialPanel, WRAP);
    }

    private JPanel createInventorySummaryPanel() {
        JPanel inventoryPanel = new JPanel(new MigLayout("", INVENTORY_PANEL_LAYOUT, INVENTORY_PANEL_ROWS));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory Summary"));

        InventorySummary inventorySummary = controller.getInventorySummary();
        List<Product> lowStockProducts = inventorySummary.getLowStrockProducts();
        List<InventoryTransaction> recentTransactions = inventorySummary.getRecentInventoryTransactions();

        addLowStockProducts(inventoryPanel, lowStockProducts);
        addRecentTransactions(inventoryPanel, recentTransactions);

        return inventoryPanel;
    }

    private void addLowStockProducts(JPanel panel, List<Product> products) {
        panel.add(new JLabel("Low Stock Products:"), WRAP);
        for (Product product : products) {
            panel.add(new JLabel("Product: " + product.getName()), WRAP);
        }
    }

    private void addRecentTransactions(JPanel panel, List<InventoryTransaction> transactions) {
        panel.add(new JLabel("Recent Transactions:"), WRAP);
        for (InventoryTransaction transaction : transactions) {
            panel.add(new JLabel("Transaction ID: " + transaction.getTransactionID()), WRAP);
        }
    }

    private JPanel createFinancialSummaryPanel() {
        JPanel financialPanel = new JPanel(new MigLayout("", FINANCIAL_PANEL_LAYOUT, FINANCIAL_PANEL_ROWS));
        financialPanel.setBorder(BorderFactory.createTitledBorder("Financial Summary"));

        FinancialSummary financialSummary = controller.getFinancialSummary();

        addFinancialData(financialPanel, "Total Sales:", String.valueOf(financialSummary.getTotalSales()));
        addFinancialData(financialPanel, "Total Cost:", String.valueOf(financialSummary.getTotalCost()));
        addFinancialData(financialPanel, "Net Profit:", String.valueOf(financialSummary.getNetProfit()));
        addFinancialData(financialPanel, "Gross Margin Percentage:", financialSummary.getGrossMarginPercentage() + "%");

        return financialPanel;
    }

    private void addFinancialData(JPanel panel, String label, String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value), WRAP);
    }
}