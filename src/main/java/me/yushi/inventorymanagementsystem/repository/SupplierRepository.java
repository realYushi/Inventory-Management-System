/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.ISupplier;

/**
 *
 * @author yushi
 */
public class SupplierRepository implements ISupplierRepository {

    private Map<Integer, ISupplier> supplierMap;
    private IFileHandler<ISupplier> supplierFileHandler;

    public SupplierRepository(IFileHandler<ISupplier> fileHandler) throws IOException {
        supplierFileHandler = fileHandler;
        this.supplierMap = supplierFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(s -> s.getSupplierID(), s -> s));
    }

    @Override
    public ISupplier createSupplier(ISupplier newSupplier) {
        supplierMap.put(newSupplier.getSupplierID(), newSupplier);
        return supplierMap.get(newSupplier.getSupplierID());
    }

    @Override
    public ISupplier readSupplier(int supplierID) {
        return supplierMap.get(supplierID);
    }

    @Override
    public ISupplier updateSupplier(ISupplier updatedSupplier) {
        supplierMap.put(updatedSupplier.getSupplierID(), updatedSupplier);
        return supplierMap.get(updatedSupplier.getSupplierID());
    }

    @Override
    public boolean deleteSupplier(int supplierID) {
        supplierMap.remove(supplierID);
        return !supplierMap.containsKey(supplierID);
    }

    @Override
    public Map<Integer, ISupplier> getAllSuppliers() {
        return supplierMap;
    }

    @Override
    public void save() {
        supplierFileHandler.writeToFile(supplierMap);
    }

}
