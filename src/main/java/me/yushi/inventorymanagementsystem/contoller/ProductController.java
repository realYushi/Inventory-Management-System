/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController{
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productService=new ProductService(productRepository);
        this.categoryService=new CategoryService(categoryRepository);
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
    public ProductDto getProductByID(String productID) {
        return productService.getProductByID(productID);
    }

    @Override
    public boolean deleteProduct(String productID) {
        return productService.deleteProduct(productID);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public CategoryDto getCategory(String categoryID) {
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryService.getAllCategorys();
    }
    
    
}
