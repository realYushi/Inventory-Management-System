/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

/**
 *
 * @author yushi
 */
public interface IUnitOfWork {
    CategoryRepository getCategoryRepository();
    InventoryTransactionRepository getInventoryTransactionRepository();
    ProductRepository getProductRepository();
    SupplierRepository getSupplierRepository();
}
