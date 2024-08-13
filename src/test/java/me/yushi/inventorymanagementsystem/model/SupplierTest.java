/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author yushi
 */
public class SupplierTest {
    private Supplier instance; 
    private SupplierDto mockDto;
    
    @Before
    public void setUp() {
        mockDto=mock(SupplierDto.class);
        when(mockDto.getSupplierID()).thenReturn(1);
        when(mockDto.getSupplierName()).thenReturn("test");

        instance=new Supplier(mockDto);
    }

    /**
     * Test of getSupplierID method, of class Supplier.
     */
     @Test
    public void testGetSupplierID() {
        assertThat(instance.getSupplierID()).isEqualTo(1);
    }

    @Test
    public void testSetSupplierID() {
        int newSupplierID = 2;
        instance.setSupplierID(newSupplierID);
        assertThat(instance.getSupplierID()).isEqualTo(newSupplierID);
    }

    @Test
    public void testGetSupplierName() {
        assertThat(instance.getSupplierName()).isEqualTo("test");
    }

    @Test
    public void testSetSupplierName() {
        String newSupplierName = "New Test Supplier";
        instance.setSupplierName(newSupplierName);
        assertThat(instance.getSupplierName()).isEqualTo(newSupplierName);
    }
    
    
}
