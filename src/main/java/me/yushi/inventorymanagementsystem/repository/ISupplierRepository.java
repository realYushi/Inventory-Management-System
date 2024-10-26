/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;
import java.util.List;
import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public interface ISupplierRepository {
    Supplier createSupplier(Supplier newSupplier,EntityManager em);
    Supplier readSupplier(String supplierID,EntityManager em);
    Supplier updateSupplier(Supplier updatedSupplier,EntityManager em);
    boolean deleteSupplier(String supplierID,EntityManager em);
    List<Supplier>getAllSuppliers(EntityManager em);
    boolean haveLinkedProduct(String supplierID,EntityManager em);
    
}
