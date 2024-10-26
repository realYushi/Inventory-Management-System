package me.yushi.inventorymanagementsystem.controller;

import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.service.SupplierService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SupplierControllerTest {

    @Mock
    private SupplierService supplierService;

    private SupplierController supplierController;
    private Supplier testSupplier;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        supplierController = new SupplierController(supplierService);
        testSupplier = new Supplier("SUP123", "Test Supplier");
    }

    @Test
    public void createSupplier_Success() {
        // Arrange
        when(supplierService.createSupplier(testSupplier)).thenReturn(testSupplier);

        // Act
        Supplier result = supplierController.createSupplier(testSupplier);

        // Assert
        assertNotNull(result);
        assertEquals("Test Supplier", result.getSupplierName());
        verify(supplierService, times(1)).createSupplier(testSupplier);
    }

    @Test
    public void createSupplier_NullSupplier() {
        // Act
        Supplier result = supplierController.createSupplier(null);

        // Assert
        assertNull(result);
        verify(supplierService, never()).createSupplier(any());
    }

    @Test
    public void updateSupplier_Success() {
        // Arrange
        when(supplierService.updateSupplier(testSupplier)).thenReturn(testSupplier);

        // Act
        Supplier result = supplierController.updateSupplier(testSupplier);

        // Assert
        assertNotNull(result);
        assertEquals("Test Supplier", result.getSupplierName());
        verify(supplierService, times(1)).updateSupplier(testSupplier);
    }

    @Test
    public void updateSupplier_NullSupplier() {
        // Act
        Supplier result = supplierController.updateSupplier(null);

        // Assert
        assertNull(result);
        verify(supplierService, never()).updateSupplier(any());
    }

    @Test
    public void getSupplierByID_Success() {
        // Arrange
        String supplierId = "SUP123";
        when(supplierService.getSupplierByID(supplierId)).thenReturn(testSupplier);

        // Act
        Supplier result = supplierController.getSupplierByID(supplierId);

        // Assert
        assertNotNull(result);
        assertEquals(supplierId, result.getSupplierID());
        verify(supplierService, times(1)).getSupplierByID(supplierId);
    }

    @Test
    public void getSupplierByID_NullId() {
        // Act
        Supplier result = supplierController.getSupplierByID(null);

        // Assert
        assertNull(result);
        verify(supplierService, never()).getSupplierByID(any());
    }

    @Test
    public void getSupplierByID_EmptyId() {
        // Act
        Supplier result = supplierController.getSupplierByID("");

        // Assert
        assertNull(result);
        verify(supplierService, never()).getSupplierByID(any());
    }

    @Test
    public void deleteSupplier_Success() {
        // Arrange
        String supplierId = "SUP123";
        when(supplierService.deleteSupplier(supplierId)).thenReturn(true);

        // Act
        boolean result = supplierController.deleteSupplier(supplierId);

        // Assert
        assertTrue(result);
        verify(supplierService, times(1)).deleteSupplier(supplierId);
    }

    @Test
    public void deleteSupplier_NullId() {
        // Act
        boolean result = supplierController.deleteSupplier(null);

        // Assert
        assertFalse(result);
        verify(supplierService, never()).deleteSupplier(any());
    }

    @Test
    public void getAllSuppliers_Success() {
        // Arrange
        List<Supplier> suppliers = Arrays.asList(new Supplier("SUP1", "Supplier 1"),
                new Supplier("SUP2", "Supplier 2"));
        when(supplierService.getAllSuppliers()).thenReturn(suppliers);

        // Act
        List<Supplier> result = supplierController.getAllSuppliers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplierService, times(1)).getAllSuppliers();
    }

    @Test
    public void haveLinkedProduct_Success() {
        // Arrange
        String supplierId = "SUP123";
        when(supplierService.haveLinkedProduct(supplierId)).thenReturn(true);

        // Act
        boolean result = supplierController.haveLinkedProduct(supplierId);

        // Assert
        assertTrue(result);
        verify(supplierService, times(1)).haveLinkedProduct(supplierId);
    }
}
