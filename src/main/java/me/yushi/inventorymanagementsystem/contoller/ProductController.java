/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController{
    private ProductService productService;

    public ProductController(ProductRepository repository) {
        this.productService=new ProductService(repository);
    }
    

    @Override
    public ProductDto createProduct(ProductDto newProductDto) {
        return productService.createProduct(newProductDto);
    }

    @Override
    public ProductDto updateProduct(ProductDto updatedProductDto) {
        return productService.updateProduct(updatedProductDto);
    }

    @Override
    public ProductDto getProductByID(int productID) {
        return productService.getProductByID(productID);
    }

    @Override
    public boolean deleteProduct(int productID) {
        return productService.deleteProduct(productID);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    
    
}
