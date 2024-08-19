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
import static me.yushi.inventorymanagementsystem.model.IInventoryTransaction.TransactionType.PURCHASE;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;

/**
 *
 * @author yushi
 */
public class InventoryTransactionService implements IInventoryTransactionService, IMapper<IInventoryTransactionDto, IInventoryTransaction> {

    private IInventoryTransactionRepository repository;

    public InventoryTransactionService(IInventoryTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public IInventoryTransactionDto createInventoryTransaction(IInventoryTransactionDto newInventoryTransactionDto) {
        IInventoryTransaction inventoryTransaton = toModel(newInventoryTransactionDto);
        IInventoryTransactionDto inventoryTransactionDto=toDto(repository.createInventoryTransaction(inventoryTransaton));
        this.save();
        return inventoryTransactionDto;
    }

    @Override
    public IInventoryTransactionDto updateInventoryTransaction(IInventoryTransactionDto updatedInventoryTransactionDto) {
        IInventoryTransaction inventoryTransaction = toModel(updatedInventoryTransactionDto);
        IInventoryTransactionDto inventoryTransactionDto=toDto(repository.updateInventoryTransaction(inventoryTransaction));
        this.save();
        return inventoryTransactionDto;
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
        List<IInventoryTransaction> transactions = repository.getAllInventoryTransations()
                .values().stream().collect(Collectors.toList());
        return transactions.stream().map(transaction -> this.toDto(transaction)).collect(Collectors.toList());

    }

    @Override
    public IInventoryTransactionDto toDto(IInventoryTransaction model) {
        return new InventoryTransactionDto.Builder()
                .productID(model.getProductID())
                .date(model.getDate())
                .quantity(model.getQuantity())
                .transactionType(mapModelToDtoType(model.getTransactionType()))
                .price(model.getPrice())
                .build();
    }

    private IInventoryTransactionDto.TransactionType mapModelToDtoType(IInventoryTransaction.TransactionType modelType) {
        switch (modelType) {
            case PURCHASE:
                return IInventoryTransactionDto.TransactionType.PURCHASE;
            case SALE:
                return IInventoryTransactionDto.TransactionType.SALE;
            case SPOILAGE:
                return IInventoryTransactionDto.TransactionType.SPOILAGE;
            default:
                throw new IllegalArgumentException("Unknown enum type: " + modelType);
        }
    }

    @Override
    public IInventoryTransaction toModel(IInventoryTransactionDto dto) {
        return new InventoryTransaction(dto.getTransactionID(),
                 dto.getProductID(),
                 dto.getQuantity(),
                 dto.getDate(),
                 mapDtoToModelType(dto.getTransactionType()),
                 dto.getPrice());

    }

    private IInventoryTransaction.TransactionType mapDtoToModelType(IInventoryTransactionDto.TransactionType dtoType) {
        switch (dtoType) {
            case PURCHASE:
                return IInventoryTransaction.TransactionType.PURCHASE;
            case SALE:
                return IInventoryTransaction.TransactionType.SALE;
            case SPOILAGE:
                return IInventoryTransaction.TransactionType.SPOILAGE;
            default:
                throw new IllegalArgumentException("Unknown enum type: " + dtoType);
        }
    }

    @Override
    public void save() {
        repository.save();
    }

}
