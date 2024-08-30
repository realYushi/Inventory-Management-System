package me.yushi.inventorymanagementsystem.InventoryManagementSystem;

import java.io.IOException;
import me.yushi.inventorymanagementsystem.view.APP;

public class Main {

    public static void main(String[] args) throws IOException {
        // File locations
        String productFile = "product.json";
        String categoryFile = "category.json";
        String transationFile = "transation.json";
        String supplierFile = "supplier.json";

        try {
            // Start the application, pass the file locations
            APP app = new APP(categoryFile, transationFile, productFile, supplierFile);
            app.start();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

}
