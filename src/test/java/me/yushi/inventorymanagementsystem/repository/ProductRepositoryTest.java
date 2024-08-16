/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import me.yushi.inventorymanagementsystem.model.IProduct;
import me.yushi.inventorymanagementsystem.model.Product;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class ProductRepositoryTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String filePath;

    @Mock
    private IFileHandler<IProduct> mockFileHandler;

    private ProductRepository productRepository;
    private Product testProduct;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        testProduct = new Product(1, "Test Product", 1, 10, "pcs", 9.99, new Date());
        File tempFile = tempFolder.newFile("test.json");
        filePath = tempFile.getAbsolutePath();
        
        // Create the mock FileHandler
        mockFileHandler = mock(IFileHandler.class);
        
        // Create initial data
        Map<Integer, IProduct> initialData = new HashMap<>();
        initialData.put(testProduct.getProductID(), testProduct);
        
        // Set up the mock behavior
        when(mockFileHandler.readFromFile()).thenReturn(new ArrayList<>(initialData.values()));
        
        // Create ProductRepository with the mock FileHandler
        productRepository = new ProductRepository(mockFileHandler);
    }

    @Test
    public void testCreateProduct() {
        Product newProduct = new Product(2, "New Product", 1, 5, "kg", 19.99, new Date());
        IProduct createdProduct = productRepository.createProduct(newProduct);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getProductID()).isEqualTo(2);
        assertThat(createdProduct).isEqualTo(newProduct);
        
        verify(mockFileHandler, times(1)).writeToFile(anyMap());
    }

    @Test
    public void testReadProduct() {
        IProduct readProduct = productRepository.readProduct(1);

        assertThat(readProduct).isNotNull();
        assertThat(readProduct).isEqualTo(testProduct);
    }

    @Test
    public void testUpdateProduct() {
        Product updatedProduct = new Product(1, "Updated Product", 1, 10, "pcs", 9.99, new Date());
        IProduct result = productRepository.updateProduct(updatedProduct);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(updatedProduct);
        assertThat(productRepository.readProduct(1)).isEqualTo(updatedProduct);
        
        verify(mockFileHandler, times(1)).writeToFile(anyMap());
    }

    @Test
    public void testDeleteProduct() {
        boolean deleted = productRepository.deleteProduct(1);

        assertThat(deleted).isTrue();
        assertThat(productRepository.readProduct(1)).isNull();
        
        verify(mockFileHandler, times(1)).writeToFile(anyMap());
    }

    @Test
    public void testGetAllProducts() {
        Map<Integer, IProduct> allProducts = productRepository.getAllProducts();

        assertThat(allProducts).isNotNull();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts.get(1)).isEqualTo(testProduct);
        
        verify(mockFileHandler, times(1)).readFromFile();
    }

    @Test
    public void testSave() throws IOException {
        productRepository.save();

        verify(mockFileHandler, times(1)).writeToFile(anyMap());
    }
}
