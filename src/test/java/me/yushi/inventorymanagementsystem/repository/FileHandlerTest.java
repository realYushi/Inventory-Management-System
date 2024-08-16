/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author yushi
 */
public class FileHandlerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private FileHandler<TestObject> fileHandler;
    private String filePath;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        File tempFile = tempFolder.newFile("test.json");
        filePath = tempFile.getAbsolutePath();
        fileHandler = new FileHandler<>(TestObject.class, filePath);
    }

    @Test
    public void testReadFromFile() {
        // Prepare test data
        TestObject obj1 = new TestObject("Test1", 1);
        TestObject obj2 = new TestObject("Test2", 2);
        List<TestObject> expectedList = Arrays.asList(obj1, obj2);
        Map<Integer, TestObject> expectedMap = expectedList.stream()
        .collect(Collectors.toMap(
            testObject -> testObject.value, // Use 'value' as the key
            testObject -> testObject // Use the TestObject itself as the value
        ));

        // Write test data to file
        fileHandler.writeToFile(expectedMap);

        // Read from file
        List<TestObject> result = fileHandler.readFromFile();

        // Assert
        assertThat(result).hasSize(2)
                .extracting("name", "value")
                .containsExactly(
                        tuple("Test1", 1),
                        tuple("Test2", 2)
                );
    }

    @Test
    public void testWriteToFile() throws IOException {
        // Prepare test data
        TestObject obj1 = new TestObject("Test1", 1);
        TestObject obj2 = new TestObject("Test2", 2);
        List<TestObject> testList = Arrays.asList(obj1, obj2);
        Map<Integer, TestObject> expectedMap = testList.stream()
        .collect(Collectors.toMap(
            testObject -> testObject.value, // Use 'value' as the key
            testObject -> testObject // Use the TestObject itself as the value
        ));

        // Write to file
        fileHandler.writeToFile(expectedMap);

        // Read from file to verify
        List<TestObject> result = fileHandler.readFromFile();

        // Assert
        assertThat(result).hasSize(2)
                .extracting("name", "value")
                .containsExactly(
                        tuple("Test1", 1),
                        tuple("Test2", 2)
                );
    }

    private static class TestObject {

        private final String name;
        private final int value;

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }

    }

}
