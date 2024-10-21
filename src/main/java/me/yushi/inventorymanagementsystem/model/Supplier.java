/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 *
 * @author yushi
 */
@Entity
public class Supplier implements ISupplier {


    @Id
    private String supplierID;
    @Column(name = "supplierName", nullable = false)
    private String supplierName;
    @OneToMany(mappedBy = "supplier")
    private List<IProduct> products = new ArrayList<>();
    public Supplier() {
    }
    public Supplier(String supplierID,String supplierName) {
        this.supplierID = (supplierID==null?UUID.randomUUID().toString():supplierID);
        this.supplierName = supplierName;
    }

    @Override
    public String getSupplierID() {
        return this.supplierID;
    }


    @Override
    public String getSupplierName() {
        return this.supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    @Override
    public List<IProduct> getProducts() {
        return this.products;
    }

}
