/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author yushi
 * @param <T>
 */
public class FileHandler<T> implements IFileHandler<T> {

    private final Gson gson = new Gson();
    private final Class<T> targetClass;
    private String fileLocation;

    public FileHandler(Class<T> targetClass,String fileLocation) throws IOException {
        this.targetClass = targetClass;
        this.fileLocation=fileLocation;
        checkFile();
    }

    private void checkFile() throws IOException {
        File file = new File(this.fileLocation);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public List<T> readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileLocation))) {
            Type listType = TypeToken.getParameterized(List.class, targetClass).getType();
            List<T> loadedList = gson.fromJson(reader, listType);
            return loadedList != null ? loadedList : new ArrayList<>();
        } catch (Exception ex) {
            return new ArrayList<>();
        }

    }

    @Override
    public void writeToFile(List<T> objectList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileLocation))){
            writer.write(gson.toJson(objectList));
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
