/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

/**
 *
 * @author yushi
 */
public class FinancialSummaryDto implements IFinancialSummaryDto {

    private final double totalSales;
    private final double totalCost;
    private final double netProfit;
    private final double grossMarginPercentage;

    private FinancialSummaryDto(Builder builder) {
        this.totalSales = builder.totalSales;
        this.totalCost = builder.totalCost;
        this.netProfit = builder.netProfit;
        this.grossMarginPercentage = builder.grossMarginPercentage;
    }

    public class Builder {

        private double totalSales;
        private double totalCost;
        private double netProfit;
        private double grossMarginPercentage;

        public Builder totalSales(double totalSale) {
            this.totalSales = totalSale;
            return this;

        }

        public Builder totalCost(double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Builder netProfit(double netProfit) {
            this.netProfit = netProfit;
            return this;
        }

        public Builder grossMarginPercentage(double grossMarginPercentage) {
            this.grossMarginPercentage = grossMarginPercentage;
            return this;
        }

        public FinancialSummaryDto build() {
            return new FinancialSummaryDto(this);
        }

    }

    @Override
    public double getTotalSales() {
        return this.totalSales;
    }

    @Override
    public double getTotalCost() {
        return this.totalCost;
    }

    @Override
    public double getNetProfit() {
        return  this.netProfit;
    }

    @Override
    public double getGrossMarginPercentage() {
        return this.grossMarginPercentage;
    }

}
