/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author yushi
 */
public class SupplierTest {
    private Supplier instance;

    @Before
    public void setUp() {
        instance = new Supplier(1, "test");

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
