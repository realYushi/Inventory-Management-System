package me.yushi.inventorymanagementsystem.Dto;

import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author yushi
 */
public interface IInventorySummaryDto {

    List<IProductDto> getRecentProductDtos();

    List<ICategoryDto> getRecentCategoryDtos();

    List<ISupplierDto> getRecentSupplierDtos();

    List<IInventoryTransactionDto> getRecentInventoryTransactionDtos();

}
