/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.service.DashboardService;
import me.yushi.inventorymanagementsystem.service.IDashboardService;

/**
 *
 * @author yushi
 */
public class DashBoardController implements IDashBoardController{
    IDashboardService dashboardService;

    public DashBoardController(IUnitOfWork unitOfWork) {
        dashboardService=new DashboardService(unitOfWork);
    }
    

    @Override
    public InventorySummary getInventorySummary() {
        return dashboardService.getIentorySummary();
    }

    @Override
    public FinancialSummary getFinancialSummary() {
        return dashboardService.getFinancialSummary();
    }
    
}
