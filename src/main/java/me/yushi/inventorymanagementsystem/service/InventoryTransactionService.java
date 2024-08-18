/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService implements IInventoryTransactionService,IMapper<IInventoryTransactionDto,IInventoryTransaction>{
    private IInventoryTransactionRepository repository;

    public InventoryTransactionService(IInventoryTransactionRepository repository) {
        this.repository=repository;
    }

    

    @Override
    public IInventoryTransactionDto createInventoryTransaction(IInventoryTransactionDto newInventoryTransactionDto) {
        IInventoryTransaction inventoryTransaton=toModel(newInventoryTransactionDto);
        return toDto(repository.createInventoryTransaction(inventoryTransaton));
    }

    @Override
    public IInventoryTransactionDto updateInventoryTransaction(IInventoryTransactionDto updatedInventoryTransactionDto) {
        IInventoryTransaction inventoryTransaction=toModel(updatedInventoryTransactionDto);
        return toDto(repository.updateInventoryTransaction(inventoryTransaction));
    }

    @Override
    public IInventoryTransactionDto getInventoryTransactionByID(int inventoryTransationID) {
        return toDto(repository.readInventoryTransaction(inventoryTransationID));
    }

    @Override
    public boolean deleteInventoryTransaction(int inventoryTransationID) {
        return repository.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    public List<IInventoryTransactionDto> getAllInventoryTransations() {
        List<IInventoryTransaction> transactions=repository.getAllInventoryTransations()
                .values().stream().collect(Collectors.toList());
        return transactions.stream().map(transaction->this.toDto(transaction)).collect(Collectors.toList());
        
    }

    @Override
    public IInventoryTransactionDto toDto(IInventoryTransaction model) {
        return new InventoryTransactionDto.Builder()
                .productID(model.getProductID())
                .date(model.getDate())
                .quantity(model.getQuantity())
                .transactionType(model.getTransactionType());
                
                
    }

    @Override
    public IInventoryTransaction toModel(IInventoryTransactionDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
