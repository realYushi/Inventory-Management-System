package me.yushi.inventorymanagementsystem.contoller;

import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;
import me.yushi.inventorymanagementsystem.service.DashboardService;
import me.yushi.inventorymanagementsystem.service.IDashboardService;

/**
 *
 * @author yushi
 */
public class DashBoardController implements IDashBoardController {
    IDashboardService service;

    public DashBoardController(DashboardService dashboardService) {
        this.service = dashboardService;
    }

    @Override
    // Get inventory summary
    public InventorySummary getInventorySummary() {
        return service.getInventorySummary();
    }

    @Override
    // Get financial summary
    public FinancialSummary getFinancialSummary() {
        return service.getFinancialSummary();
    }

}
