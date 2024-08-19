/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ISupplierDto;

/**
 *
 * @author yushi
 */
public interface ISupplierService {
    ISupplierDto createSupplier(ISupplierDto newSupplierDto);
    ISupplierDto updateSupplier(ISupplierDto updatedSupplierDto);
    ISupplierDto getSupplierByID(int supplierDtoID);
    boolean deleteSupplier(int supplierDtoID);
    List<ISupplierDto> getAllSuppliers();
    void save();
    
    
    
}
