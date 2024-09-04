package com.multibahana.inventoryapp.context;

import com.multibahana.inventoryapp.views.components.molecules.ProductTable;
import com.multibahana.inventoryapp.views.components.molecules.ReceiptTable;
import com.multibahana.inventoryapp.views.components.molecules.VendorTable;

public class TablesContext {

    private static TablesContext instance;
    private ReceiptTable currentReciptTables;
    private ProductTable currentProductTables;
    private VendorTable currentVendorTables;

    private TablesContext() {
    }

    public static synchronized TablesContext getInstance() {
        if (instance == null) {
            instance = new TablesContext();
        }
        return instance;
    }

    public ReceiptTable getCurrentReceiptTables() {
        return currentReciptTables;
    }

    public void setCurrentReceiptTables(ReceiptTable currentTables) {
        this.currentReciptTables = currentTables;
    }

    public void updateReceiptTables(ReceiptTable tables) {
        this.currentReciptTables = tables;
    }

    public ProductTable getCurrentProductTables() {
        return currentProductTables;
    }

    public void setCurrentProductTables(ProductTable currentTables) {
        this.currentProductTables = currentTables;
    }

    public void updateProductTables(ProductTable tables) {
        this.currentProductTables = tables;
    }

    public VendorTable getCurrentVendorTables() {
        return currentVendorTables;
    }

    public void setCurrentVendorTables(VendorTable currentTables) {
        this.currentVendorTables = currentTables;
    }

    public void updateVendorTables(VendorTable tables) {
        this.currentVendorTables = tables;
    }

}
