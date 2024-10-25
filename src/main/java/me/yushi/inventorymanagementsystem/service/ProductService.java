/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
/**
 *
 * @author yushi
 */
public class ProductService implements IProductService {
    ProductRepository repository  ;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    @Override
    public Product createProduct(Product newProduct) {
        Product createdProduct=TransactionUtil.executeTransaction(em -> {
            return repository.createProduct((newProduct), em);
        });
        if(createdProduct==null){
            System.out.println("Failed to create product : " + newProduct.getName());
            return null;
        }
        return newProduct;
    }

    @Override
    // Update a product, save it to the repository, and return the updated
    public Product updateProduct(Product updatedProduct) {
        Product product = TransactionUtil.executeTransaction(em -> {
            return repository.updateProduct((updatedProduct), em);
        });
        if (product == null) {
            System.out.println("Failed to update product: " + updatedProduct.getName());
            return null;
        }
        
        return updatedProduct;
    }

    @Override
    // Get a product by its ID
    public Product getProductByID(String productID) {
        Product product = TransactionUtil.executeTransaction(em -> {
            return repository.readProduct(productID, em);
        });
        if (product == null) {
            System.out.println("No product found with ID: " + productID);
            return null;
        }
        return product;
    }

    @Override
    public boolean deleteProduct(String productID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteProduct(productID, em);
        });
    }

    @Override
    // Get all products
    public List<Product> getAllProducts() {
        List<Product> products = TransactionUtil.executeTransaction(em -> {
            return repository.getAllProducts(em);
        });
        if (products == null) {
            System.out.println("No products found");
            return null;
        }
        return products;
        
        
    }

    


}
