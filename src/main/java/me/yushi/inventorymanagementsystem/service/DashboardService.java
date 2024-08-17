/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.yushi.inventorymanagementsystem.Dto.FinancialSummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IFinancialSummaryDto;
import me.yushi.inventorymanagementsystem.Dto.IInventorySummaryDto;
import me.yushi.inventorymanagementsystem.model.FinancialSummary;
import me.yushi.inventorymanagementsystem.model.IFinancialSummary;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.service.IDashboardService;

/**
 *
 * @author yushi
 */
public class DashboardService implements IDashboardService {
    IInventoryTransactionRepository inventoryTransactionRepository;
    private final int DATE_RANGE=30;
    private final double PROFIT_RATE=1.2;
    
    


    public DashboardService(IInventoryTransactionRepository inventoryTransactionRepository) {
        this.inventoryTransactionRepository=inventoryTransactionRepository;
    }

    @Override
    public IFinancialSummaryDto getFinancialSummary() {
        Map<Integer,IInventoryTransaction> allData=inventoryTransactionRepository.getAllInventoryTransations();
        
        Date currentDate=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -DATE_RANGE);
        Date date30DaysAgo=calendar.getTime();

        List<IInventoryTransaction>recentData=allData.values().stream()
             .filter(transation->transation.getDate().after(date30DaysAgo))
             .collect(Collectors.toList());
        double totalSales=0;
        double totalCost=0;
        for (IInventoryTransaction t: recentData) {
            switch (t.getTransactionType()) {
                case SALE:
                    totalSales=totalSales+(t.getPrice()*PROFIT_RATE);
                    break;
                case PURCHASE:
                    totalCost=totalCost+t.getPrice();
                    break;
                case SPOILAGE:
                    totalCost=totalCost+t.getPrice();
                    break;
                default:
                    throw new AssertionError();
            }

            
        }
        IFinancialSummary financialSummary=new FinancialSummary(totalSales, totalCost);
        IFinancialSummaryDto financialSummaryDto=new FinancialSummaryDto.Builder()
                .grossMarginPercentage(financialSummary.getGrossMarginPercentage())
                .netProfit(financialSummary.getNetProfit())
                .totalCost(financialSummary.getTotalCost())
                .totalSales(financialSummary.getTotalSales())
                .build();
        return financialSummaryDto;
                                                                    
     
            

    }
        

    @Override
    public IInventorySummaryDto getIentorySummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IFinancialSummaryDto getFinancialSummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
