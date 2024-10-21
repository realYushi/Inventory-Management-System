/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;
import java.util.List;

/**
 *
 * @author yushi
 */
public interface ISupplier {
    String getSupplierID();
    String getSupplierName();
    void setSupplierName(String supplierName);
    List<IProduct> getProducts();
    
}
