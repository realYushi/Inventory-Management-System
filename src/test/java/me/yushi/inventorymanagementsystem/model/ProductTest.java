/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author yushi
 */
public class ProductTest {
    private Product instance;
    private IProductDto mockDto;

    @Before
    public void setUp() {
        mockDto = mock(IProductDto.class);
        when(mockDto.getProductID()).thenReturn(1);
        when(mockDto.getName()).thenReturn("Test Product");
        when(mockDto.getCategoryID()).thenReturn(2);
        when(mockDto.getQuantity()).thenReturn(10);
        when(mockDto.getUnit()).thenReturn("pcs");
        when(mockDto.getPrice()).thenReturn(19.99);
        when(mockDto.getExpirationDate()).thenReturn(new Date(1640995200000L)); // 2022-01-01

        instance = new Product(mockDto);
    }

    @Test
    public void testGetProductID() {
        assertThat(instance.getProductID()).isEqualTo(1);
    }

    @Test
    public void testSetProductID() {
        instance.setProductID(2);
        assertThat(instance.getProductID()).isEqualTo(2);
    }

    @Test
    public void testGetName() {
        assertThat(instance.getName()).isEqualTo("Test Product");
    }

    @Test
    public void testSetName() {
        instance.setName("New Product Name");
        assertThat(instance.getName()).isEqualTo("New Product Name");
    }

    @Test
    public void testGetCategoryID() {
        assertThat(instance.getCategoryID()).isEqualTo(2);
    }

    @Test
    public void testSetCategoryID() {
        instance.setCategoryID(3);
        assertThat(instance.getCategoryID()).isEqualTo(3);
    }

    @Test
    public void testGetQuantity() {
        assertThat(instance.getQuantity()).isEqualTo(10);
    }

    @Test
    public void testSetQuantity() {
        instance.setQuantity(20);
        assertThat(instance.getQuantity()).isEqualTo(20);
    }

    @Test
    public void testGetPrice() {
        assertThat(instance.getPrice()).isEqualTo(19.99);
    }

    @Test
    public void testSetPrice() {
        instance.setPrice(29.99);
        assertThat(instance.getPrice()).isEqualTo(29.99);
    }

    @Test
    public void testGetExpirationDate() {
        assertThat(instance.getExpirationDate()).isEqualTo(new Date(1640995200000L));
    }

    @Test
    public void testSetExpirationDate() {
        Date newDate = new Date(1672531200000L); // 2023-01-01
        instance.setExpirationDate(newDate);
        assertThat(instance.getExpirationDate()).isEqualTo(newDate);
    }

    @Test
    public void testGetUnit() {
        assertThat(instance.getUnit()).isEqualTo("pcs");
    }

    @Test
    public void testSetUnit() {
        instance.setUnit("kg");
        assertThat(instance.getUnit()).isEqualTo("kg");
    }

    
}
