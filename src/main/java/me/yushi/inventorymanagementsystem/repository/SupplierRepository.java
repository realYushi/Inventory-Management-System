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

    private Map<Integer, Supplier> supplierMap;
    private FileHandler<Supplier> supplierFileHandler;

    public SupplierRepository(FileHandler<Supplier> fileHandler) throws IOException {
        supplierFileHandler = fileHandler;
        this.supplierMap = supplierFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(s -> s.getSupplierID(), s -> s));
    }

    @Override
    public Supplier createSupplier(Supplier newSupplier) {
        supplierMap.put(newSupplier.getSupplierID(), newSupplier);
        return supplierMap.get(newSupplier.getSupplierID());
    }

    @Override
    public Supplier readSupplier(int supplierID) {
        return supplierMap.get(supplierID);
    }

    @Override
    public Supplier updateSupplier(Supplier updatedSupplier) {
        supplierMap.put(updatedSupplier.getSupplierID(), updatedSupplier);
        return supplierMap.get(updatedSupplier.getSupplierID());
    }

    @Override
    public boolean deleteSupplier(int supplierID) {
        supplierMap.remove(supplierID);
        return !supplierMap.containsKey(supplierID);
    }

    @Override
    public Map<Integer, Supplier> getAllSuppliers() {
        return supplierMap;
    }

    @Override
    public void save() {
        supplierFileHandler.writeToFile(supplierMap);
    }

}
