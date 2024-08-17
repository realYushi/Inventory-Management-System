/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author yushi
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.yushi.inventorymanagementsystem.model.ISupplier;
import me.yushi.inventorymanagementsystem.model.Supplier;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class SupplierRepositoryTest {

    private ISupplierRepository repo;
    @Mock
    private IFileHandler<ISupplier> fileHandler;
    
    
    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        List<ISupplier> initialSuppliers = new ArrayList<>();
        initialSuppliers.add(new Supplier(1, "Supplier1"));
        initialSuppliers.add(new Supplier(2, "Supplier2"));
        when(fileHandler.readFromFile()).thenReturn(initialSuppliers);
        repo = new SupplierRepository(fileHandler);
    }

    @Test
    public void testCreateSupplier() {
        ISupplier newSupplier = new Supplier(3, "Supplier3");
        ISupplier created = repo.createSupplier(newSupplier);
        assertThat(created).isEqualTo(newSupplier);
    }

    @Test
    public void testReadSupplier() {
        ISupplier newSupplier = new Supplier(3, "Supplier3");
        repo.createSupplier(newSupplier);
        ISupplier read = repo.readSupplier(3);
        assertThat(read).isEqualTo(newSupplier);
    }

    @Test
    public void testUpdateSupplier() {
        ISupplier newSupplier = new Supplier(3, "Supplier3");
        repo.createSupplier(newSupplier);
        ISupplier updatedSupplier = new Supplier(3, "UpdatedSupplier3");
        repo.updateSupplier(updatedSupplier);
        ISupplier read = repo.readSupplier(3);
        assertThat(read).isEqualTo(updatedSupplier);
    }

    @Test
    public void testDeleteSupplier() {
        ISupplier newSupplier = new Supplier(3, "Supplier3");
        repo.createSupplier(newSupplier);
        boolean deleted = repo.deleteSupplier(3);
        assertThat(deleted).isTrue();
        ISupplier read = repo.readSupplier(3);
        assertThat(read).isNull();
    }

    @Test
    public void testGetAllSuppliers() {
        ISupplier newSupplier1 = new Supplier(3, "Supplier3");
        ISupplier newSupplier2 = new Supplier(4, "Supplier4");
        repo.createSupplier(newSupplier1);
        repo.createSupplier(newSupplier2);
        List<ISupplier> allSuppliers = new ArrayList<>(repo.getAllSuppliers().values());
        assertThat(allSuppliers).hasSize(4);
    }

    @Test
    public void testSave() {
        ISupplier newSupplier = new Supplier(3, "Supplier3");
        repo.createSupplier(newSupplier);
        repo.save();
    }
    
}
