/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionController{
    InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newIInventoryTransactionDto);
    InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto);
    InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID);
    boolean deleteInventoryTransaction(String inventoryTransationID);
    List<InventoryTransactionDto> getAllInventoryTransations();
    List<ProductDto> getAllProduct();
    ProductDto getProduct(String produdctID);
    
    
}
