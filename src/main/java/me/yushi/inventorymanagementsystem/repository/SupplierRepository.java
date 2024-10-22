/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public class SupplierRepository implements ISupplierRepository {
    public SupplierRepository() {
    }

    @Override
    public Supplier createSupplier(Supplier newSupplier,EntityManager em) {
        em.persist(newSupplier);
        return newSupplier;
    }

    @Override
    public Supplier readSupplier(String supplierID,EntityManager em) {
        return em.find(Supplier.class, supplierID);
    }

    @Override
    public Supplier updateSupplier(Supplier updatedSupplier,EntityManager em) {
        return em.merge(updatedSupplier);
    }

    @Override
    public boolean deleteSupplier(String supplierID,EntityManager em) {
        if (em.find(Supplier.class, supplierID) != null) {
            em.remove(em.find(Supplier.class, supplierID));
            return true;
        }
        return false;
    }

    @Override
    public List<Supplier> getAllSuppliers(EntityManager em) {
        return em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
    }


}
