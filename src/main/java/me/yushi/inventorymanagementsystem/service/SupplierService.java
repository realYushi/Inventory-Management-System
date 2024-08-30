/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class SupplierService implements ISupplierService, IMapper<SupplierDto, Supplier> {

    private SupplierRepository repository;

    public SupplierService(IUnitOfWork unitOfWork) {
        this.repository = unitOfWork.getSupplierRepository();
    }

    @Override
    // Create a new supplier, save it to the repository, and return the created
    public SupplierDto createSupplier(SupplierDto newSupplierDto) {

        Supplier supplier = toModel(newSupplierDto);
        SupplierDto supplierDto = toDto(repository.createSupplier(supplier));
        this.save();
        return supplierDto;
    }

    @Override
    // Update a supplier, save it to the repository, and return the updated
    public SupplierDto updateSupplier(SupplierDto updatedSupplierDto) {
        // Check if the supplier exists
        if (repository.readSupplier(updatedSupplierDto.getSupplierID()) == null) {
            System.out.print("No supplier found with ID: " + updatedSupplierDto.getSupplierID());
            return null;
        }
        Supplier supplier = toModel(updatedSupplierDto);
        SupplierDto supplierDto = toDto(repository.updateSupplier(supplier));
        this.save();
        return supplierDto;

    }

    @Override
    // Get a supplier by its ID
    public SupplierDto getSupplierByID(String supplierDtoID) {
        if (repository.readSupplier(supplierDtoID) == null) {
            System.out.print("No supplier found with ID: " + supplierDtoID);
            return null;
        }
        return toDto(repository.readSupplier(supplierDtoID));
    }

    @Override
    // Delete a supplier by its ID
    public boolean deleteSupplier(String supplierDtoID) {
        boolean result = repository.deleteSupplier(supplierDtoID);
        this.save();
        return result;
    }

    @Override
    // Get all suppliers in the repository
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = repository.getAllSuppliers().values().stream()
                .collect(Collectors.toList());
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

    @Override
    public void save() {
        repository.save();
    }

}
