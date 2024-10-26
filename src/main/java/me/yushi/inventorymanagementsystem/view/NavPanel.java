package me.yushi.inventorymanagementsystem.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class NavPanel extends JPanel {

    private JButton dashboardButton;
    private JButton productsButton;
    private JButton categoriesButton;
    private JButton suppliersButton;
    private JButton transactionsButton;

    public NavPanel(BodyPanel bodyPanel) {
        initializeComponents();
        setLayoutAndBorder();
        addComponentsToPanel();
        addEventListeners(bodyPanel);
    }

    private void initializeComponents() {
        dashboardButton = new JButton("Dashboard");
        productsButton = new JButton("Products");
        categoriesButton = new JButton("Categories");
        suppliersButton = new JButton("Suppliers");
        transactionsButton = new JButton("Transactions");
    }

    private void setLayoutAndBorder() {
        this.setBorder(BorderFactory.createTitledBorder("Navigation"));
        this.setLayout(new MigLayout("wrap,fill", "[grow]", "[grow][grow][grow][grow][grow][grow]"));
    }

    private void addComponentsToPanel() {
        this.add(dashboardButton, "growx");
        this.add(productsButton, "growx");
        this.add(categoriesButton, "growx");
        this.add(suppliersButton, "growx");
        this.add(transactionsButton, "growx");
    }

    private void addEventListeners(BodyPanel bodyPanel) {
        dashboardButton.addActionListener(e -> bodyPanel.showPanel("dashboard"));
        productsButton.addActionListener(e -> bodyPanel.showPanel("products"));
        categoriesButton.addActionListener(e -> bodyPanel.showPanel("categories"));
        suppliersButton.addActionListener(e -> bodyPanel.showPanel("suppliers"));
        transactionsButton.addActionListener(e -> bodyPanel.showPanel("transactions"));
    }
}
