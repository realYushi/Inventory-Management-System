/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public interface ISupplierRepository {
    Supplier createSupplier(Supplier newSupplier);
    Supplier readSupplier(String supplierID);
    Supplier updateSupplier(Supplier updatedSupplier);
    boolean deleteSupplier(String supplierID);
    Map<String,Supplier>getAllSuppliers();
    void save();
    
}
