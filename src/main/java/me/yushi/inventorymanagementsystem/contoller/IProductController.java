/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;

/**
 *
 * @author yushi
 */
public interface IProductController{
    Product createProduct(Product newProduct);
    Product updateProduct(Product updatedProduct);
    Product getProductByID(String productID);
    boolean deleteProduct(String productID);
    List<Product> getAllProducts();
    Category getCategory(Product product);
    Supplier getSupplier(Product product);
}
