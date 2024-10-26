package me.yushi.inventorymanagementsystem.controller;

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    private CategoryController categoryController;
    private Category testCategory;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryController = new CategoryController(categoryService);
        testCategory = new Category("Test Category", "CAT123");
    }

    @Test
    public void createCategory_Success() {
        // Arrange
        when(categoryService.createCategory(testCategory)).thenReturn(testCategory);

        // Act
        Category result = categoryController.createCategory(testCategory);

        // Assert
        assertNotNull(result);
        assertEquals("Test Category", result.getCategoryName());
        verify(categoryService, times(1)).createCategory(testCategory);
    }

    @Test
    public void createCategory_NullCategory() {
        // Act
        Category result = categoryController.createCategory(null);

        // Assert
        assertNull(result);
        verify(categoryService, never()).createCategory(any());
    }

    @Test
    public void updateCategory_Success() {
        // Arrange
        when(categoryService.updateCategory(testCategory)).thenReturn(testCategory);

        // Act
        Category result = categoryController.updateCategory(testCategory);

        // Assert
        assertNotNull(result);
        assertEquals("Test Category", result.getCategoryName());
        verify(categoryService, times(1)).updateCategory(testCategory);
    }

    @Test
    public void updateCategory_NullCategory() {
        // Act
        Category result = categoryController.updateCategory(null);

        // Assert
        assertNull(result);
        verify(categoryService, never()).updateCategory(any());
    }

    @Test
    public void getCategoryByID_Success() {
        // Arrange
        String categoryId = "CAT123";
        when(categoryService.getCategoryByID(categoryId)).thenReturn(testCategory);

        // Act
        Category result = categoryController.getCategoryByID(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getCategoryID());
        verify(categoryService, times(1)).getCategoryByID(categoryId);
    }

    @Test
    public void getCategoryByID_NullId() {
        // Act
        Category result = categoryController.getCategoryByID(null);

        // Assert
        assertNull(result);
        verify(categoryService, never()).getCategoryByID(any());
    }

    @Test
    public void deleteCategory_Success() {
        // Arrange
        String categoryId = "CAT123";
        when(categoryService.deleteCategory(categoryId)).thenReturn(true);

        // Act
        boolean result = categoryController.deleteCategory(categoryId);

        // Assert
        assertTrue(result);
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void deleteCategory_NullId() {
        // Act
        boolean result = categoryController.deleteCategory(null);

        // Assert
        assertFalse(result);
        verify(categoryService, never()).deleteCategory(any());
    }

    @Test
    public void getAllCategories_Success() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category("Category 1", "CAT1"),
                new Category("Category 2", "CAT2"));
        when(categoryService.getAllCategorys()).thenReturn(categories);

        // Act
        List<Category> result = categoryController.getAllCategorys();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryService, times(1)).getAllCategorys();
    }

    @Test
    public void haveLinkedProduct_Success() {
        // Arrange
        String categoryId = "CAT123";
        when(categoryService.haveLinkedProduct(categoryId)).thenReturn(true);

        // Act
        boolean result = categoryController.haveLinkedProduct(categoryId);

        // Assert
        assertTrue(result);
        verify(categoryService, times(1)).haveLinkedProduct(categoryId);
    }

    @Test
    public void haveLinkedProduct_NullId() {
        // Arrange
        when(categoryService.haveLinkedProduct(null)).thenReturn(false);

        // Act
        boolean result = categoryController.haveLinkedProduct(null);

        // Assert
        assertFalse(result);
        verify(categoryService, times(1)).haveLinkedProduct(null);
    }
}
