/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class SupplierService implements ISupplierService, IMapper<SupplierDto, Supplier> {

    private SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplierDto createSupplier(SupplierDto newSupplierDto) {
        Supplier supplier = toModel(newSupplierDto);
        SupplierDto supplierDto=toDto(repository.createSupplier(supplier));
        this.save();
        return supplierDto;
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto updatedSupplierDto) {
        Supplier supplier = toModel(updatedSupplierDto);
        SupplierDto supplierDto=toDto(repository.updateSupplier(supplier));
        this.save();
        return supplierDto;

    }

    @Override
    public SupplierDto getSupplierByID(int supplierDtoID) {
        return toDto(repository.readSupplier(supplierDtoID));
    }

    @Override
    public boolean deleteSupplier(int supplierDtoID) {
        return repository.deleteSupplier(supplierDtoID);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> suppliers = repository.getAllSuppliers().values().stream()
                .collect(Collectors.toList());
        return suppliers.stream().map(supplier -> this.toDto(supplier))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto toDto(Supplier model) {
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
