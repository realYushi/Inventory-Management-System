/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public class SupplierRepository implements ISupplierRepository {

    private Map<String, Supplier> supplierMap;
    private FileHandler<Supplier> supplierFileHandler;

    public SupplierRepository(FileHandler<Supplier> fileHandler) throws IOException {
        // Read all suppliers from file and store them in a map
        supplierFileHandler = fileHandler;
        this.supplierMap = supplierFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(s -> s.getSupplierID(), s -> s));
    }

    @Override
    public Supplier createSupplier(Supplier newSupplier) {
        // Add new supplier to the map
        supplierMap.put(newSupplier.getSupplierID(), newSupplier);
        return supplierMap.get(newSupplier.getSupplierID());
    }

    @Override
    public Supplier readSupplier(String supplierID) {
        // Read supplier from the map
        return supplierMap.get(supplierID);
    }

    @Override
    public Supplier updateSupplier(Supplier updatedSupplier) {
        // Update supplier in the map
        supplierMap.put(updatedSupplier.getSupplierID(), updatedSupplier);
        return supplierMap.get(updatedSupplier.getSupplierID());
    }

    @Override
    public boolean deleteSupplier(String supplierID) {
        // Delete supplier from the map
        supplierMap.remove(supplierID);
        return !supplierMap.containsKey(supplierID);
    }

    @Override
    public Map<String, Supplier> getAllSuppliers() {
        // Return all suppliers
        return supplierMap;
    }

    @Override
    public void save() {
        // Write all suppliers to file
        supplierFileHandler.writeToFile(supplierMap);
    }

}
