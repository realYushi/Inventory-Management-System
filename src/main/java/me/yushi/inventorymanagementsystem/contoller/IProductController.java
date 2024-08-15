/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;

/**
 *
 * @author yushi
 */
public interface IProductController{
    IProductDto createProduct(IProductDto newProductDto);
    IProductDto updateProduct(IProductDto updatedProductDto);
    IProductDto getProductByID(int productID);
    boolean deleteProduct(int productID);
    List<IProductDto> getAllProducts();
}
