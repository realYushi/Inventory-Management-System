/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.service.SupplierService;

/**
 *
 * @author yushi
 */
public class SupplierController implements ISupplierController {
    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    // Create a new supplier
    public Supplier createSupplier(Supplier newSupplier) {
        if (newSupplier == null) {
            System.err.println("Error: Supplier cannot be null");
            return null;
        }
        return supplierService.createSupplier(newSupplier);
    }

    @Override
    // Update a supplier
    public Supplier updateSupplier(Supplier updateSupplier) {
        if (updateSupplier == null) {
            System.err.println("Error: Supplier cannot be null");
            return null;
        }
        return supplierService.updateSupplier(updateSupplier);
    }

    @Override
    // Get a supplier by its ID
    public Supplier getSupplierByID(String supplierID) {
        if (supplierID == null || supplierID.isEmpty()) {
            System.err.println("Error: SupplierID cannot be null or empty");
            return null;
        }
        return supplierService.getSupplierByID(supplierID);
    }

    @Override
    // Delete a supplier
    public boolean deleteSupplier(String supplierID) {
        if (supplierID == null || supplierID.isEmpty()) {
            System.err.println("Error: SupplierID cannot be null or empty");
            return false;
        }
        return supplierService.deleteSupplier(supplierID);
    }

    @Override
    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @Override
    public boolean haveLinkedProduct(String supplierID) {
        return supplierService.haveLinkedProduct(supplierID);
    }

}
