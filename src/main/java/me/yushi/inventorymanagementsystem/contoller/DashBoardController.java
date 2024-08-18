/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import me.yushi.inventorymanagementsystem.model.IFinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventorySummary;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;
import me.yushi.inventorymanagementsystem.service.DashboardService;
import me.yushi.inventorymanagementsystem.service.IDashboardService;

/**
 *
 * @author yushi
 */
public class DashBoardController implements IDashBoardController{
    IDashboardService dashboardService;

    public DashBoardController(IInventoryTransactionRepository transactionRepository,IProductRepository productRepository) {
        dashboardService=new DashboardService(transactionRepository,productRepository);
    }
    

    @Override
    public IInventorySummary getInventorySummary() {
        return dashboardService.getIentorySummary();
    }

    @Override
    public IFinancialSummary getFinancialSummary() {
        return dashboardService.getFinancialSummary();
    }
    
}
