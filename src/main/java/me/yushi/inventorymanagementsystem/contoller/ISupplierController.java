/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;

/**
 *
 * @author yushi
 */
public interface ISupplierController {
    SupplierDto createSupplier(SupplierDto newSupplierDto);
    SupplierDto updateSupplier(SupplierDto updateSupplierDto);
    SupplierDto getSupplierByID(String supplierID);
    boolean deleteSupplier(String supplierID);
    List<SupplierDto> getAllSuppliers();
    
    
}
