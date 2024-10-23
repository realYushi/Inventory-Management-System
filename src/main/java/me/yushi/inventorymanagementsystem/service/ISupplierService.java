/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;

/**
 *
 * @author yushi
 */
public interface ISupplierService {
    SupplierDto createSupplier(SupplierDto newSupplierDto);
    SupplierDto updateSupplier(SupplierDto updatedSupplierDto);
    SupplierDto getSupplierByID(String supplierDtoID);
    boolean deleteSupplier(String supplierDtoID);
    List<SupplierDto> getAllSuppliers();
}
