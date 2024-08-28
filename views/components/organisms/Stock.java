/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.organisms;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.views.components.atoms.TitleCenter;
import com.multibahana.inventoryapp.views.components.molecules.StockTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author mohammadnuridin
 */
public class Stock extends JPanel {

    private StockTable tables;

    public Stock() {
        setLayout(new BorderLayout());

        tables = new StockTable();
        TablesContext.getInstance().setCurrentStockTables(tables);

        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(new TitleCenter("Stock"), BorderLayout.CENTER);
//        stockPanel.add(new StockForm(), BorderLayout.SOUTH);

        JSplitPane stockSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stockPanel, tables );
        add(stockSplitPane, BorderLayout.CENTER);

    }


}
