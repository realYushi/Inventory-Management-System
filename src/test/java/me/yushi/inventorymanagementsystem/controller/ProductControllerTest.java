package me.yushi.inventorymanagementsystem.controller;

import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;
    private Product testProduct;
    private Category testCategory;
    private Supplier testSupplier;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);

        // Create test objects
        testCategory = new Category("Test Category", "CAT123");
        testSupplier = new Supplier("SUP123", "Test Supplier");
        testProduct = new Product("PROD123", "Test Product", testCategory, testSupplier, 100, "pcs", 9.99);
    }

    @Test
    public void createProduct_Success() {
        // Arrange
        when(productService.createProduct(testProduct)).thenReturn(testProduct);

        // Act
        Product result = productController.createProduct(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(100, result.getQuantity());
        assertEquals("pcs", result.getUnit());
        assertEquals(9.99, result.getPrice(), 0.001);
        verify(productService, times(1)).createProduct(testProduct);
    }

    @Test
    public void createProduct_NullProduct() {
        // Act
        Product result = productController.createProduct(null);

        // Assert
        assertNull(result);
        verify(productService, never()).createProduct(any());
    }

    @Test
    public void updateProduct_Success() {
        // Arrange
        when(productService.updateProduct(testProduct)).thenReturn(testProduct);

        // Act
        Product result = productController.updateProduct(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productService, times(1)).updateProduct(testProduct);
    }

    @Test
    public void updateProduct_NullProduct() {
        // Act
        Product result = productController.updateProduct(null);

        // Assert
        assertNull(result);
        verify(productService, never()).updateProduct(any());
    }

    @Test
    public void getProductByID_Success() {
        // Arrange
        String productId = "PROD123";
        when(productService.getProductByID(productId)).thenReturn(testProduct);

        // Act
        Product result = productController.getProductByID(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getProductID());
        verify(productService, times(1)).getProductByID(productId);
    }

    @Test
    public void getProductByID_NullId() {
        // Act
        Product result = productController.getProductByID(null);

        // Assert
        assertNull(result);
        verify(productService, never()).getProductByID(any());
    }

    @Test
    public void getProductByID_EmptyId() {
        // Act
        Product result = productController.getProductByID("");

        // Assert
        assertNull(result);
        verify(productService, never()).getProductByID(any());
    }

    @Test
    public void deleteProduct_Success() {
        // Arrange
        String productId = "PROD123";
        when(productService.deleteProduct(productId)).thenReturn(true);

        // Act
        boolean result = productController.deleteProduct(productId);

        // Assert
        assertTrue(result);
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void deleteProduct_NullId() {
        // Act
        boolean result = productController.deleteProduct(null);

        // Assert
        assertFalse(result);
        verify(productService, never()).deleteProduct(any());
    }

    @Test
    public void getAllProducts_Success() {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product("PROD1", "Product 1", testCategory, testSupplier, 100, "pcs", 9.99),
                new Product("PROD2", "Product 2", testCategory, testSupplier, 200, "boxes", 19.99));
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = productController.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getCategory_Success() {
        // Act
        Category result = productController.getCategory(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals(testCategory, result);
        assertEquals("Test Category", result.getCategoryName());
    }

    @Test
    public void getCategory_NullProduct() {
        // Act
        Category result = productController.getCategory(null);

        // Assert
        assertNull(result);
    }

    @Test
    public void getSupplier_Success() {
        // Act
        Supplier result = productController.getSupplier(testProduct);

        // Assert
        assertNotNull(result);
        assertEquals(testSupplier, result);
        assertEquals("Test Supplier", result.getSupplierName());
    }

    @Test
    public void getSupplier_NullProduct() {
        // Act
        Supplier result = productController.getSupplier(null);

        // Assert
        assertNull(result);
    }
}
