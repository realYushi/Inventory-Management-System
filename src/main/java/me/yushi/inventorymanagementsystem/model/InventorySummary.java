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

    private List<IProduct> lowStockProducts;
    private List<IProduct> expirySoonProducts;
    private List<IProduct> expiriedProducts;
    private List<IInventoryTransaction> recentInventoryTransactions; 

    public InventorySummary(List<IProduct> lowStock,List<IProduct> expritySoon,List<IProduct> expried,List<IInventoryTransaction> transatioins) {
        this.lowStockProducts=lowStock;
        this.expiriedProducts=expried;
        this.expirySoonProducts=expritySoon;
        this.recentInventoryTransactions=transatioins;
    }
    

    @Override
    public List<IProduct> getLowStrockProducts() {
        return this.lowStockProducts;
    }

    @Override
    public List<IProduct> getExpirySoonProducts() {
        return this.expirySoonProducts;
    }

    @Override
    public List<IProduct> getExpriedProducts() {
        return this.expiriedProducts;
    }

    @Override
    public List<IInventoryTransaction> getRecentInventoryTransactions() {
        return this.recentInventoryTransactions;
    }




}
