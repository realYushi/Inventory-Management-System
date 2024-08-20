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

    private List<Product> lowStockProducts;
    private List<Product> expirySoonProducts;
    private List<Product> expiriedProducts;
    private List<InventoryTransaction> recentInventoryTransactions; 

    public InventorySummary(List<Product> lowStock,List<Product> expritySoon,List<Product> expried,List<InventoryTransaction> transatioins) {
        this.lowStockProducts=lowStock;
        this.expiriedProducts=expried;
        this.expirySoonProducts=expritySoon;
        this.recentInventoryTransactions=transatioins;
    }
    

    @Override
    public List<Product> getLowStrockProducts() {
        return this.lowStockProducts;
    }

    @Override
    public List<Product> getExpirySoonProducts() {
        return this.expirySoonProducts;
    }
    @Override
    public List<Product> getExpriedProducts() {
        return this.expiriedProducts;
    }

    @Override
    public List<InventoryTransaction> getRecentInventoryTransactions() {
        return this.recentInventoryTransactions;
    }




}
