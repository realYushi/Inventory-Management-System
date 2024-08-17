/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.Map;
import me.yushi.inventorymanagementsystem.Dto.IFinancialSummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IInventorySummaryDto;
import me.yushi.inventorymanagementsystem.model.IFinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class DashboardService implements IDashboardService {
    IInventoryTransactionRepository inventoryTransactionRepository;
    private final int DATE_RANGE=30;


    public DashboardService(IInventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryTransactionRepository=inventoryTransactionRepository;
    }

    @Override
    public IFinancialSummaryDto getFinancialSummary() {
        IFinancialSummary financialSummary;
        IFinancialSummaryDto financialSummaryDto;
        Map<Integer,IInventoryTransaction> allData=inventoryTransactionRepository.getAllInventoryTransations();
        
        
        double totalSales;
        double totalCost;
        double netProfit;
    }
        
    double totalCost;
    double netProfit;

    @Override
    public IInventorySummaryDto getIentorySummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IFinancialSummaryDto getFinancialSummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
