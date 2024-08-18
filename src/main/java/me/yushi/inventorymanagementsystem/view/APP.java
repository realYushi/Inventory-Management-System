/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.IInventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.FileHandler;
import me.yushi.inventorymanagementsystem.repository.ICategoryRepository;
import me.yushi.inventorymanagementsystem.repository.IInventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.IProductRepository;
import me.yushi.inventorymanagementsystem.repository.ISupplierRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class APP {
    private ICategoryRepository categoryRepository;
    private IProductRepository productRepository;
    private IInventoryTransactionRepository transactionRepository;
    private ISupplierRepository supplierRepository;
    public APP(String categoryFile, String transationFile, String productFile, String supplierFile) throws IOException {
        categoryRepository=new CategoryRepository(new FileHandler(Category.class,categoryFile));
        productRepository=new ProductRepository(new FileHandler(Product.class,productFile));
        transactionRepository=new IInventoryTransactionRepository(new FileHandler(IInventoryTransaction.class,transationFile));
        supplierRepository=new SupplierRepository(new FileHandler(Supplier.class,supplierFile));

    }

    public void start() {
        Terminal terminal = null;
        Screen screen = null;
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Inventory Management System");

            Panel mainPanel = new Panel(new GridLayout(1));
            // Create body panel
            Panel bodyPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            // Create top navigation panel
            Panel topNavPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            topNavPanel.addComponent(new Button("Dashboard", () -> showDashboard(bodyPanel)));
            topNavPanel.addComponent(new Button("Product", () -> showProduct(bodyPanel)));
            topNavPanel.addComponent(new Button("Supplier", () -> showSupplier(bodyPanel)));
            topNavPanel.addComponent(new Button("Transaction", () -> showTransaction(bodyPanel)));
            topNavPanel.addComponent(new Button("Category", () -> showCategory(bodyPanel)));

            // Create bottom panel for help
            Panel bottomPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            bottomPanel.addComponent(new Label("Help: Press F1 for more info"));

            // Add sub-panels to main panel
            mainPanel.addComponent(topNavPanel);
            mainPanel.addComponent(bodyPanel);
            mainPanel.addComponent(bottomPanel);

            // Set main panel as the content of the window
            window.setComponent(mainPanel);

            // Add window to the GUI and run
            textGUI.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void showDashboard(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Dashboard Content"));
    }

    private static void showProduct(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Product Content"));
    }

    private static void showSupplier(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Supplier Content"));
    }

    private static void showTransaction(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Transaction Content"));
    }

    private static void showCategory(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Category Content"));
    }
}
