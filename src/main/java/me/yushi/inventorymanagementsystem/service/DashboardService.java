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
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

/**
 *
 * @author yushi
 */
public class DashboardService implements IDashboardService {

    InventoryTransactionRepository inventoryTransactionRepository;
    ProductRepository productRepository;
    private final int TRANSATION_DATE_RANGE = 30;
    private final int EXPRIY_SOON_DATE_RANGE = 7;
    private final double PROFIT_RATE = 1.2;
    private final int LOW_STOCK_TRIGGER = 10;
    private List<InventoryTransaction> recentTransationData;

    public DashboardService(InventoryTransactionRepository inventoryTransactionRepository, ProductRepository productRepository) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.productRepository = productRepository;
        recentTransationData = getRecentTransactionData();
    }

    @Override
    public FinancialSummary getFinancialSummary() {

        double totalSales = 0;
        double totalCost = 0;
        for (IInventoryTransaction t : recentTransationData) {
            switch (t.getTransactionType()) {
                case SALE:
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
    public InventorySummary getIentorySummary() {
        List<Product> allData = productRepository.getAllProducts().values().stream().collect(Collectors.toList());
        List<Product> lowStock = new ArrayList<>();
        List<Product> expirySoon = new ArrayList<>();
        List<Product> expired = new ArrayList<>();

        Date currentDate = new Date();
        Date expirySoonDate = getEndDate(EXPRIY_SOON_DATE_RANGE);

        for (Product product : allData) {
            // Check for low stock 
            if (product.getQuantity() < LOW_STOCK_TRIGGER) {
                lowStock.add(product);
            }

            // Check for expiry
            if (product.getExpirationDate() != null) {
                if (product.getExpirationDate().before(currentDate)) {
                    expired.add(product);
                } else if (product.getExpirationDate().before(expirySoonDate)) {
                    expirySoon.add(product);
                }
            }
        }
        return new InventorySummary(lowStock, expirySoon, expired, recentTransationData);

    }

    private Date getEndDate(int dataRange) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -dataRange);
        Date endDate = calendar.getTime();
        return endDate;

    }

    private List<InventoryTransaction> getRecentTransactionData() {
        Date endDate = getEndDate(TRANSATION_DATE_RANGE);
        Map<Integer, InventoryTransaction> allData = inventoryTransactionRepository.getAllInventoryTransations();
        recentTransationData = allData.values().stream()
                .filter(transation -> transation.getDate().after(endDate))
                .collect(Collectors.toList());

        return recentTransationData;
    }

}
