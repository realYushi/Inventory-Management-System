/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.InventorySummary;

/**
 *
 * @author yushi
 */
public interface IDashBoardController {
    InventorySummary getInventorySummary();
    FinancialSummary getFinancialSummary();
}
