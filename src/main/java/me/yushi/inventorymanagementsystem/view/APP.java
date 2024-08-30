/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.SGR;
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
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowListenerAdapter;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.FileHandler;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;
import me.yushi.inventorymanagementsystem.repository.UnitOfWork;

/**
 *
 * @author yushi
 */
public class APP {

    private IUnitOfWork unitOfWork;
    private WindowBasedTextGUI textGUI;

    public APP(String categoryFile, String transactionFile, String productFile, String supplierFile)
            throws IOException {
        // Initialize FileHandlers
        FileHandler<Category> categoryFileHandler = new FileHandler<>(Category.class, categoryFile);
        FileHandler<Product> productFileHandler = new FileHandler<>(Product.class, productFile);
        FileHandler<InventoryTransaction> transactionFileHandler = new FileHandler<>(InventoryTransaction.class,
                transactionFile);
        FileHandler<Supplier> supplierFileHandler = new FileHandler<>(Supplier.class, supplierFile);

        // Initialize Repositories
        CategoryRepository categoryRepository = new CategoryRepository(categoryFileHandler);
        ProductRepository productRepository = new ProductRepository(productFileHandler);
        InventoryTransactionRepository transactionRepository = new InventoryTransactionRepository(
                transactionFileHandler);
        SupplierRepository supplierRepository = new SupplierRepository(supplierFileHandler);

        // Initialize UnitOfWork
        this.unitOfWork = new UnitOfWork(categoryRepository, transactionRepository, productRepository,
                supplierRepository);

    }

    public void start() throws IOException {
        Terminal terminal = null;
        Screen screen = null;
        try {
            // Create terminal and screen
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            // Create text GUI and window
            textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Inventory Management System");
            // Create main panel
            Panel mainPanel = new Panel(new GridLayout(1));
            mainPanel.setLayoutData(
                    GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true));
            // Create body panel
            Panel bodyPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bodyPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            // Create top navigation panel
            Panel topNavPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            // Add navigation buttons
            topNavPanel.addComponent(new Button("Dashboard", () -> showDashboard(bodyPanel)));
            topNavPanel.addComponent(new Button("Product", () -> showProduct(bodyPanel)));
            topNavPanel.addComponent(new Button("Supplier", () -> showSupplier(bodyPanel)));
            topNavPanel.addComponent(new Button("Transaction", () -> showTransaction(bodyPanel)));
            topNavPanel.addComponent(new Button("Category", () -> showCategory(bodyPanel)));
            topNavPanel.addComponent(new Button("Exit", this::confirmExit));

            // Create bottom panel for help
            Panel bottomPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bottomPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            bottomPanel.withBorder(Borders.singleLine("Quick Help"));
            bottomPanel.addComponent(new Label("Press F1 for detailed help"));
            bottomPanel.addComponent(new Label("Use arrow keys to navigate, Enter or Spacebar to activate"));

            // Add sub-panels to main panel
            mainPanel.addComponent(topNavPanel);
            mainPanel.addComponent(bodyPanel);
            mainPanel.addComponent(bottomPanel);

            // Set main panel as the content of the window
            window.setComponent(mainPanel);
            // Add window listener for F1 key
            window.addWindowListener(new WindowListenerAdapter() {
                @Override
                public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                    if (keyStroke.getKeyType() == KeyType.F1) {
                        showHelpPanel(textGUI);
                        deliverEvent.set(false);
                    }
                }
            });

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

    // Navigation item
    private void showDashboard(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new DashboardView(this.unitOfWork));
    }

    // Navigation item
    private void showProduct(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new ProductView(this.unitOfWork, this.textGUI));
    }

    // Navigation item
    private void showSupplier(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new SupplierView(this.unitOfWork, this.textGUI));
    }

    // navigation item
    private void showTransaction(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new InventoryTransactionView(this.unitOfWork, this.textGUI));
    }

    // navigation item
    private void showCategory(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new CategoryView(this.unitOfWork, this.textGUI));
    }

    // Exit confirmation dialog
    private void confirmExit() {
        boolean confirmExit = MessageDialog.showMessageDialog(this.textGUI, "Exit", "Are you sure you want to exit?",
                MessageDialogButton.Yes, MessageDialogButton.No) == MessageDialogButton.Yes;
        if (confirmExit) {
            textGUI.getActiveWindow().close();
            System.exit(0);
        }
    }

    private void showHelpPanel(WindowBasedTextGUI textGUI) {
        BasicWindow helpWindow = new BasicWindow("Help");
        Panel helpPanel = new Panel(new GridLayout(1));

        Panel helpContent = new Panel(new LinearLayout(Direction.VERTICAL));

        helpContent.addComponent(new Label("Navigation:").addStyle(SGR.BOLD));
        helpContent.addComponent(new Label("- Use arrow keys to navigate"));
        helpContent.addComponent(new Label("- Press Enter or Spacebar to activate buttons"));
        helpContent.addComponent(new Label("- Press F1 to open this help panel"));

        helpContent.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        helpContent.addComponent(new Label("Order Creation Process:").addStyle(SGR.BOLD));

        String[] steps = {
                "1. Add Supplier: 'Suppliers' > 'Add Supplier'",
                "2. Add Category: 'Categories' > 'Add Category'",
                "3. Add Product: 'Products' > 'Add Product'",
                "4. Transaction: 'Transactions' > 'Add Transaction'"
        };

        for (String step : steps) {
            helpContent.addComponent(new Label(step));
        }
        // Add an empty space for padding

        helpPanel.addComponent(helpContent);

        // Add an empty space for padding
        helpPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

        // Create and add the exit button
        Button exitButton = new Button("Exit Help", new Runnable() {
            @Override
            public void run() {
                helpWindow.close();
            }
        });

        Panel buttonPanel = new Panel(new GridLayout(1).setLeftMarginSize(1).setRightMarginSize(1));
        buttonPanel.addComponent(exitButton);
        helpPanel.addComponent(buttonPanel);

        helpWindow.setComponent(helpPanel);

        textGUI.addWindowAndWait(helpWindow);
    }
}
