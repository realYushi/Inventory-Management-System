/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.model.IProduct;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;

/**
 *
 * @author yushi
 */
public class ProductService implements IProductService,IMapper<IProductDto, IProduct>{
    IProductRepository repository;

    public ProductService(IProductRepository repository) {
        this.repository=repository;
    }
    

    @Override
    public IProductDto createProduct(IProductDto newProductDto) {
        IProduct product=toModel(newProductDto);
        return toDto(repository.createProduct(product));
    }

    @Override
    public IProductDto updateProduct(IProductDto updatedProductDto) {
        IProduct product =toModel(updatedProductDto);
        return toDto(repository.updateProduct(product));
    }

    @Override
    public IProductDto getProductByID(int productID) {
        return toDto(repository.readProduct(productID));
    }

    @Override
    public boolean deleteProduct(int productID) {
        return repository.deleteProduct(productID);
    }

    @Override
    public List<IProductDto> getAllProducts() {
        List<IProduct> products = repository.getAllProducts().values().stream()
                .collect(Collectors.toList());
        return products.stream().map(product->this.toDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public IProductDto toDto(IProduct model) {
        return new ProductDto.Builder()
                .categoryID(model.getCategoryID())
                .expirationDate(model.getExpirationDate())
                .name(model.getName())
                .unit(model.getUnit())
                .price(model.getPrice())
                .productID(model.getProductID())
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public IProduct toModel(IProductDto dto) {
        return new Product(dto.getProductID(), 
                dto.getName(),
                dto.getCategoryID(),
                dto.getQuantity(),
                dto.getUnit(),
                dto.getPrice(),
                dto.getExpirationDate());
    }
    
}
