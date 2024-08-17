/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.yushi.inventorymanagementsystem.model.IProduct;
import me.yushi.inventorymanagementsystem.model.Product;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author yushi
 */
public class ProductRepositoryTest {
    
    public ProductRepositoryTest() {
    }
    
    @Mock
    private IFileHandler<IProduct> fileHandler;
    private IProductRepository repo;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<IProduct> initialProducts = new ArrayList<>();
        initialProducts.add(new Product(1, "Product1", 1, 10, "Unit1", 10.0, new Date()));
        initialProducts.add(new Product(2, "Product2", 2, 20, "Unit2", 20.0, new Date()));
        when(fileHandler.readFromFile()).thenReturn(initialProducts);
        repo = new ProductRepository(fileHandler);
    }

    @Test
    public void testCreateProduct() {
        IProduct newProduct = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        IProduct created = repo.createProduct(newProduct);
        assertThat(created).isEqualTo(newProduct);
    }

    @Test
    public void testReadProduct() {
        IProduct newProduct = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        repo.createProduct(newProduct);
        IProduct read = repo.readProduct(3);
        assertThat(read).isEqualTo(newProduct);
    }

    @Test
    public void testUpdateProduct() {
        IProduct newProduct = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        repo.createProduct(newProduct);
        IProduct updatedProduct = new Product(3, "UpdatedProduct3", 3, 35, "Unit3", 35.0, new Date());
        repo.updateProduct(updatedProduct);
        IProduct read = repo.readProduct(3);
        assertThat(read).isEqualTo(updatedProduct);
    }

    @Test
    public void testDeleteProduct() {
        IProduct newProduct = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        repo.createProduct(newProduct);
        boolean deleted = repo.deleteProduct(3);
        assertThat(deleted).isTrue();
        IProduct read = repo.readProduct(3);
        assertThat(read).isNull();
    }

    @Test
    public void testGetAllProducts() {
        IProduct newProduct1 = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        IProduct newProduct2 = new Product(4, "Product4", 4, 40, "Unit4", 40.0, new Date());
        repo.createProduct(newProduct1);
        repo.createProduct(newProduct2);
        List<IProduct> allProducts = new ArrayList<>(repo.getAllProducts().values());
        assertThat(allProducts).hasSize(4);
    }

    @Test
    public void testSave() {
        IProduct newProduct = new Product(3, "Product3", 3, 30, "Unit3", 30.0, new Date());
        repo.createProduct(newProduct);
        repo.save();
    }
    
}
