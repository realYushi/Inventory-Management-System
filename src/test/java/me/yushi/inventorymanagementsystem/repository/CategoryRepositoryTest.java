package me.yushi.inventorymanagementsystem.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.ICategory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class CategoryRepositoryTest {

    private CategoryRepository categoryRepository;

    @Mock
    private IFileHandler<ICategory> mockFileHandler;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        List<ICategory> initialCategories = new ArrayList<>();
        initialCategories.add(new Category(1, "Test Category 1"));
        initialCategories.add(new Category(2, "Test Category 2"));

        when(mockFileHandler.readFromFile()).thenReturn(initialCategories);

        categoryRepository = new CategoryRepository(mockFileHandler);
    }

    @Test
    public void testCreateCategory() {
        ICategory category = new Category(3, "New Category");
        ICategory createdCategory = categoryRepository.createCategory(category);
        assertEquals(category, createdCategory);
        assertTrue(categoryRepository.getAllCategorys().containsKey(3));
    }

    @Test
    public void testReadCategory() {
        ICategory readCategory = categoryRepository.readCategory(1);
        assertNotNull(readCategory);
        assertEquals("Test Category 1", readCategory.getCategoryName());
    }

    @Test
    public void testUpdateCategory() {
        ICategory updatedCategory = new Category(1, "Updated Category");
        ICategory result = categoryRepository.updateCategory(updatedCategory);
        assertEquals(updatedCategory, result);
        assertEquals("Updated Category", categoryRepository.readCategory(1).getCategoryName());
    }

    @Test
    public void testDeleteCategory() {
        boolean result = categoryRepository.deleteCategory(1);
        assertTrue(result);
        assertNull(categoryRepository.readCategory(1));
    }

    @Test
    public void testGetAllCategories() {
        Map<Integer, ICategory> allCategories = categoryRepository.getAllCategorys();
        assertEquals(2, allCategories.size());
        assertTrue(allCategories.containsKey(1));
        assertTrue(allCategories.containsKey(2));
    }

    @Test
    public void testSave() {
        categoryRepository.save();
        verify(mockFileHandler, times(1)).writeToFile(categoryRepository.getAllCategorys());
    }
}
