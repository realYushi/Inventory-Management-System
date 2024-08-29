/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yushi
 * @param <T>
 */
public class FileHandler<T> implements IFileHandler<T> {

    private final Gson gson;
    private final Class<T> targetClass;
    private final String fileLocation;

    public FileHandler(Class<T> targetClass, String fileLocation) throws IOException {
        // Initialize Gson, with pretty printing
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.targetClass = targetClass;
        this.fileLocation = fileLocation;
        // Check if file exists, if not create it
        checkFile();
    }

    // Check if file exists, if not create it
    private void checkFile() throws IOException {
        File file = new File(this.fileLocation);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    // Read from file, return a list of objects
    public List<T> readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileLocation))) {
            // Create a list type for Gson to parse
            Type listType = TypeToken.getParameterized(List.class, targetClass).getType();
            // Parse the file into a list of objects
            List<T> loadedList = gson.fromJson(reader, listType);
            if (loadedList == null) {
                loadedList = new ArrayList<>();
            }
            return loadedList;
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        return new ArrayList<>();
    }

    @Override
    // Write to file, takes a map of objects
    public void writeToFile(Map<String, T> objectList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileLocation))) {
            writer.write(gson.toJson(objectList.values()));
        } catch (IOException ex) {
            System.out.println("File not found");
        }

    }

}
