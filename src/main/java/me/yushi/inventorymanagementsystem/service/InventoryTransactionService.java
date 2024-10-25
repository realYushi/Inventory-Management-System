/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService
        implements IInventoryTransactionService  {
    private InventoryTransactionRepository repository;
    public InventoryTransactionService(InventoryTransactionRepository repository) {
        this.repository = repository; 
    }

    @Override
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction) {
        InventoryTransaction inventoryTransaton = TransactionUtil.executeTransaction(em -> {
            return repository.createInventoryTransaction((newInventoryTransaction), em);
        }); 
        if(inventoryTransaton == null){
            System.out.println("Failed to create inventory transaction: " + newInventoryTransaction.getTransactionID());
            return null;
        }
        return newInventoryTransaction;
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction) {
        InventoryTransaction inventoryTransaction = TransactionUtil.executeTransaction(em -> {
            return repository.updateInventoryTransaction((updatedInventoryTransaction), em);
        }); 
        if(inventoryTransaction == null){
            System.out.println("Failed to update inventory transaction: " + updatedInventoryTransaction.getTransactionID());
            return null;
        }
        return updatedInventoryTransaction;
    }

    @Override
    // Get an inventory transaction by its ID
    public InventoryTransaction getInventoryTransactionByID(String inventoryTransationID) {
        InventoryTransaction inventoryTransaction = TransactionUtil.executeTransaction(em -> {
            return repository.readInventoryTransaction(inventoryTransationID, em);
        });
        if(inventoryTransaction == null){
            System.out.println("No inventory transaction found with ID: " + inventoryTransationID);
            return null;
        }
        return inventoryTransaction;
    }

    @Override
    // Delete an inventory transaction by its ID
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteInventoryTransaction(inventoryTransationID, em);
        });
    }

    @Override
    public List<InventoryTransaction> getAllInventoryTransations() {
        List<InventoryTransaction> inventoryTransactions = TransactionUtil.executeTransaction(em -> {
            return repository.getAllInventoryTransations(em);
        });
        return inventoryTransactions;

    }

     

}
