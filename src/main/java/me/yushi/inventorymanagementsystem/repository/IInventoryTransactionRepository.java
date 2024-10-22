/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;
import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionRepository {
    InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction, EntityManager em);
    InventoryTransaction readInventoryTransaction(String inventoryTransationID, EntityManager em);
    InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedIInventoryTransaction, EntityManager em);
    boolean deleteInventoryTransaction(String inventoryTransationID, EntityManager em); 
    List<InventoryTransaction> getAllInventoryTransations(EntityManager em);
}
