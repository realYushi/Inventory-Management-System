/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public class ProductRepository implements IProductRepository {

    private Map<String, Product> productMap;
    private FileHandler<Product> productFileHandler;

    public ProductRepository(FileHandler<Product> fileHandler) throws IOException {
        // Read all products from file and store them in a map
        productFileHandler = fileHandler;
        this.productMap = productFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(product -> product.getProductID(), product -> product));
    }

    @Override
    public Product createProduct(Product newProduct) {
        // Add new product to the map
        productMap.put(newProduct.getProductID(), newProduct);
        return newProduct;
    }

    @Override
    public Product readProduct(String productID) {
        // Read product from the map
        return productMap.get(productID);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        // Update product in the map
        productMap.put(updatedProduct.getProductID(), updatedProduct);
        return productMap.get(updatedProduct.getProductID());
    }

    @Override
    public boolean deleteProduct(String productID) {
        // Delete product from the map
        productMap.remove(productID);
        return productMap.containsKey(productID);
    }

    @Override
    public Map<String, Product> getAllProducts() {
        // Return all products
        return productMap;
    }

    @Override
    public void save() {
        // Write all products to file
        productFileHandler.writeToFile(productMap);
    }

}
