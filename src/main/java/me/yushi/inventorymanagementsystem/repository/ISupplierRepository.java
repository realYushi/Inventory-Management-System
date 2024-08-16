/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.ISupplier;

/**
 *
 * @author yushi
 */
public interface ISupplierRepository {
    ISupplier createSupplier(ISupplier newSupplier);
    ISupplier readSupplier(int supplierID);
    ISupplier updateSupplier(ISupplier updatedSupplier);
    boolean deleteSupplier(int supplierID);
    Map<Integer,ISupplier>getAllSuppliers();
    void save();
    
}
