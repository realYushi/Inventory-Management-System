/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.File;
import java.io.IOException;
import me.yushi.inventorymanagementsystem.model.ICategory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author yushi
 */
public class CategoryRepositoryTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private FileHandler<ICategory> fileHandler;
    private String filePath;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File tempFile = tempFolder.newFile("test.json");
        filePath = tempFile.getAbsolutePath();
        fileHandler = new FileHandler<>(ICategory.class, filePath);
    }
    
    

    @Test
    public void testCreateCategory() {
    }

    @Test
    public void testReadCategory() {
    }

    @Test
    public void testUpdateCategory() {
    }

    @Test
    public void testDeleteCategory() {
    }

    @Test
    public void testGetAllCategorys() {
    }

    @Test
    public void testSave() {
    }
    
}
