/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;
import me.yushi.inventorymanagementsystem.model.IProduct;

/**
 *
 * @author yushi
 */
public interface IProductRepository {
    IProduct createProduct(IProduct newProduct);
    IProduct readProduct(int productID);
    IProduct updateProduct(IProduct updatedProduct);
    boolean deleteProduct(int productID);
    void save();
    List<IProduct> getAllProducts();
}
