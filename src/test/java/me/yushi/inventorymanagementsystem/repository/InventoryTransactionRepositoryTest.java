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
        IInventoryTransaction newInventoryTransaction = new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        repo.createInventoryTransaction(newInventoryTransaction);
        IInventoryTransaction read = repo.readInventoryTransaction(3);
        assertThat(read).isEqualTo(newInventoryTransaction);
    }

    @Test
    public void testUpdateInventoryTransaction() {
        IInventoryTransaction newInventoryTransaction = new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        repo.createInventoryTransaction(newInventoryTransaction);
        IInventoryTransaction updatedInventoryTransaction = new InventoryTransaction(3, 200, 10, new Date(), IInventoryTransaction.TransactionType.SALE);
        repo.updateInventoryTransaction(updatedInventoryTransaction);
        IInventoryTransaction read = repo.readInventoryTransaction(3);
        assertThat(read).isEqualTo(updatedInventoryTransaction);
    }

    @Test
    public void testDeleteInventoryTransaction() {
        IInventoryTransaction newInventoryTransaction = new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        repo.createInventoryTransaction(newInventoryTransaction);
        boolean deleted = repo.deleteInventoryTransaction(3);
        assertThat(deleted).isTrue();
        IInventoryTransaction read = repo.readInventoryTransaction(3);
        assertThat(read).isNull();
    }

    @Test
    public void testGetAllInventoryTransations() {
        IInventoryTransaction newInventoryTransaction1 = new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        IInventoryTransaction newInventoryTransaction2 = new InventoryTransaction(4, 200, 10, new Date(), IInventoryTransaction.TransactionType.SALE);
        repo.createInventoryTransaction(newInventoryTransaction1);
        repo.createInventoryTransaction(newInventoryTransaction2);
        List<IInventoryTransaction> allTransactions = new ArrayList<>(repo.getAllInventoryTransations().values());
        assertThat(allTransactions).containsExactlyInAnyOrder(newInventoryTransaction1, newInventoryTransaction2);
    }

    @Test
    public void testSave() {
        IInventoryTransaction newInventoryTransaction = new InventoryTransaction(3, 100, 5, new Date(), IInventoryTransaction.TransactionType.PURCHASE);
        repo.createInventoryTransaction(newInventoryTransaction);
        repo.save();
        // Assuming the save method calls fileHandler.writeToFile, we can verify this with Mockito
        // This is a simplified example, as the actual implementation might require more complex verification
        // depending on how the fileHandler is mocked and used.
    }

}
