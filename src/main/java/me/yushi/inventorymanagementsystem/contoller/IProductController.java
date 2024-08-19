/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;

/**
 *
 * @author yushi
 */
public interface IProductController{
    ProductDto createProduct(ProductDto newProductDto);
    ProductDto updateProduct(ProductDto updatedProductDto);
    ProductDto getProductByID(String productID);
    boolean deleteProduct(String productID);
    List<ProductDto> getAllProducts();
}
