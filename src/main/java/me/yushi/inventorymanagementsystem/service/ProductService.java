/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.database.TransactionUtil;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
/**
 *
 * @author yushi
 */
public class ProductService implements IProductService, IMapper<ProductDto, Product> {
    ProductRepository repository  ;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    @Override
    public ProductDto createProduct(ProductDto newProductDto) {
        Product createdProduct=TransactionUtil.executeTransaction(em -> {
            return repository.createProduct(toModel(newProductDto), em);
        });
        if(createdProduct==null){
            System.out.println("Failed to create product : " + newProductDto.getName());
            return null;
        }
        return newProductDto;
    }

    @Override
    // Update a product, save it to the repository, and return the updated
    public ProductDto updateProduct(ProductDto updatedProductDto) {
        Product product = TransactionUtil.executeTransaction(em -> {
            return repository.updateProduct(toModel(updatedProductDto), em);
        });
        if (product == null) {
            System.out.println("Failed to update product: " + updatedProductDto.getName());
            return null;
        }
        
        return updatedProductDto;
    }

    @Override
    // Get a product by its ID
    public ProductDto getProductByID(String productID) {
        Product product = TransactionUtil.executeTransaction(em -> {
            return repository.readProduct(productID, em);
        });
        if (product == null) {
            System.out.println("No product found with ID: " + productID);
            return null;
        }
        return toDto(product);
    }

    @Override
    public boolean deleteProduct(String productID) {
        return TransactionUtil.executeTransaction(em -> {
            return repository.deleteProduct(productID, em);
        });
    }

    @Override
    // Get all products
    public List<ProductDto> getAllProducts() {
        return TransactionUtil.executeTransaction(em -> {
            return repository.getAllProducts(em).stream().map(this::toDto).collect(Collectors.toList());
        });
    }

    @Override
    // Convert a product to a DTO
    public ProductDto toDto(Product model) {
        return new ProductDto.Builder()
                .categoryID(model.getCategoryID())
                .supplierID(model.getSupplierID())
                .name(model.getName())
                .unit(model.getUnit())
                .price(model.getPrice())
                .productID(model.getProductID())
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    // Convert a DTO to a product
    public Product toModel(ProductDto dto) {
        return new Product(
                dto.getProductID(),
                dto.getName(),
                dto.getCategoryID(),
                dto.getSupplierID(),
                dto.getQuantity(),
                dto.getUnit(),
                dto.getPrice()
        );
         
    }



}
