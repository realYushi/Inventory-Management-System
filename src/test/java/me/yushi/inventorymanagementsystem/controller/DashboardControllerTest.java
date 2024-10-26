package me.yushi.inventorymanagementsystem.controller;

import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.model.*;
import me.yushi.inventorymanagementsystem.service.DashboardService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    private DashBoardController dashboardController;
    private InventorySummary testInventorySummary;
    private FinancialSummary testFinancialSummary;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dashboardController = new DashBoardController(dashboardService);

        // Create test data
        Category testCategory = new Category("Test Category", "CAT123");
        Supplier testSupplier = new Supplier("SUP123", "Test Supplier");
        Product testProduct = new Product("PROD123", "Test Product", testCategory, testSupplier, 5, "pcs", 9.99);

        // Create test inventory summary
        testInventorySummary = new InventorySummary(Arrays.asList(testProduct),
                Arrays.asList(new InventoryTransaction("TRANS1", testProduct, 10, new Date(),
                        IInventoryTransaction.TransactionType.PURCHASE, 99.90)));

        // Create test financial summary
        testFinancialSummary = new FinancialSummary(1000.0, 800.0); // Sales: 1000, Cost: 800
    }

    @Test
    public void getInventorySummary_Success() {
        // Arrange
        when(dashboardService.getInventorySummary()).thenReturn(testInventorySummary);

        // Act
        InventorySummary result = dashboardController.getInventorySummary();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getLowStrockProducts().size());
        assertEquals(1, result.getRecentInventoryTransactions().size());
        verify(dashboardService, times(1)).getInventorySummary();
    }

    @Test
    public void getInventorySummary_ReturnsNull() {
        // Arrange
        when(dashboardService.getInventorySummary()).thenReturn(null);

        // Act
        InventorySummary result = dashboardController.getInventorySummary();

        // Assert
        assertNull(result);
        verify(dashboardService, times(1)).getInventorySummary();
    }

    @Test
    public void getFinancialSummary_Success() {
        // Arrange
        when(dashboardService.getFinancialSummary()).thenReturn(testFinancialSummary);

        // Act
        FinancialSummary result = dashboardController.getFinancialSummary();

        // Assert
        assertNotNull(result);
        assertEquals(1000.0, result.getTotalSales(), 0.001);
        assertEquals(800.0, result.getTotalCost(), 0.001);
        verify(dashboardService, times(1)).getFinancialSummary();
    }

    @Test
    public void getFinancialSummary_ReturnsNull() {
        // Arrange
        when(dashboardService.getFinancialSummary()).thenReturn(null);

        // Act
        FinancialSummary result = dashboardController.getFinancialSummary();

        // Assert
        assertNull(result);
        verify(dashboardService, times(1)).getFinancialSummary();
    }
}
