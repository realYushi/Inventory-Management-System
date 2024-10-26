/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

/**
 *
 * @author yushi
 */
public class DashboardService implements IDashboardService {

    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductRepository productRepository;
    // Number of days to consider for recent transactions
    private static final int TRANSACTION_DATE_RANGE = 30;
    // Profit rate
    private static final double PROFIT_RATE = 1.2;
    // Low stock trigger
    private static final int LOW_STOCK_TRIGGER = 10;
    // Maximum number of recent transactions to show
    private static final int MAX_RECENT_TRANSACTIONS = 20;

    public DashboardService(InventoryTransactionRepository inventoryTransactionRepository,
            ProductRepository productRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public FinancialSummary getFinancialSummary() {
        List<InventoryTransaction> recentTransactions = getRecentTransactionData();
        return calculateFinancialSummary(recentTransactions);
    }

    @Override
    public InventorySummary getInventorySummary() {
        List<Product> allProducts = TransactionUtil.executeTransaction(em -> productRepository.getAllProducts(em));
        List<Product> lowStockProducts = filterLowStockProducts(allProducts);
        
        // Sort low stock products by quantity (ascending) so most critical items appear first
        lowStockProducts.sort(Comparator.comparingInt(Product::getQuantity));
        
        List<InventoryTransaction> recentTransactions = getRecentTransactionData();
        
        // Sort transactions by date (descending) to show most recent first
        recentTransactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        
        // Limit to MAX_RECENT_TRANSACTIONS
        if (recentTransactions.size() > MAX_RECENT_TRANSACTIONS) {
            recentTransactions = recentTransactions.subList(0, MAX_RECENT_TRANSACTIONS);
        }

        return new InventorySummary(lowStockProducts, recentTransactions);
    }

    // Calculate financial summary based on transaction data
    private FinancialSummary calculateFinancialSummary(List<InventoryTransaction> transactions) {
        double totalSales = transactions.stream()
                .filter(t -> t.getTransactionType() == IInventoryTransaction.TransactionType.SALE)
                .mapToDouble(t -> t.getPrice() * t.getQuantity() * PROFIT_RATE) // Include quantity in calculation
                .sum();

        double totalCost = transactions.stream()
                .filter(t -> t.getTransactionType() == IInventoryTransaction.TransactionType.PURCHASE ||
                        t.getTransactionType() == IInventoryTransaction.TransactionType.SPOILAGE)
                .mapToDouble(t -> t.getPrice() * t.getQuantity()) // Include quantity in calculation
                .sum();

        return new FinancialSummary(totalSales, totalCost);
    }

    // Get products that are low in stock
    private List<Product> filterLowStockProducts(List<Product> products) {
        return products.stream()
                .filter(product -> product.getQuantity() < LOW_STOCK_TRIGGER)
                .collect(Collectors.toList());
    }

    // Fetch recent transactions based on date range
    private List<InventoryTransaction> getRecentTransactionData() {
        Date startDate = getStartDate(TRANSACTION_DATE_RANGE);
        return TransactionUtil.executeTransaction(em -> {
            return inventoryTransactionRepository.getAllInventoryTransations(em)
                    .stream()
                    .filter(transaction -> transaction.getDate().after(startDate))
                    .collect(Collectors.toList());
        });
    }

    // Calculate the start date for recent transactions
    private Date getStartDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -daysAgo);
        return calendar.getTime();
    }
}
