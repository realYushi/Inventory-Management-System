/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService
        implements IInventoryTransactionService, IMapper<InventoryTransactionDto, InventoryTransaction> {
    private InventoryTransactionRepository repository;
    public InventoryTransactionService(InventoryTransactionRepository repository) {
        this.repository = repository; 
    }

    @Override
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newInventoryTransactionDto) {
        InventoryTransaction inventoryTransaton = TransactionUtil.executeTransaction(em -> {
            return repository.createInventoryTransaction(toModel(newInventoryTransactionDto), em);
        }); 
        if(inventoryTransaton == null){
            System.out.println("Failed to create inventory transaction: " + newInventoryTransactionDto.getTransactionID());
            return null;
        }
        return newInventoryTransactionDto;
    }

    @Override
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        InventoryTransaction inventoryTransaction = TransactionUtil.executeTransaction(em -> {
            return repository.updateInventoryTransaction(toModel(updatedInventoryTransactionDto), em);
        }); 
        if(inventoryTransaction == null){
            System.out.println("Failed to update inventory transaction: " + updatedInventoryTransactionDto.getTransactionID());
            return null;
        }
        return updatedInventoryTransactionDto;
    }

    @Override
    // Get an inventory transaction by its ID
    public InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID) {
        InventoryTransaction inventoryTransaction = TransactionUtil.executeTransaction(em -> {
            return repository.readInventoryTransaction(inventoryTransationID, em);
        });
        if(inventoryTransaction == null){
            System.out.println("No inventory transaction found with ID: " + inventoryTransationID);
            return null;
        }
        return toDto(inventoryTransaction);
    }

    @Override
    // Delete an inventory transaction by its ID
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteInventoryTransaction(inventoryTransationID, em);
        });
    }

    @Override
    // Get all inventory transactions, convert them to DTOs, and return the list of
    // DTOs
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        return TransactionUtil.executeTransaction(em -> {
            return repository.getAllInventoryTransations(em).stream().map(this::toDto).collect(Collectors.toList());
        });

    }

    @Override
    // Convert a model to a DTO
    public InventoryTransactionDto toDto(InventoryTransaction model) {
        return new InventoryTransactionDto.Builder()
                .transactionID(model.getTransactionID())
                .productID(model.getProductID())
                .date(model.getDate())
                .quantity(model.getQuantity())
                .transactionType(mapModelToDtoType(model.getTransactionType()))
                .price(model.getPrice())
                .build();
    }

    @Override
    // Convert a DTO to a model
    public InventoryTransaction toModel(InventoryTransactionDto dto) {
        return new InventoryTransaction(
                dto.getTransactionID(),
                dto.getProductID(),
                dto.getQuantity(),
                dto.getDate(),
                mapDtoToModelType(dto.getTransactionType()),
                dto.getPrice());

    }

    // Convert the Enum type to model
    private InventoryTransaction.TransactionType mapDtoToModelType(InventoryTransactionDto.TransactionType dtoType) {
        switch (dtoType) {
            case PURCHASE:
                return InventoryTransaction.TransactionType.PURCHASE;
            case SALE:
                return InventoryTransaction.TransactionType.SALE;
            case SPOILAGE:
                return InventoryTransaction.TransactionType.SPOILAGE;
            default:
                throw new IllegalArgumentException("Unknown enum type: " + dtoType);
        }
    }

    // Convert the Enum type to DTO
    private InventoryTransactionDto.TransactionType mapModelToDtoType(InventoryTransaction.TransactionType modelType) {
        switch (modelType) {
            case PURCHASE:
                return InventoryTransactionDto.TransactionType.PURCHASE;
            case SALE:
                return InventoryTransactionDto.TransactionType.SALE;
            case SPOILAGE:
                return InventoryTransactionDto.TransactionType.SPOILAGE;
            default:
                throw new IllegalArgumentException("Unknown enum type: " + modelType);
        }
    }


}
