/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.List;

/**
 *
 * @author yushi
 */
public class InventorySummary implements IInventorySummary{

    private List<IProduct> recentProducts;
    private List<ISupplier> recentSuppliers;
    private List<ICategory> recentCategories;
    private List<IInventoryTransaction> recentInventoryTransactions;

    public InventorySummary(List<IProduct> recentProducts, List<ISupplier> recentSuppliers, 
                            List<ICategory> recentCategories, 
                            List<IInventoryTransaction> recentInventoryTransactions) {
        this.recentProducts = recentProducts;
        this.recentSuppliers = recentSuppliers;
        this.recentCategories = recentCategories;
        this.recentInventoryTransactions = recentInventoryTransactions;
    }

    @Override
    public List<IProduct> getRecentProducts() {
        return this.recentProducts;
    }

    @Override
    public void setRecentProducts(List<IProduct> recentProducts) {
        this.recentProducts=recentProducts;
    }

    @Override
    public List<ICategory> getRecentCategories() {
        return this.recentCategories;
    }

    @Override
    public void setRecentCategories(List<ICategory> recentCategories) {
        this.recentCategories=recentCategories;
    }

    @Override
    public List<ISupplier> getRecentSuppliers() {
        return this.recentSuppliers;
    }

    @Override
    public void setRecentSuppliers(List<ISupplier> recentSuppliers) {
        this.recentSuppliers=recentSuppliers;

    }

    @Override
    public List<IInventoryTransaction> getRecentInventoryTransactions() {
        return this.recentInventoryTransactions;
    }

    @Override
    public void setRecentInventoryTransactions(List<IInventoryTransaction> recentInventoryTransactions) {
        this.recentInventoryTransactions=recentInventoryTransactions;
    }


}
