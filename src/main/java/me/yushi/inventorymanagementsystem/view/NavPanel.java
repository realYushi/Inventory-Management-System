package me.yushi.inventorymanagementsystem.view;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class NavPanel extends JPanel{
    public NavPanel(BodyPanel bodyPanel){
        this.setBorder(BorderFactory.createTitledBorder("Navigation"));
        this.setLayout(new MigLayout("wrap,fill","[grow]","[grow][grow][grow][grow][grow][grow]"));
        JButton dashboardButton = new JButton("Dashboard");
        JButton productsButton = new JButton("Products");
        JButton categoriesButton = new JButton("Categories");
        JButton suppliersButton = new JButton("Suppliers");
        JButton transactionsButton = new JButton("Transactions");

        this.add(dashboardButton, "growx");
        this.add(productsButton, "growx");
        this.add(categoriesButton, "growx");
        this.add(suppliersButton, "growx");
        this.add(transactionsButton, "growx");

        dashboardButton.addActionListener(e -> bodyPanel.showPanel("dashboard"));
        productsButton.addActionListener(e -> bodyPanel.showPanel("products"));
        categoriesButton.addActionListener(e -> bodyPanel.showPanel("categories"));
        suppliersButton.addActionListener(e -> bodyPanel.showPanel("suppliers"));
        transactionsButton.addActionListener(e -> bodyPanel.showPanel("transactions"));
    }

}
