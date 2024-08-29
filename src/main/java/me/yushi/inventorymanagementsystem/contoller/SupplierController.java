/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.service.ISupplierService;
import me.yushi.inventorymanagementsystem.service.SupplierService;

/**
 *
 * @author yushi
 */
public class SupplierController implements ISupplierController{
    private ISupplierService supplierService;

    public SupplierController(IUnitOfWork unitOfWork) {
        this.supplierService=new SupplierService(unitOfWork);
    }
    

    @Override
    public SupplierDto createSupplier(SupplierDto newSupplierDto) {
        return supplierService.createSupplier(newSupplierDto);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto updateSupplierDto) {
        return supplierService.updateSupplier(updateSupplierDto);
    }

    @Override
    public SupplierDto getSupplierByID(String supplierID) {
        return supplierService.getSupplierByID(supplierID);
    }

    @Override
    public boolean deleteSupplier(String supplierID) {
        return supplierService.deleteSupplier(supplierID);

    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
    
}
