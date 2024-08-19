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

    private Map<Integer, InventoryTransaction> inventoryTransactionMap;
    private IFileHandler<InventoryTransaction> inventoryTransactionFileHandler;

    public InventoryTransactionRepository(IFileHandler<InventoryTransaction> fileHandler) throws IOException {
        inventoryTransactionFileHandler=fileHandler;
        this.inventoryTransactionMap = inventoryTransactionFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(i -> i.getTransactionID(), i -> i));
    }

    @Override
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newIInventoryTransaction) {
        inventoryTransactionMap.put(newIInventoryTransaction.getTransactionID(), newIInventoryTransaction);
        return inventoryTransactionMap.get(newIInventoryTransaction.getTransactionID());

    }

    @Override
    public InventoryTransaction readInventoryTransaction(int inventoryTransationID) {
        return inventoryTransactionMap.get(inventoryTransationID);
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedIInventoryTransaction) {
        inventoryTransactionMap.put(updatedIInventoryTransaction.getTransactionID(), updatedIInventoryTransaction);
        return inventoryTransactionMap.get(updatedIInventoryTransaction.getTransactionID());
    }

    @Override
    public boolean deleteInventoryTransaction(int inventoryTransationID) {
        inventoryTransactionMap.remove(inventoryTransationID);
        return !inventoryTransactionMap.containsKey(inventoryTransationID);
    }

    @Override
    public Map<Integer, InventoryTransaction> getAllInventoryTransations() {
        return inventoryTransactionMap;
    }

    @Override
    public void save() {
        inventoryTransactionFileHandler.writeToFile(inventoryTransactionMap);
    }

}
