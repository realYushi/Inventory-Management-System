package me.yushi.inventorymanagementsystem.model;

import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author yushi
 */
public interface IInventorySummary {

    List<IProduct> getRecentProducts();

    void setRecentProducts(List<IProduct> recentProducts);

    List<ICategory> getRecentCategories();

    void setRecentCategories(List<ICategory> recentCategories);

    List<ISupplier> getRecentSuppliers();

    void setRecentSuppliers(List<ISupplier> recentSuppliers);

    List<IInventoryTransaction> getRecentInventoryTransactions();

    void setRecentInventoryTransactions(List<IInventoryTransaction> recentInventoryTransactions);

}
