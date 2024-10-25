/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionController{
    InventoryTransaction createInventoryTransaction(InventoryTransaction newIInventoryTransaction);
    InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction);
    InventoryTransaction getInventoryTransactionByID(String inventoryTransationID);
    boolean deleteInventoryTransaction(String inventoryTransationID);
    List<InventoryTransaction> getAllInventoryTransations();
    List<Product> getAllProduct();
    Product getProduct(String produdctID);
    
    
}
