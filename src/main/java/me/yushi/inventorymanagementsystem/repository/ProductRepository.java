/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.model.IProduct;

/**
 *
 * @author yushi
 */
public class ProductRepository implements IProductRepository {

    private Map<Integer, IProduct> productMap;
    private IFileHandler<IProduct> productFileHandler;

    public ProductRepository(String filePath) throws IOException {
        productFileHandler = new FileHandler<>(IProduct.class, filePath);
        this.productMap = productFileHandler.readFromFile()
                .stream()
                .collect(Collectors.toMap(product -> product.getProductID(), product -> product));
    }

    @Override
    public IProduct createProduct(IProduct newProduct) {
        productMap.put(newProduct.getProductID(), newProduct);
        return newProduct;
    }

    @Override
    public IProduct readProduct(int productID) {
        return productMap.get(productID);
    }

    @Override
    public IProduct updateProduct(IProduct updatedProduct) {
        productMap.put(updatedProduct.getProductID(), updatedProduct);
        return productMap.get(updatedProduct.getProductID());
    }

    @Override
    public boolean deleteProduct(int productID) {
        productMap.remove(productID);
        return productMap.containsKey(productID);
    }

    @Override
    public Map<Integer, IProduct> getAllProducts() {
        return productMap;
    }

}
