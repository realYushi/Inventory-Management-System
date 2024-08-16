/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import me.yushi.inventorymanagementsystem.model.ICategory;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author yushi
 */
public class CategoryRepositoryTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FileHandler<ICategory> fileHandler;

    private String filePath;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File tempFile = tempFolder.newFile("test.json");
        filePath = tempFile.getAbsolutePath();
        fileHandler = new FileHandler<>(ICategory.class, filePath);
        categoryRepository = new CategoryRepository(fileHandler);
    }
    
    

    @Test
    public void testCreateCategory() {
        ICategory category = new Category(1, "Test Category");
        when(categoryRepository.create(category)).thenReturn(category);
        ICategory createdCategory = categoryRepository.create(category);
        assertEquals(category, createdCategory);
    }

    @Test
    public void testReadCategory() {
        ICategory category = new Category(1, "Test Category");
        when(categoryRepository.read(1)).thenReturn(category);
        ICategory readCategory = categoryRepository.read(1);
        assertEquals(category, readCategory);
    }

    @Test
    public void testUpdateCategory() {
        ICategory category = new Category(1, "Test Category");
        when(categoryRepository.update(category)).thenReturn(category);
        ICategory updatedCategory = categoryRepository.update(category);
        assertEquals(category, updatedCategory);
    }

    @Test
    public void testDeleteCategory() {
        doNothing().when(categoryRepository).delete(1);
        categoryRepository.delete(1);
        verify(categoryRepository, times(1)).delete(1);
    }

    @Test
    public void testGetAllCategorys() {
        List<ICategory> categories = List.of(new Category(1, "Test Category 1"), new Category(2, "Test Category 2"));
        when(categoryRepository.getAll()).thenReturn(categories);
        List<ICategory> allCategories = categoryRepository.getAll();
        assertEquals(categories, allCategories);
    }

    @Test
    public void testSave() {
        ICategory category = new Category(1, "Test Category");
        doNothing().when(categoryRepository).save(category);
        categoryRepository.save(category);
        verify(categoryRepository, times(1)).save(category);
    }
    
}
