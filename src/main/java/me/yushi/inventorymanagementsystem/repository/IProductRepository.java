/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;
import java.util.List;
import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public interface IProductRepository {
    Product createProduct(Product newProduct,EntityManager em);
    Product readProduct(String productID,EntityManager em);
    Product updateProduct(Product updatedProduct,EntityManager em);
    boolean deleteProduct(String productID,EntityManager em);
    List<Product> getAllProducts(EntityManager em);
}
