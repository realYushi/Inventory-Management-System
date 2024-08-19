/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import static me.yushi.inventorymanagementsystem.model.IInventoryTransaction.TransactionType.PURCHASE;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService implements IInventoryTransactionService, IMapper<InventoryTransactionDto, InventoryTransaction> {

    private InventoryTransactionRepository repository;

    public InventoryTransactionService(InventoryTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newInventoryTransactionDto) {
        InventoryTransaction inventoryTransaton = toModel(newInventoryTransactionDto);
        InventoryTransactionDto inventoryTransactionDto=toDto(repository.createInventoryTransaction(inventoryTransaton));
        this.save();
        return inventoryTransactionDto;
    }

    @Override
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        InventoryTransaction inventoryTransaction = toModel(updatedInventoryTransactionDto);
        InventoryTransactionDto inventoryTransactionDto=toDto(repository.updateInventoryTransaction(inventoryTransaction));
        this.save();
        return inventoryTransactionDto;
    }

    @Override
    public InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID) {
        return toDto(repository.readInventoryTransaction(inventoryTransationID));
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        boolean result =repository.deleteInventoryTransaction(inventoryTransationID);
        this.save();
        return result;
    }

    @Override
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        List<InventoryTransaction> transactions = repository.getAllInventoryTransations()
                .values().stream().collect(Collectors.toList());
        return transactions.stream().map(transaction -> this.toDto(transaction)).collect(Collectors.toList());

    }

    @Override
    public InventoryTransactionDto toDto(InventoryTransaction model) {
        return new InventoryTransactionDto.Builder()
                .productID(model.getProductID())
                .date(model.getDate())
                .quantity(model.getQuantity())
                .transactionType(mapModelToDtoType(model.getTransactionType()))
                .price(model.getPrice())
                .build();
    }

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
    public InventoryTransaction toModel(InventoryTransactionDto dto) {
        return new InventoryTransaction(dto.getTransactionID(),
                 dto.getProductID(),
                 dto.getQuantity(),
                 dto.getDate(),
                 mapDtoToModelType(dto.getTransactionType()),
                 dto.getPrice());

    }

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

    @Override
    public void save() {
        repository.save();
    }

}
