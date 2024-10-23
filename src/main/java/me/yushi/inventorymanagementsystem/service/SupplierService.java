/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class SupplierService implements ISupplierService, IMapper<SupplierDto, Supplier> {
    SupplierRepository repository;
    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    // Create a new supplier, save it to the repository, and return the created
    public SupplierDto createSupplier(SupplierDto newSupplierDto) {

        Supplier supplier=TransactionUtil.executeTransaction(em -> {
            return repository.createSupplier(toModel(newSupplierDto), em);
        });
        if(supplier==null){
            System.out.println("Failed to create supplier : " + newSupplierDto.getSupplierName());
            return null;
        }
        return newSupplierDto;
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto updatedSupplierDto) {
        Supplier newSupplier=TransactionUtil.executeTransaction(em -> {
            return repository.updateSupplier(toModel(updatedSupplierDto), em);
        });
        return toDto(newSupplier);
    }

    @Override
    public SupplierDto getSupplierByID(String supplierDtoID) {
        Supplier supplier = TransactionUtil.executeTransaction(em -> {
            return repository.readSupplier(supplierDtoID, em);
        });
        if (supplier != null) {
            System.out.println("No supplier found with ID: " + supplierDtoID);
            return null;
        }
        return toDto(supplier);
        
    }

    @Override
    // Delete a supplier by its ID
    public boolean deleteSupplier(String supplierDtoID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteSupplier(supplierDtoID, em);
        });
    }

    @Override
    // Get all suppliers in the repository
    public List<SupplierDto> getAllSuppliers() {
        List <Supplier> suppliers = TransactionUtil.executeTransaction(em -> {
            return repository.getAllSuppliers(em);
        });
        return suppliers.stream().map(supplier -> this.toDto(supplier))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto toDto(Supplier model) {
        if (model == null) {
            return null;
        }
        return new SupplierDto.Builder()
                .supplierID(model.getSupplierID())
                .supplierName(model.getSupplierName())
                .build();
    }

    @Override
    public Supplier toModel(SupplierDto dto) {
        return new Supplier(dto.getSupplierID(), dto.getSupplierName());
    }


}
