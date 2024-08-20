/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

/**
 *
 * @author yushi
 */
public class InventoryTransactionRepository implements IInventoryTransactionRepository {

    private Map<String, InventoryTransaction> inventoryTransactionMap;
    private IFileHandler<InventoryTransaction> inventoryTransactionFileHandler;

    public InventoryTransactionRepository(IFileHandler<InventoryTransaction> fileHandler) throws IOException {
        inventoryTransactionFileHandler=fileHandler;
        this.inventoryTransactionMap = inventoryTransactionFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(transation -> transation.getTransactionID(), i -> i));
    }

    @Override
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction) {
        inventoryTransactionMap.put(newInventoryTransaction.getTransactionID(), newInventoryTransaction);
        return inventoryTransactionMap.get(newInventoryTransaction.getTransactionID());

    }

    @Override
    public InventoryTransaction readInventoryTransaction(String inventoryTransationID) {
        return inventoryTransactionMap.get(inventoryTransationID);
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedIInventoryTransaction) {
        inventoryTransactionMap.put(updatedIInventoryTransaction.getTransactionID(), updatedIInventoryTransaction);
        return inventoryTransactionMap.get(updatedIInventoryTransaction.getTransactionID());
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        inventoryTransactionMap.remove(inventoryTransationID);
        return !inventoryTransactionMap.containsKey(inventoryTransationID);
    }

    @Override
    public Map<String,InventoryTransaction> getAllInventoryTransations() {
        return inventoryTransactionMap;
    }

    @Override
    public void save() {
        inventoryTransactionFileHandler.writeToFile(inventoryTransactionMap);
    }

}
