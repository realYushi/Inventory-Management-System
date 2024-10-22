/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;

/**
 *
 * @author yushi
 */
public class InventoryTransactionRepository implements IInventoryTransactionRepository {
    public InventoryTransactionRepository(){
    }

    @Override
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction,EntityManager em) {
        em.persist(newInventoryTransaction);
        return newInventoryTransaction;

    }

    @Override
    public InventoryTransaction readInventoryTransaction(String inventoryTransationID,EntityManager em) {
        return em.find(InventoryTransaction.class, inventoryTransationID);
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction,EntityManager em) {
        return em.merge(updatedInventoryTransaction);
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID,EntityManager em) {
        if(em.find(InventoryTransaction.class, inventoryTransationID) != null){
            em.remove(em.find(InventoryTransaction.class, inventoryTransationID));
            return true;
        }
        return false;
    }

    @Override
    public List<InventoryTransaction> getAllInventoryTransations(EntityManager em) {
        
        return em.createQuery("SELECT i FROM InventoryTransaction i", InventoryTransaction.class).getResultList();
    }


}
