/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.organisms;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.views.components.atoms.TitleCenter;
import com.multibahana.inventoryapp.views.components.molecules.VendorForm;
import com.multibahana.inventoryapp.views.components.molecules.VendorTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author mohammadnuridin
 */
public class Vendor extends JPanel {

    private VendorTable tables;

    public Vendor() {
        setLayout(new BorderLayout());

        tables = new VendorTable();
        TablesContext.getInstance().setCurrentVendorTables(tables);

        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(new TitleCenter("VENDOR"), BorderLayout.CENTER);
        stockPanel.add(new VendorForm(), BorderLayout.SOUTH);

        JSplitPane stockSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stockPanel, tables );
        add(stockSplitPane, BorderLayout.CENTER);

    }

}
