/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

/**
 *
 * @author yushi
 */
public interface IFinancialSummary {

    double getTotalSales();
    void getTotalSales(double totalSales);
    

    double getTotalCost();
    void setTotalCost(double totalCost);

    double getNetProfit();
    void setNetProfit(double netProfit);

    double getGrossMarginPercentage();
    void setGrossMarginPercentage(double grossMarginPercentage);

}
