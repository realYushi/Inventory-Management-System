/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
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
public class InventoryTransactionRepositoryTest {
    private IInventoryTransactionRepository repo;
    @Mock
    private IFileHandler fileHandler;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<IInventoryTransaction> initalInventoryTransactions = new ArrayList<>();
        initalInventoryTransactions.add(new InventoryTransaction(1, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE));
        initalInventoryTransactions.add(new InventoryTransaction(2, 200, 10, new Date(), IInventoryTransaction.TransactionType.PURCHASE));
        when(fileHandler.readFromFile()).thenReturn(initalInventoryTransactions);
        repo=new InventoryTransactionRepository(fileHandler);
    }

    @Test
    public void testCreateInventoryTransaction() {
        IInventoryTransaction newInventoryTransaction=new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        IInventoryTransaction created=repo.createInventoryTransaction(newInventoryTransaction);
        assertThat(created).isEqualTo(newInventoryTransaction);
       
    }

    @Test
    public void testReadInventoryTransaction() {
    }

    @Test
    public void testUpdateInventoryTransaction() {
    }

    @Test
    public void testDeleteInventoryTransaction() {
    }

    @Test
    public void testGetAllInventoryTransations() {
    }

    @Test
    public void testSave() {
    }

}
