/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;

/**
 *
 * @author yushi
 */
public class ProductService implements IProductService, IMapper<ProductDto, Product> {

    IProductRepository repository;

    public ProductService(IUnitOfWork unitOfWork) {
        this.repository = unitOfWork.getProductRepository();
    }

    @Override
    public ProductDto createProduct(ProductDto newProductDto) {
        Product product = toModel(newProductDto);

        ProductDto productDto = toDto(repository.createProduct(product));
        this.save();
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto updatedProductDto) {
        Product product = toModel(updatedProductDto);
        ProductDto productDto = toDto(repository.updateProduct(product));
        this.save();
        return productDto;
    }

    @Override
    public ProductDto getProductByID(String productID) {
        Product product = repository.readProduct(productID);
        if (product == null) {
            System.out.print("No product found with ID: " + productID);
            return null;
        }

        return toDto(product);
    }

    @Override
    public boolean deleteProduct(String productID) {
        boolean result = repository.deleteProduct(productID);
        this.save();
        return result;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = repository.getAllProducts().values().stream()
                .collect(Collectors.toList());
        return products.stream().map(product -> this.toDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto toDto(Product model) {
        if (model == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return new ProductDto.Builder()
                .categoryID(model.getCategoryID())
                .name(model.getName())
                .unit(model.getUnit())
                .price(model.getPrice())
                .productID(model.getProductID())
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public Product toModel(ProductDto dto) {
        return new Product(
                dto.getProductID(),
                dto.getName(),
                dto.getCategoryID(),
                dto.getQuantity(),
                dto.getUnit(),
                dto.getPrice()
        );
    }

    @Override
    public void save() {
        repository.save();
    }

}
