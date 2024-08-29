/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService
        implements IInventoryTransactionService, IMapper<InventoryTransactionDto, InventoryTransaction> {

    private InventoryTransactionRepository repository;

    public InventoryTransactionService(IUnitOfWork unitOfWork) {
        this.repository = unitOfWork.getInventoryTransactionRepository();
    }

    @Override
    // Create a new inventory transaction, save it to the repository, and return the
    // created
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newInventoryTransactionDto) {
        InventoryTransaction inventoryTransaton = toModel(newInventoryTransactionDto);
        if (inventoryTransaton == null) {
            throw new IllegalArgumentException("Transaction Model cannot be null");
        }
        InventoryTransactionDto inventoryTransactionDto = toDto(
                repository.createInventoryTransaction(inventoryTransaton));
        this.save();
        return inventoryTransactionDto;
    }

    @Override
    // Update an inventory transaction, save it to the repository, and return the
    // updated
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        InventoryTransaction inventoryTransaction = toModel(updatedInventoryTransactionDto);
        // Check if the inventory transaction exists
        if (repository.readInventoryTransaction(inventoryTransaction.getTransactionID()) == null) {
            System.out.print("No inventory transaction found with ID: " + inventoryTransaction.getTransactionID());
            return null;
        }
        InventoryTransactionDto inventoryTransactionDto = toDto(
                repository.updateInventoryTransaction(inventoryTransaction));
        this.save();
        return inventoryTransactionDto;
    }

    @Override
    // Get an inventory transaction by its ID
    public InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID) {
        if (repository.readInventoryTransaction(inventoryTransationID) == null) {
            System.out.print("No inventory transaction found with ID: " + inventoryTransationID);
            return null;
        }
        return toDto(repository.readInventoryTransaction(inventoryTransationID));
    }

    @Override
    // Delete an inventory transaction by its ID
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        boolean result = repository.deleteInventoryTransaction(inventoryTransationID);
        this.save();
        return result;
    }

    @Override
    // Get all inventory transactions, convert them to DTOs, and return the list of
    // DTOs
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        List<InventoryTransaction> transactions = repository.getAllInventoryTransations()
                .values().stream().collect(Collectors.toList());
        return transactions.stream().map(transaction -> this.toDto(transaction)).collect(Collectors.toList());

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
        return new InventoryTransaction(dto.getTransactionID(),
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

    @Override
    public void save() {
        repository.save();
    }

}
