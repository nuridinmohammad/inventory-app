package com.multibahana.inventoryapp.context;

import com.multibahana.inventoryapp.entities.StockEntity;

import java.util.Date;

public class StockContext {
    private static StockContext instance;
    private StockEntity currentStock;

    private StockContext() {}

    public static synchronized StockContext getInstance() {
        if (instance == null) {
            instance = new StockContext();
        }
        return instance;
    }

    public StockEntity getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(String noEvidence, Date dateReceipt, Integer amount, Integer productId, Integer vendorId) {
        this.currentStock = new StockEntity(noEvidence, dateReceipt, amount, productId, vendorId);
    }

    public void updateStock(StockEntity stock) {
        this.currentStock = stock;
    }
}
