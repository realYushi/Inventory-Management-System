/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.service.IInventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;

/**
 *
 * @author yushi
 */
public class InventoryTransactionController implements IInventoryTransactionController{
    IInventoryTransactionService inventoryTransactionService;

    public InventoryTransactionController(IInventoryTransactionRepository repository) {
        this.inventoryTransactionService=new InventoryTransactionService(repository);
    }
    

    @Override
    public IInventoryTransactionDto createInventoryTransaction(IInventoryTransactionDto newIInventoryTransactionDto) {
        return inventoryTransactionService.createInventoryTransaction(newIInventoryTransactionDto);
    }

    @Override
    public IInventoryTransactionDto updateInventoryTransaction(IInventoryTransactionDto updatedInventoryTransactionDto) {
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransactionDto);
    }

    @Override
    public IInventoryTransactionDto getInventoryTransactionByID(int inventoryTransationID) {
        return inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID);
    }

    @Override
    public boolean deleteInventoryTransaction(int inventoryTransationID) {
        return inventoryTransactionService.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    public List<IInventoryTransactionDto> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }
    
    
}
