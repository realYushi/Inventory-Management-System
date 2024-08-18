package me.yushi.inventorymanagementsystem.InventoryManagementSystem;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.yushi.inventorymanagementsystem.view.APP;

public class Main {

    public static void main(String[] args) {
        String productFile = "product.json";
        String categoryFile = "category.json";
        String transationFile = "transation.json";
        String supplierFile = "supplier.json";

        try {
            APP app = new APP(categoryFile, transationFile, productFile, supplierFile);
            app.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
