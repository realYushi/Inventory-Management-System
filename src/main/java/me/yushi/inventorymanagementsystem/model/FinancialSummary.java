/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

/**
 *
 * @author yushi
 */
public class FinancialSummary implements IFinancialSummary {

    private double totalSales;
    private double totalCost;
    private double netProfit;
    private double grossMarginPercentage;

    public FinancialSummary(double totalSales, double totalCost, double netProfit) {
        this.totalSales = totalSales;
        this.totalCost = totalCost;
        this.netProfit = netProfit;
        this.grossMarginPercentage = calculateGrossMarginPercentage();
    }

    private double calculateGrossMarginPercentage() {
        if (totalSales != 0) {
            return ((totalSales - totalCost) / totalSales) * 100;
        } else {
            return 0;
        }
    }

    @Override
    public double getTotalSales() {
        return this.totalSales;

    }

    @Override
    public void getTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public double getTotalCost() {
        return this.totalCost;
    }

    @Override
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public double getNetProfit() {
        return this.netProfit;

    }

    @Override
    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    @Override
    public double getGrossMarginPercentage() {
        return this.grossMarginPercentage;
    }

    @Override
    public void setGrossMarginPercentage(double grossMarginPercentage) {
        this.grossMarginPercentage = grossMarginPercentage;
    }


}
