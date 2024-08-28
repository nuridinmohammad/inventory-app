/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.organisms;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.views.components.atoms.TitleCenter;
import com.multibahana.inventoryapp.views.components.molecules.ProductForm;
import com.multibahana.inventoryapp.views.components.molecules.ProductTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author mohammadnuridin
 */
public class Product extends JPanel {

    private ProductTable tables;

    public Product() {
        setLayout(new BorderLayout());

        tables = new ProductTable();
        TablesContext.getInstance().setCurrentProductTables(tables);

        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(new TitleCenter("PRODUCT"), BorderLayout.CENTER);
        productPanel.add(new ProductForm(), BorderLayout.SOUTH);

        JSplitPane productSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productPanel, tables);
        add(productSplitPane, BorderLayout.CENTER);

    }

}
