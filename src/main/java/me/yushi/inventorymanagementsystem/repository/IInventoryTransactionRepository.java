/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionRepository {
    IInventoryTransaction createInventoryTransaction(IInventoryTransaction newIInventoryTransaction);
    IInventoryTransaction readInventoryTransaction(int inventoryTransationID);
    IInventoryTransaction updateInventoryTransaction(IInventoryTransaction updatedIInventoryTransaction);
    boolean deleteInventoryTransaction(int inventoryTransationID); 

    List<IInventoryTransaction> getAllInventoryTransations();
    boolean setUp();
}
