package me.yushi.inventorymanagementsystem.view;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.DashBoardController;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import java.awt.CardLayout;
public class BodyPanel extends JPanel{
    private CardLayout cardLayout;

    public BodyPanel(ProductController productController, CategoryController categoryController, SupplierController supplierController,InventoryTransactionController transactionController,DashBoardController dashBoardController){
        

        this.setBorder(BorderFactory.createTitledBorder("Main Content"));
        this.setLayout(cardLayout = new CardLayout());
        this.add(new DashboardPanel(dashBoardController),"dashboard");
        this.add(new ProductsPanel(productController, categoryController, supplierController),"products");
        this.add(new CategoriesPanel(categoryController),"categories");
        this.add(new SuppliersPanel(supplierController),"suppliers");
        this.add(new TransactionsPanel(transactionController, productController),"transactions");
        cardLayout.show(this,"dashboard");
    }
    public void showPanel(String panelName){
        cardLayout.show(this,panelName);
    }


}
