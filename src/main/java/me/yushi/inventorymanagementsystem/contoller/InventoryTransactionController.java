/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;

/**
 *
 * @author yushi
 */
public class InventoryTransactionController implements IInventoryTransactionController{
    InventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(InventoryTransactionRepository repository) {
        this.inventoryTransactionService=new InventoryTransactionService(repository);
    }
    

    @Override
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newIInventoryTransactionDto) {
        return inventoryTransactionService.createInventoryTransaction(newIInventoryTransactionDto);
    }

    @Override
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransactionDto);
    }

    @Override
    public InventoryTransactionDto getInventoryTransactionByID(int inventoryTransationID) {
        return inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID);
    }

    @Override
    public boolean deleteInventoryTransaction(int inventoryTransationID) {
        return inventoryTransactionService.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }
    
    
}
