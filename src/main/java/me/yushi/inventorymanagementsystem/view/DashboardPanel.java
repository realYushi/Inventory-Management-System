package me.yushi.inventorymanagementsystem.view;

import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import net.miginfocom.swing.MigLayout;
import java.util.List;

public class DashboardPanel extends JPanel {

    private static final String INVENTORY_PANEL_LAYOUT = "fill";
    private static final String FINANCIAL_PANEL_LAYOUT = "fill";
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private DashBoardController controller;

    public DashboardPanel(DashBoardController controller) {
        this.controller = controller;
        setLayout(new MigLayout("fill", "[grow,fill]", "[grow][grow]"));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshData();
            }
        });
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel topPanel = new JPanel(new MigLayout("fill", "[grow,fill][grow,fill]", "[grow]"));
        JPanel bottomPanel = new JPanel(new MigLayout("fill", "[grow,fill]", "[grow]"));

        // Create and add inventory and financial panels to top panel
        JPanel inventoryPanel = createInventorySummaryPanel();
        JPanel financialPanel = createFinancialSummaryPanel();
        topPanel.add(inventoryPanel, "cell 0 0");
        topPanel.add(financialPanel, "cell 1 0");

        // Create and add recent transactions panel to bottom panel
        JPanel transactionsPanel = createRecentTransactionsPanel();
        bottomPanel.add(transactionsPanel);

        // Add panels to main panel
        add(topPanel, "grow, wrap");
        add(bottomPanel, "grow");
    }

    private JPanel createInventorySummaryPanel() {
        JPanel inventoryPanel = new JPanel(new MigLayout(INVENTORY_PANEL_LAYOUT));
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Low Stock Alert"));

        InventorySummary inventorySummary = controller.getInventorySummary();
        List<Product> lowStockProducts = inventorySummary.getLowStrockProducts();

        // Create table for low stock products
        String[] columns = { "Product", "Stock", "Category" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Product product : lowStockProducts) {
            model.addRow(
                    new Object[] { product.getName(), product.getQuantity(), product.getCategory().getCategoryName() });
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Add warning label if there are low stock products
        if (!lowStockProducts.isEmpty()) {
            JLabel warningLabel = new JLabel("⚠️ Products requiring restock!");
            warningLabel.setForeground(Color.RED);
            inventoryPanel.add(warningLabel, "wrap");
        }

        inventoryPanel.add(scrollPane, "grow");
        return inventoryPanel;
    }

    private JPanel createFinancialSummaryPanel() {
        JPanel financialPanel = new JPanel(new MigLayout(FINANCIAL_PANEL_LAYOUT));
        financialPanel.setBorder(BorderFactory.createTitledBorder("Financial Summary"));

        FinancialSummary financialSummary = controller.getFinancialSummary();

        // Create a more visually appealing financial summary
        JPanel statsPanel = new JPanel(new MigLayout("fillx", "[][grow]", "[]10[]10[]10[]"));

        addFinancialData(statsPanel, "Total Sales:", CURRENCY_FORMAT.format(financialSummary.getTotalSales()));
        addFinancialData(statsPanel, "Total Cost:", CURRENCY_FORMAT.format(financialSummary.getTotalCost()));
        addFinancialData(statsPanel, "Net Profit:", CURRENCY_FORMAT.format(financialSummary.getNetProfit()));

        // Add gross margin with color coding
        double margin = financialSummary.getGrossMarginPercentage();
        JLabel marginLabel = new JLabel(String.format("%.1f%%", margin));
        if (margin > 30) {
            marginLabel.setForeground(new Color(0, 150, 0)); // Dark green
        } else if (margin > 15) {
            marginLabel.setForeground(new Color(200, 150, 0)); // Orange
        } else {
            marginLabel.setForeground(Color.RED);
        }
        addFinancialDataWithLabel(statsPanel, "Gross Margin:", marginLabel);

        financialPanel.add(statsPanel, "grow");
        return financialPanel;
    }

    private JPanel createRecentTransactionsPanel() {
        JPanel transactionsPanel = new JPanel(new MigLayout("fill"));
        transactionsPanel.setBorder(BorderFactory.createTitledBorder("Recent Transactions"));

        InventorySummary inventorySummary = controller.getInventorySummary();
        List<InventoryTransaction> recentTransactions = inventorySummary.getRecentInventoryTransactions();

        // Create table for recent transactions
        String[] columns = { "Date", "Product", "Type", "Quantity", "Amount" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (InventoryTransaction transaction : recentTransactions) {
            model.addRow(new Object[] { DATE_FORMAT.format(transaction.getDate()), transaction.getProduct().getName(),
                    transaction.getTransactionType(), transaction.getQuantity(),
                    CURRENCY_FORMAT.format(transaction.getPrice()) });
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        transactionsPanel.add(scrollPane, "grow");
        return transactionsPanel;
    }

    private void addFinancialData(JPanel panel, String label, String value) {
        panel.add(new JLabel(label), "gap 10");
        panel.add(new JLabel(value), "wrap");
    }

    private void addFinancialDataWithLabel(JPanel panel, String label, JLabel valueLabel) {
        panel.add(new JLabel(label), "gap 10");
        panel.add(valueLabel, "wrap");
    }

    private void refreshData() {
        removeAll();
        initializeComponents();
        revalidate();
        repaint();
    }
}
