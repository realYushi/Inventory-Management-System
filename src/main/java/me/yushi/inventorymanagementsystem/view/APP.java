/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.FileHandler;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;

/**
 *
 * @author yushi
 */
public class APP {

    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private InventoryTransactionRepository transactionRepository;
    private SupplierRepository supplierRepository;

    public APP(String categoryFile, String transationFile, String productFile, String supplierFile) throws IOException {
        FileHandler<Category> categoryFileHandler = new FileHandler<>(Category.class, categoryFile);
        categoryRepository = new CategoryRepository(categoryFileHandler);

        FileHandler<Product> productFileHandler = new FileHandler<>(Product.class, productFile);
        productRepository = new ProductRepository(productFileHandler);

        FileHandler<InventoryTransaction> transationFileHandler = new FileHandler<>(InventoryTransaction.class, transationFile);
        transactionRepository = new InventoryTransactionRepository(transationFileHandler);

        FileHandler<Supplier> supplierFileHandler = new FileHandler<>(Supplier.class, supplierFile);
        supplierRepository = new SupplierRepository(supplierFileHandler);

    }

    public void start() throws IOException {
        Terminal terminal = null;
        Screen screen = null;
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Inventory Management System");

            Panel mainPanel = new Panel(new GridLayout(1));
            mainPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true));
            // Create body panel
            Panel bodyPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bodyPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            // Create top navigation panel
            Panel topNavPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            topNavPanel.addComponent(new Button("Dashboard", () -> showDashboard(bodyPanel, productRepository, transactionRepository, textGUI)));
            topNavPanel.addComponent(new Button("Product", () -> showProduct(bodyPanel, productRepository, categoryRepository, textGUI)));
            topNavPanel.addComponent(new Button("Supplier", () -> showSupplier(bodyPanel, supplierRepository, textGUI)));
            topNavPanel.addComponent(new Button("Transaction", () -> showTransaction(bodyPanel, transactionRepository, productRepository, textGUI)));
            topNavPanel.addComponent(new Button("Category", () -> showCategory(bodyPanel, categoryRepository, supplierRepository, textGUI)));
            topNavPanel.addComponent(new Button("Exit", () -> confirmExit(textGUI)));

            // Create bottom panel for help
            Panel bottomPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bottomPanel.addComponent(new EmptySpace(new TerminalSize(0, 1))); 
             bottomPanel.withBorder(Borders.singleLine("Help"));

            bottomPanel.addComponent(new Label("Help: Use arrow keys for navigation. Press Enter or Spacebar to activate buttons. "));
            bottomPanel.addComponent(new Label("If display is abnormal, adjust the terminal size."));
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

    private static void showDashboard(Panel bodyPanel, ProductRepository productRepository, InventoryTransactionRepository transactionRepository, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new DashboardView(transactionRepository, productRepository, textGUI));
    }

    private static void showProduct(Panel bodyPanel, ProductRepository productRepository, CategoryRepository categoryRepository, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new ProductView(productRepository, categoryRepository, textGUI));
    }

    private static void showSupplier(Panel bodyPanel, SupplierRepository repository, WindowBasedTextGUI textGUI) {

        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new SupplierView(repository, textGUI));
    }

    private static void showTransaction(Panel bodyPanel, InventoryTransactionRepository transactionRepository, ProductRepository productRepository, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new InventoryTransactionView(transactionRepository, productRepository, textGUI));
    }

    private static void showCategory(Panel bodyPanel, CategoryRepository categoryRepository, SupplierRepository supplierRepository, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new CategoryView(categoryRepository, supplierRepository, textGUI));
    }

    private static void confirmExit(WindowBasedTextGUI textGUI) {
        boolean confirmExit = MessageDialog.showMessageDialog(textGUI, "Exit", "Are you sure you want to exit?", MessageDialogButton.Yes, MessageDialogButton.No) == MessageDialogButton.Yes;

        if (confirmExit) {
            textGUI.getActiveWindow().close();
            System.exit(0);
        }
    }
}
