/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;

/**
 *
 * @author yushi
 */
public interface IProductService {
    ProductDto createProduct(ProductDto newProductDto);
    ProductDto updateProduct(ProductDto updatedProductDto);
    ProductDto getProductByID(int productID);
    boolean deleteProduct(int productID);
    List<ProductDto> getAllProducts();
    void save();
}
