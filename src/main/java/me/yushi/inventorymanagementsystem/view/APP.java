package me.yushi.inventorymanagementsystem.view;
import javax.swing.*;

import com.formdev.flatlaf.FlatDarculaLaf;

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import net.miginfocom.swing.MigLayout;

public class APP extends JFrame{
    private ProductController productController;
    private CategoryController categoryController;
    private SupplierController supplierController;
    private InventoryTransactionController transactionController;
    public APP(ProductController productController, CategoryController categoryController, SupplierController supplierController,InventoryTransactionController transactionController){
        this.productController = productController;
        this.categoryController = categoryController;
        this.supplierController = supplierController;
        this.transactionController = transactionController;
        FlatDarculaLaf.setup();
        this.setTitle("Inventory Management System");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // main layout
        JPanel mainPanel = new JPanel(new MigLayout("fill,debug","[200px!][grow]","[]"));
        BodyPanel bodyPanel = new BodyPanel(productController, categoryController, supplierController, transactionController);
        NavPanel navPanel = new NavPanel(bodyPanel);
        mainPanel.add(navPanel,"cell 0 0, growy");
        mainPanel.add(bodyPanel,"cell 1 0, grow");
        this.add(mainPanel);
        


        this.setVisible(true);
    }
    

}
