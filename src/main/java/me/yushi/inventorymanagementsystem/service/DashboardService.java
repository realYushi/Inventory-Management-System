/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

/**
 *
 * @author yushi
 */
public class DashboardService implements IDashboardService {

    InventoryTransactionRepository inventoryTransactionRepository;
    ProductRepository productRepository;
    // Number of days to consider for recent transactions
    private static final int TRANSATION_DATE_RANGE = 30;
    // Profit rate
    private static final double PROFIT_RATE = 1.2;
    // Low stock trigger
    private static final int LOW_STOCK_TRIGGER = 10;
    // Recent transaction data
    private List<InventoryTransaction> recentTransationData;

    public DashboardService(IUnitOfWork unitOfWork) {
        this.inventoryTransactionRepository = unitOfWork.getInventoryTransactionRepository();
        this.productRepository = unitOfWork.getProductRepository();
        recentTransationData = getRecentTransactionData();
    }

    @Override
    public FinancialSummary getFinancialSummary() {
        double totalSales = 0;
        double totalCost = 0;
        for (IInventoryTransaction t : recentTransationData) {
            // Calculate total sales and total cost
            switch (t.getTransactionType()) {
                case SALE:
                    // Calculate total sales, including profit
                    totalSales = totalSales + (t.getPrice() * PROFIT_RATE);
                    break;
                case PURCHASE:
                    totalCost = totalCost + t.getPrice();
                    break;
                case SPOILAGE:
                    totalCost = totalCost + t.getPrice();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        return new FinancialSummary(totalSales, totalCost);

    }

    @Override
    // Get inventory summary, including low stock
    public InventorySummary getIentorySummary() {
        List<Product> allData = productRepository.getAllProducts().values().stream().collect(Collectors.toList());
        List<Product> lowStock = new ArrayList<>();
        for (Product product : allData) {
            // Check for low stock
            if (product.getQuantity() < LOW_STOCK_TRIGGER) {
                lowStock.add(product);
            }
        }
        return new InventorySummary(lowStock, recentTransationData);

    }

    // Get end date for recent transactions
    private Date getEndDate(int dataRange) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // Subtract data range from current date
        calendar.add(Calendar.DAY_OF_MONTH, -dataRange);
        return calendar.getTime();

    }

    // Get recent transaction data
    private List<InventoryTransaction> getRecentTransactionData() {
        Date endDate = getEndDate(TRANSATION_DATE_RANGE);
        Map<String, InventoryTransaction> allData = inventoryTransactionRepository.getAllInventoryTransations();
        // Filter transactions by date
        recentTransationData = allData.values().stream()
                .filter(transation -> transation.getDate().after(endDate))
                .collect(Collectors.toList());

        return recentTransationData;
    }

}
