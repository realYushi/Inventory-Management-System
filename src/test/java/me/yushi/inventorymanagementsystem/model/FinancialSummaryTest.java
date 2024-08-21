// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
//  */
// package me.yushi.inventorymanagementsystem.model;

// import static org.assertj.core.api.Assertions.assertThat;
// import org.junit.Before;
// import org.junit.Test;

// /**
//  *
//  * @author yushi
//  */
// public class FinancialSummaryTest {
    
//     private FinancialSummary instance;
    
//     @Before
//     public void setUp() {
//         instance= new FinancialSummary(10.0, 6 );
//     }

//     @Test
//     public void testGetTotalSales() {
//         assertThat(instance.getTotalSales()).isEqualTo(10.0);
//     }


//     @Test
//     public void testGetTotalCost() {
//         assertThat(instance.getTotalCost()).isEqualTo(6.0);
//     }

//     @Test
//     public void testSetTotalCost() {
//         double expect= 10.0;
//         instance.setTotalCost(10.0);
//         assertThat(instance.getTotalCost()).isEqualTo(expect);
        
//     }

//    @Test
//     public void testGetNetProfit() {
//         assertThat(instance.getNetProfit()).isEqualTo(4.0);
//     }

//     @Test
//     public void testSetNetProfit() {
//         double newProfit = 5.0;
//         instance.setNetProfit(newProfit);
//         assertThat(instance.getNetProfit()).isEqualTo(newProfit);
//     }

//     @Test
//     public void testGetGrossMarginPercentage() {
//         assertThat(instance.getGrossMarginPercentage()).isEqualTo(40.0);
//     }

//     @Test
//     public void testSetGrossMarginPercentage() {
//         double newPercentage = 45.0;
//         instance.setGrossMarginPercentage(newPercentage);
//         assertThat(instance.getGrossMarginPercentage()).isEqualTo(newPercentage);
//     }
    
// }
