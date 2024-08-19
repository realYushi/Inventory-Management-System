/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.Map;
import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public interface IProductRepository {
    Product createProduct(Product newProduct);
    Product readProduct(String productID);
    Product updateProduct(Product updatedProduct);
    boolean deleteProduct(String productID);
    Map<String,Product> getAllProducts();
    void save();
}
