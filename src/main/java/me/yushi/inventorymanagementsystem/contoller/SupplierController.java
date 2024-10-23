/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
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
    public SupplierDto createSupplier(SupplierDto newSupplierDto) {
        if (newSupplierDto == null) {
            System.err.println("Error: SupplierDto cannot be null");
            return null;
        }
        return supplierService.createSupplier(newSupplierDto);
    }

    @Override
    // Update a supplier
    public SupplierDto updateSupplier(SupplierDto updateSupplierDto) {
        if (updateSupplierDto == null) {
            System.err.println("Error: SupplierDto cannot be null");
            return null;
        }
        return supplierService.updateSupplier(updateSupplierDto);
    }

    @Override
    // Get a supplier by its ID
    public SupplierDto getSupplierByID(String supplierID) {
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
    public List<SupplierDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}
