/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CategoryTest {
    private Category instance;
    private CategoryDto mockDto;

    @Before
    public void setUp() {
        mockDto = mock(CategoryDto.class);
        when(mockDto.getCategoryID()).thenReturn(1);
        when(mockDto.getCategoryName()).thenReturn("test");
        instance = new Category(mockDto);
    }

    @Test
    public void testGetCategoryID() {
        assertThat(instance.getCategoryID()).isEqualTo(1);
    }

    @Test
    public void testSetCategoryID() {
        instance.setCategoryID(2);
        assertThat(instance.getCategoryID()).isEqualTo(2);
    }

    @Test
    public void testGetCategoryName() {
        assertThat(instance.getCategoryName()).isEqualTo("test");
    }

    @Test
    public void testSetCategoryName() {
        instance.setCategoryName("new test");
        assertThat(instance.getCategoryName()).isEqualTo("new test");
    }

}