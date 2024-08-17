/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;

/**
 *
 * @author yushi
 */
public class InventoryTransactionRepository implements IInventoryTransactionRepository {

    private Map<Integer, IInventoryTransaction> inventoryTransactionMap;
    private IFileHandler<IInventoryTransaction> inventoryTransactionFileHandler;

    public InventoryTransactionRepository(IFileHandler<IInventoryTransaction> fileHandler) throws IOException {
        inventoryTransactionFileHandler=fileHandler;
        this.inventoryTransactionMap = inventoryTransactionFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(i -> i.getTransactionID(), i -> i));
    }

    @Override
    public IInventoryTransaction createInventoryTransaction(IInventoryTransaction newIInventoryTransaction) {
        inventoryTransactionMap.put(newIInventoryTransaction.getTransactionID(), newIInventoryTransaction);
        return inventoryTransactionMap.get(newIInventoryTransaction.getTransactionID());

    }

    @Override
    public IInventoryTransaction readInventoryTransaction(int inventoryTransationID) {
        return inventoryTransactionMap.get(inventoryTransationID);
    }

    @Override
    public IInventoryTransaction updateInventoryTransaction(IInventoryTransaction updatedIInventoryTransaction) {
        inventoryTransactionMap.put(updatedIInventoryTransaction.getTransactionID(), updatedIInventoryTransaction);
        return inventoryTransactionMap.get(updatedIInventoryTransaction.getTransactionID());
    }

    @Override
    public boolean deleteInventoryTransaction(int inventoryTransationID) {
        inventoryTransactionMap.remove(inventoryTransationID);
        return !inventoryTransactionMap.containsKey(inventoryTransationID);
    }

    @Override
    public Map<Integer, IInventoryTransaction> getAllInventoryTransations() {
        return inventoryTransactionMap;
    }

    @Override
    public void save() {
        inventoryTransactionFileHandler.writeToFile(inventoryTransactionMap);
    }

}
