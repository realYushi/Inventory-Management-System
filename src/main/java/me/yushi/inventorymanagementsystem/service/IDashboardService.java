/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import me.yushi.inventorymanagementsystem.Dto.IFinancialSummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IInventorySummaryDto;

/**
 *
 * @author yushi
 */
public interface IDashboardService {
    IFinancialSummaryDto getFinancialSummary();
    IInventorySummaryDto getIentorySummary();
}
