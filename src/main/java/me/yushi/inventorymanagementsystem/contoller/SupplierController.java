/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ISupplierDto;
import me.yushi.inventorymanagementsystem.repository.ISupplierRepository;
import me.yushi.inventorymanagementsystem.service.ISupplierService;
import me.yushi.inventorymanagementsystem.service.SupplierService;

/**
 *
 * @author yushi
 */
public class SupplierController implements ISupplierController{
    private ISupplierService supplierService;

    public SupplierController(ISupplierRepository repository) {
        this.supplierService=new SupplierService(repository);
    }
    

    @Override
    public ISupplierDto createSupplier(ISupplierDto newSupplierDto) {
        return supplierService.createSupplier(newSupplierDto);
    }

    @Override
    public ISupplierDto updateSupplier(ISupplierDto updateSupplierDto) {
        return supplierService.updateSupplier(updateSupplierDto);
    }

    @Override
    public ISupplierDto getSupplierByID(int supplierID) {
        return supplierService.getSupplierByID(supplierID);
    }

    @Override
    public boolean deleteSupplier(int supplierID) {
        return supplierService.deleteSupplier(supplierID);

    }

    @Override
    public List<ISupplierDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
    
}
