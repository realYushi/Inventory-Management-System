/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;

/**
 *
 * @author yushi
 */
public class InventorySummaryTest {
    
    
    private InventorySummary inventorySummary;

    @Mock
    private IProduct product1, product2;
    @Mock
    private ISupplier supplier1, supplier2;
    @Mock
    private ICategory category1, category2;
    @Mock
    private IInventoryTransaction transaction1, transaction2;

    @Before
        public void setUp() {
        product1=new Product(1, "Test Product1", 1, 10, "pcs", 19.99, new Date(1640995200000L));
        product2=new Product(2, "Test Product2", 2, 20, "L", 29.99, new Date(1640995200000L));
        
        supplier1 = new Supplier(1, "test1");
        supplier2 = new Supplier(2, "test2");
        
        category1 = new Category(1, "Category 1");
        category2 = new Category(2, "Category 2");
        
        transaction1= new InventoryTransaction(1, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        transaction2= new InventoryTransaction(2, 200, 10, new Date(), IInventoryTransaction.TransactionType.PURCHASE);

        List<IProduct> products = Arrays.asList(product1, product2);
        List<ISupplier> suppliers = Arrays.asList(supplier1, supplier2);
        List<ICategory> categories = Arrays.asList(category1, category2);
        List<IInventoryTransaction> transactions = Arrays.asList(transaction1, transaction2);

        inventorySummary = new InventorySummary(products, suppliers, categories, transactions);
    }


    @Test
    public void testGetRecentProducts() {
        List<IProduct> result = inventorySummary.getRecentProducts();
        assertThat(result).containsExactly(product1, product2);
    }

    @Test
    public void testSetRecentProducts() {
        List<IProduct> newProducts = Arrays.asList(mock(IProduct.class), mock(IProduct.class));
        inventorySummary.setRecentProducts(newProducts);
        assertThat(inventorySummary.getRecentProducts()).isEqualTo(newProducts);
    }

    @Test
    public void testGetRecentCategories() {
        List<ICategory> result = inventorySummary.getRecentCategories();
        assertThat(result).containsExactly(category1, category2);
    }

    @Test
    public void testSetRecentCategories() {
        List<ICategory> newCategories = Arrays.asList(mock(ICategory.class), mock(ICategory.class));
        inventorySummary.setRecentCategories(newCategories);
        assertThat(inventorySummary.getRecentCategories()).isEqualTo(newCategories);
    }

    @Test
    public void testGetRecentSuppliers() {
        List<ISupplier> result = inventorySummary.getRecentSuppliers();
        assertThat(result).containsExactly(supplier1, supplier2);
    }

    @Test
    public void testSetRecentSuppliers() {
        List<ISupplier> newSuppliers = Arrays.asList(mock(ISupplier.class), mock(ISupplier.class));
        inventorySummary.setRecentSuppliers(newSuppliers);
        assertThat(inventorySummary.getRecentSuppliers()).isEqualTo(newSuppliers);
    }

    @Test
    public void testGetRecentInventoryTransactions() {
        List<IInventoryTransaction> result = inventorySummary.getRecentInventoryTransactions();
        assertThat(result).containsExactly(transaction1, transaction2);
    }


    @Test
    public void testSetRecentInventoryTransactions() {
        List<IInventoryTransaction> newTransactions = Arrays.asList(mock(IInventoryTransaction.class), mock(IInventoryTransaction.class));
        inventorySummary.setRecentInventoryTransactions(newTransactions);
        assertThat(inventorySummary.getRecentInventoryTransactions()).isEqualTo(newTransactions); 
    } 
    
}
