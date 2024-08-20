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

    List<Product> getLowStrockProducts();
    List<InventoryTransaction> getRecentInventoryTransactions();

}
