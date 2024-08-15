/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.IFinancialSummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IInventorySummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;

/**
 *
 * @author yushi
 */
public interface IDashBoardController {
    IInventorySummaryDto getInventorySummaryDto(int timeRange);
    List<IProductDto> getLowStockProduct();
    List<IProductDto> getExpiratedSoonProduct();
    IFinancialSummaryDto getFinancialSummaryDto();
}
