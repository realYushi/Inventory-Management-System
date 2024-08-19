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

    private Map<Integer, Product> productMap;
    private FileHandler<Product> productFileHandler;

    public ProductRepository(FileHandler<Product> fileHandler) throws IOException {
        productFileHandler=fileHandler;
        this.productMap = productFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(product -> product.getProductID(), product -> product));
    }

    @Override
    public Product createProduct(Product newProduct) {
        productMap.put(newProduct.getProductID(), newProduct);
        return newProduct;
    }

    @Override
    public Product readProduct(int productID) {
        return productMap.get(productID);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        productMap.put(updatedProduct.getProductID(), updatedProduct);
        return productMap.get(updatedProduct.getProductID());
    }

    @Override
    public boolean deleteProduct(int productID) {
        productMap.remove(productID);
        return productMap.containsKey(productID);
    }

    @Override
    public Map<Integer, Product> getAllProducts() {
        return productMap;
    }

    @Override
    public void save() {
        productFileHandler.writeToFile(productMap);
    }

}
