/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public interface ISupplierController {
    Supplier createSupplier(Supplier newSupplier);
    Supplier updateSupplier(Supplier updateSupplier);
    Supplier getSupplierByID(String supplierID);
    boolean deleteSupplier(String supplierID);
    List<Supplier> getAllSuppliers();
    boolean haveLinkedProduct(String supplierID);
    
    
}
