/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(IUnitOfWork unitOfWork) {
        this.productService = new ProductService(unitOfWork);
        this.categoryService = new CategoryService(unitOfWork);
    }

    @Override
    // Create a new product
    public ProductDto createProduct(ProductDto newProductDto) {
        return productService.createProduct(newProductDto);
    }

    @Override
    // Update a product
    public ProductDto updateProduct(ProductDto updatedProductDto) {
        return productService.updateProduct(updatedProductDto);
    }

    @Override
    // Get a product by its ID
    public ProductDto getProductByID(String productID) {
        return productService.getProductByID(productID);
    }

    @Override
    // Delete a product
    public boolean deleteProduct(String productID) {
        return productService.deleteProduct(productID);
    }

    @Override
    // Get all products
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    // Get a category by its ID
    public CategoryDto getCategoryById(String categoryID) {
        return categoryService.getCategoryByID(categoryID);

    }

    @Override
    // Get all categories
    public List<CategoryDto> getAllCategory() {
        return categoryService.getAllCategorys();
    }

}
