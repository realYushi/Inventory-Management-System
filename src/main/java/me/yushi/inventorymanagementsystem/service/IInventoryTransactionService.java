/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionService {
    InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newInventoryTransactionDto);
    InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto);
    InventoryTransactionDto getInventoryTransactionByID(int inventoryTransationID);
    boolean deleteInventoryTransaction(int inventoryTransationID);
    List<InventoryTransactionDto> getAllInventoryTransations();
    void save();
    
    
}
