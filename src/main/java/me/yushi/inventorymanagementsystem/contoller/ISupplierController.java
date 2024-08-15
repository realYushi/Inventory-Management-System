/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ISupplierDto;

/**
 *
 * @author yushi
 */
public interface ISupplierController {
    ISupplierDto createSupplier(ISupplierDto newSupplierDto);
    ISupplierDto updateSupplier(ISupplierDto updateSupplierDto);
    ISupplierDto getSupplierByID(int supplierID);
    boolean deleteSupplier(int supplierID);
    List<ISupplierDto> getAllSuppliers();
    
    
}
