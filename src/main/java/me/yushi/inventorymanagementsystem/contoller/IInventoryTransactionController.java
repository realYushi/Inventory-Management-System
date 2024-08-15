/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionController{
    IInventoryTransactionDto createInventoryTransaction(IInventoryTransactionDto newIInventoryTransactionDto);
    IInventoryTransactionDto updateInventoryTransaction(IInventoryTransactionDto updatedInventoryTransactionDto);
    IInventoryTransactionDto getInventoryTransactionByID(int inventoryTransationID);
    boolean deleteInventoryTransaction(int inventoryTransationID);
    List<IInventoryTransactionDto> getAllInventoryTransations();
    
    
}
