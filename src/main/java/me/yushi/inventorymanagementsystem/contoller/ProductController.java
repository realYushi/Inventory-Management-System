/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController {
    private ProductService productService;

    public ProductController(ProductService productService ) {
        this.productService = productService;
    }

    @Override
    // Create a new product
    public ProductDto createProduct(ProductDto newProductDto) {
        if(newProductDto == null) {
            System.out.println("ProductDto is null");
            return null;
        }
        return productService.createProduct(newProductDto);
    }

    @Override
    // Update a product
    public ProductDto updateProduct(ProductDto updatedProductDto) {
        if(updatedProductDto == null) {
            System.out.println("Updated ProductDto is null");
            return null;
        }
        return productService.updateProduct(updatedProductDto);
    }

    @Override
    // Get a product by its ID
    public ProductDto getProductByID(String productID) {
        if(productID == null || productID.isEmpty()) {
            System.out.println("Product ID is null or empty");
            return null;
        }
        return productService.getProductByID(productID);
    }

    @Override
    // Delete a product
    public boolean deleteProduct(String productID) {
        if(productID == null || productID.isEmpty()) {
            System.out.println("Product ID is null or empty");
            return false;
        }
        return productService.deleteProduct(productID);
    }

    @Override
    // Get all products
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public String getCategoryID(ProductDto productDto) {
        if(productDto == null) {
            System.out.println("ProductDto is null");
            return null;
        }
        return productDto.getCategoryID();
    }

    @Override
    public String getSupplierID(ProductDto productDto) {
        if(productDto == null) {
            System.out.println("ProductDto is null");
            return null;
        }
        return productDto.getSupplierID();
    }
}

