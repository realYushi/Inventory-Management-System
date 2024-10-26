package me.yushi.inventorymanagementsystem.controller;

import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.model.*;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InventoryTransactionControllerTest {

    @Mock
    private InventoryTransactionService transactionService;

    @Mock
    private ProductService productService;

    private InventoryTransactionController transactionController;
    private Product testProduct;
    private InventoryTransaction testTransaction;
    private Date testDate;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionController = new InventoryTransactionController(transactionService, productService);

        // Create test objects
        Category testCategory = new Category("Test Category", "CAT123");
        Supplier testSupplier = new Supplier("SUP123", "Test Supplier");
        testProduct = new Product("PROD123", "Test Product", testCategory, testSupplier, 100, "pcs", 9.99);
        testDate = new Date();
        testTransaction = new InventoryTransaction("TRANS123", testProduct, 10, testDate, 
                IInventoryTransaction.TransactionType.PURCHASE, 99.90);
    }

    @Test
    public void createInventoryTransaction_Purchase() {
        // Arrange
        when(productService.getProductByID(testProduct.getProductID())).thenReturn(testProduct);
        when(transactionService.createInventoryTransaction(testTransaction)).thenReturn(testTransaction);

        // Act
        InventoryTransaction result = transactionController.createInventoryTransaction(testTransaction);

        // Assert
        assertNotNull(result);
        assertEquals(110, testProduct.getQuantity()); // Initial 100 + Purchase 10
        verify(productService, times(1)).updateProduct(testProduct);
        verify(transactionService, times(1)).createInventoryTransaction(testTransaction);
    }

    @Test
    public void createInventoryTransaction_Sale() {
        // Arrange
        testTransaction.setTransactionType(IInventoryTransaction.TransactionType.SALE);
        when(productService.getProductByID(testProduct.getProductID())).thenReturn(testProduct);
        when(transactionService.createInventoryTransaction(testTransaction)).thenReturn(testTransaction);

        // Act
        InventoryTransaction result = transactionController.createInventoryTransaction(testTransaction);

        // Assert
        assertNotNull(result);
        assertEquals(90, testProduct.getQuantity()); // Initial 100 - Sale 10
        verify(productService, times(1)).updateProduct(testProduct);
        verify(transactionService, times(1)).createInventoryTransaction(testTransaction);
    }

    @Test
    public void createInventoryTransaction_Spoilage() {
        // Arrange
        testTransaction.setTransactionType(IInventoryTransaction.TransactionType.SPOILAGE);
        when(productService.getProductByID(testProduct.getProductID())).thenReturn(testProduct);
        when(transactionService.createInventoryTransaction(testTransaction)).thenReturn(testTransaction);

        // Act
        InventoryTransaction result = transactionController.createInventoryTransaction(testTransaction);

        // Assert
        assertNotNull(result);
        assertEquals(90, testProduct.getQuantity()); // Initial 100 - Spoilage 10
        verify(productService, times(1)).updateProduct(testProduct);
        verify(transactionService, times(1)).createInventoryTransaction(testTransaction);
    }

    @Test
    public void updateInventoryTransaction_Success() {
        // Arrange
        InventoryTransaction oldTransaction = new InventoryTransaction("TRANS123", testProduct, 5, testDate,
                IInventoryTransaction.TransactionType.PURCHASE, 49.95);
        InventoryTransaction updatedTransaction = new InventoryTransaction("TRANS123", testProduct, 10, testDate,
                IInventoryTransaction.TransactionType.PURCHASE, 99.90);

        when(productService.getProductByID(testProduct.getProductID())).thenReturn(testProduct);
        when(transactionService.getInventoryTransactionByID("TRANS123")).thenReturn(oldTransaction);
        when(transactionService.updateInventoryTransaction(updatedTransaction)).thenReturn(updatedTransaction);

        // Act
        InventoryTransaction result = transactionController.updateInventoryTransaction(updatedTransaction);

        // Assert
        assertNotNull(result);
        // Initial 100 - Reverse old purchase 5 + New purchase 10 = 105
        assertEquals(105, testProduct.getQuantity());
        verify(productService, times(2)).updateProduct(testProduct);
        verify(transactionService, times(1)).updateInventoryTransaction(updatedTransaction);
    }

    @Test
    public void deleteInventoryTransaction_Purchase() {
        // Arrange
        String transactionId = "TRANS123";
        when(productService.getProductByID(testProduct.getProductID())).thenReturn(testProduct);
        when(transactionService.getInventoryTransactionByID(transactionId)).thenReturn(testTransaction);
        when(transactionService.deleteInventoryTransaction(transactionId)).thenReturn(true);

        // Act
        boolean result = transactionController.deleteInventoryTransaction(transactionId);

        // Assert
        assertTrue(result);
        assertEquals(90, testProduct.getQuantity()); // Initial 100 - Reverse purchase 10
        verify(productService, times(1)).updateProduct(testProduct);
        verify(transactionService, times(1)).deleteInventoryTransaction(transactionId);
    }

    @Test
    public void getAllInventoryTransactions_Success() {
        // Arrange
        List<InventoryTransaction> transactions = Arrays.asList(
                new InventoryTransaction("TRANS1", testProduct, 10, testDate,
                        IInventoryTransaction.TransactionType.PURCHASE, 99.90),
                new InventoryTransaction("TRANS2", testProduct, 5, testDate,
                        IInventoryTransaction.TransactionType.SALE, 59.95));
        when(transactionService.getAllInventoryTransations()).thenReturn(transactions);

        // Act
        List<InventoryTransaction> result = transactionController.getAllInventoryTransations();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionService, times(1)).getAllInventoryTransations();
    }

    @Test
    public void getAllProducts_Success() {
        // Arrange
        List<Product> products = Arrays.asList(testProduct);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = transactionController.getAllProduct();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getProduct_Success() {
        // Arrange
        String productId = "PROD123";
        when(productService.getProductByID(productId)).thenReturn(testProduct);

        // Act
        Product result = transactionController.getProduct(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductID());
        verify(productService, times(1)).getProductByID(productId);
    }
}
