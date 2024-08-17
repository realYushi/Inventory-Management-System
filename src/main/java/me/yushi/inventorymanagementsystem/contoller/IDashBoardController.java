/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import me.yushi.inventorymanagementsystem.model.IFinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventorySummary;

/**
 *
 * @author yushi
 */
public interface IDashBoardController {
    IInventorySummary getInventorySummary();
    IFinancialSummary getFinancialSummary();
}
