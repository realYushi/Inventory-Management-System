package me.yushi.inventorymanagementsystem.view;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.Dto.SupplierDto;
import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;

import java.awt.CardLayout;
import java.util.List;
public class BodyPanel extends JPanel{
    private CardLayout cardLayout;
    private ProductController productController;
    private CategoryController categoryController;
    private SupplierController supplierController;
    private InventoryTransactionController transactionController;
    public BodyPanel(ProductController productController, CategoryController categoryController, SupplierController supplierController,InventoryTransactionController transactionController){
        this.productController = productController;
        this.categoryController = categoryController;
        this.supplierController = supplierController;
        this.transactionController = transactionController;
        
        List<ProductDto> products = productController.getAllProducts();
        List<CategoryDto> categories = categoryController.getAllCategorys();
        List<SupplierDto> suppliers = supplierController.getAllSuppliers();
        List<InventoryTransactionDto> transactions = transactionController.getAllInventoryTransations();


        this.setBorder(BorderFactory.createTitledBorder("Main Content"));
        this.setLayout(cardLayout = new CardLayout());

        this.add(new DashboardPanel(),"dashboard");
        this.add(new ProductsPanel(categories,suppliers),"products");
        this.add(new CategoriesPanel(categories),"categories");
        this.add(new SuppliersPanel(suppliers),"suppliers");
        this.add(new TransactionsPanel(transactions,products),"transactions");


        cardLayout.show(this,"dashboard");


    }
    public void showPanel(String panelName){
        cardLayout.show(this,panelName);
    }


}
