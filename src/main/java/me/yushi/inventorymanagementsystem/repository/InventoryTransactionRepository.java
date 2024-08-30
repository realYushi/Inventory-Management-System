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
        // Read all inventory transactions from file and store them in a map
        inventoryTransactionFileHandler = fileHandler;
        this.inventoryTransactionMap = inventoryTransactionFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(transation -> transation.getTransactionID(), i -> i));
    }

    @Override
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction) {
        // Add new inventory transaction to the map
        inventoryTransactionMap.put(newInventoryTransaction.getTransactionID(), newInventoryTransaction);
        return inventoryTransactionMap.get(newInventoryTransaction.getTransactionID());

    }

    @Override
    public InventoryTransaction readInventoryTransaction(String inventoryTransationID) {
        // Read inventory transaction from the map
        return inventoryTransactionMap.get(inventoryTransationID);
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedIInventoryTransaction) {
        // Update inventory transaction in the map
        inventoryTransactionMap.put(updatedIInventoryTransaction.getTransactionID(), updatedIInventoryTransaction);
        return inventoryTransactionMap.get(updatedIInventoryTransaction.getTransactionID());
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        // Delete inventory transaction from the map
        inventoryTransactionMap.remove(inventoryTransationID);
        return !inventoryTransactionMap.containsKey(inventoryTransationID);
    }

    @Override
    public Map<String, InventoryTransaction> getAllInventoryTransations() {
        // Return all inventory transactions
        return inventoryTransactionMap;
    }

    @Override
    public void save() {
        // Write all inventory transactions to file
        inventoryTransactionFileHandler.writeToFile(inventoryTransactionMap);
    }

}
