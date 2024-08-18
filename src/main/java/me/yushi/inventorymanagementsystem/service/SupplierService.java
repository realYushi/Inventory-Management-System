/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.ISupplierDto;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.model.ISupplier;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.ISupplierRepository;

/**
 *
 * @author yushi
 */
public class SupplierService implements ISupplierService, IMapper<ISupplierDto, ISupplier> {

    private ISupplierRepository repository;

    public SupplierService(ISupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    public ISupplierDto createSupplier(ISupplierDto newSupplierDto) {
        ISupplier supplier = toModel(newSupplierDto);
        return toDto(repository.createSupplier(supplier));
    }

    @Override
    public ISupplierDto updateSupplier(ISupplierDto updatedSupplierDto) {
        ISupplier supplier = toModel(updatedSupplierDto);
        return toDto(repository.updateSupplier(supplier));

    }

    @Override
    public ISupplierDto getSupplierByID(int supplierDtoID) {
        return toDto(repository.readSupplier(supplierDtoID));
    }

    @Override
    public boolean deleteSupplier(int supplierDtoID) {
        return repository.deleteSupplier(supplierDtoID);
    }

    @Override
    public List<ISupplierDto> getAllSuppliers() {
        List<ISupplier> suppliers = repository.getAllSuppliers().values().stream()
                .collect(Collectors.toList());
        return suppliers.stream().map(supplier -> this.toDto(supplier))
                .collect(Collectors.toList());
    }

    @Override
    public ISupplierDto toDto(ISupplier model) {
        return new SupplierDto.Builder()
                .supplierID(model.getSupplierID())
                .supplierName(model.getSupplierName())
                .build();
    }

    @Override
    public ISupplier toModel(ISupplierDto dto) {
        return new Supplier(dto.getSupplierID(), dto.getSupplierName());
    }

}
