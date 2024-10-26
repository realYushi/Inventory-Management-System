package me.yushi.inventorymanagementsystem.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.formdev.flatlaf.FlatLightLaf;
import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import net.miginfocom.swing.MigLayout;

public class APP extends JFrame {
    public APP(ProductController productController, CategoryController categoryController,
            SupplierController supplierController, InventoryTransactionController transactionController,
            DashBoardController dashBoardController) {
        // Set the look and feel
        FlatLightLaf.setup();
        this.setTitle("Inventory Management System");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // main layout
        JPanel mainPanel = new JPanel(new MigLayout("fill", "[200px!][grow]", "[]"));
        // pass the controllers to the panels
        BodyPanel bodyPanel = new BodyPanel(productController, categoryController, supplierController,
                transactionController, dashBoardController);
        NavPanel navPanel = new NavPanel(bodyPanel);
        // add the panels to the main panel
        mainPanel.add(navPanel, "cell 0 0, growy");
        mainPanel.add(bodyPanel, "cell 1 0, grow");
        // add the main panel to the frame
        this.add(mainPanel);
        this.setVisible(true);
    }

}
