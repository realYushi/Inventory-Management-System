/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionRepository {
    InventoryTransaction createInventoryTransaction(InventoryTransaction newIInventoryTransaction);
    InventoryTransaction readInventoryTransaction(int inventoryTransationID);
    InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedIInventoryTransaction);
    boolean deleteInventoryTransaction(int inventoryTransationID); 

    Map<Integer,InventoryTransaction> getAllInventoryTransations();
    void save();
}
