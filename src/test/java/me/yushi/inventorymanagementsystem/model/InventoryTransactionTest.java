/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author yushi
 */

/**
 * Test of getTransactionID method, of class InventoryTransaction.
 */
public class InventoryTransactionTest {

    private InventoryTransaction instance;

    @Before
    public void setUp() {
        instance= instance = new InventoryTransaction(1, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
    }

    @Test
    public void testGetTransactionID() {
        assertThat(instance.getTransactionID()).isEqualTo(1);
    }

    @Test
    public void testSetTransactionID() {
        instance.setTransactionID(2);
        assertThat(instance.getTransactionID()).isEqualTo(2);
    }

    @Test
    public void testGetProductID() {
        assertThat(instance.getProductID()).isEqualTo(100);
    }

    @Test
    public void testSetProductID() {
        instance.setProductID(200);
        assertThat(instance.getProductID()).isEqualTo(200);
    }

    @Test
    public void testGetQuantity() {
        assertThat(instance.getQuantity()).isEqualTo(5);
    }

    @Test
    public void testSetQuantity() {
        instance.setQuantity(10);
        assertThat(instance.getQuantity()).isEqualTo(10);
    }

    @Test
    public void testGetDate() {
        assertThat(instance.getDate()).isNotNull();
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();
        instance.setDate(newDate);
        assertThat(instance.getDate()).isEqualTo(newDate);
    }

    @Test
    public void testGetTransactionType() {
        assertThat(instance.getTransactionType()).isEqualTo(IInventoryTransaction.TransactionType.PURCHASE);
    }

    @Test
    public void testSetTransactionType() {
        instance.setTransactionType(IInventoryTransaction.TransactionType.SALE);
        assertThat(instance.getTransactionType()).isEqualTo(IInventoryTransaction.TransactionType.SALE);
    }


}
