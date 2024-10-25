/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class SupplierService implements ISupplierService {
    SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    // Create a new supplier, save it to the repository, and return the created
    public Supplier createSupplier(Supplier newSupplier) {

        Supplier supplier = TransactionUtil.executeTransaction(em -> {
            return repository.createSupplier((newSupplier), em);
        });
        if (supplier == null) {
            System.out.println("Failed to create supplier : " + newSupplier.getSupplierName());
            return null;
        }
        return newSupplier;
    }

    @Override
    public Supplier updateSupplier(Supplier updatedSupplier) {
        Supplier newSupplier = TransactionUtil.executeTransaction(em -> {
            return repository.updateSupplier((updatedSupplier), em);
        });
        return newSupplier;
    }

    @Override
    public Supplier getSupplierByID(String supplierID) {
        Supplier supplier = TransactionUtil.executeTransaction(em -> {
            return repository.readSupplier(supplierID, em);
        });
        if (supplier != null) {
            System.out.println("No supplier found with ID: " + supplierID);
            return null;
        }
        return supplier;

    }

    @Override
    // Delete a supplier by its ID
    public boolean deleteSupplier(String supplierID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteSupplier(supplierID, em);
        });
    }

    @Override
    // Get all suppliers in the repository
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = TransactionUtil.executeTransaction(em -> {
            return repository.getAllSuppliers(em);
        });
        return suppliers;
    }

}
