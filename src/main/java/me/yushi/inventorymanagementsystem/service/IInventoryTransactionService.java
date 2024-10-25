/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.InventoryTransaction;


/**
 *
 * @author yushi
 */
public interface IInventoryTransactionService {
    InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction);
    InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction);
    InventoryTransaction getInventoryTransactionByID(String inventoryTransationID);
    boolean deleteInventoryTransaction(String inventoryTransationID);
    List<InventoryTransaction> getAllInventoryTransations();
    
    
}
