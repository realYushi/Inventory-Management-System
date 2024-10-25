/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public interface IProductService {
    Product createProduct(Product newProduct);
    Product updateProduct(Product updatedProduct);
    Product getProductByID(String productID);
    boolean deleteProduct(String productID);
    List<Product> getAllProducts();
}
