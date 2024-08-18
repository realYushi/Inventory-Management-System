/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;
import me.yushi.inventorymanagementsystem.service.IProductService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController{
    private IProductService productService;

    public ProductController(IProductRepository repository) {
        this.productService=new ProductService(repository);
    }
    

    @Override
    public IProductDto createProduct(IProductDto newProductDto) {
        return productService.createProduct(newProductDto);
    }

    @Override
    public IProductDto updateProduct(IProductDto updatedProductDto) {
        return productService.updateProduct(updatedProductDto);
    }

    @Override
    public IProductDto getProductByID(int productID) {
        return productService.getProductByID(productID);
    }

    @Override
    public boolean deleteProduct(int productID) {
        return productService.deleteProduct(productID);
    }

    @Override
    public List<IProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
    
    
}
