package com.multibahana.inventoryapp.controllers;

import com.multibahana.inventoryapp.entities.StockEntity;
import com.multibahana.inventoryapp.dao.StockDAO;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;

import java.sql.SQLException;
import java.util.List;

public class StockController {

    private final StockDAO stockDAO;

    public StockController(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    public Integer addStock(StockEntity stockEntity) {
        try {
            return stockDAO.addStock(stockEntity);
        } catch (SQLException e) {
            System.err.println("Error adding stock: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public StockEntity getStockById(int id) {
        try {
            return stockDAO.getStockById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving stock: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<StockEntity> getAllStocks() {
        try {
            return stockDAO.getAllStocks();
        } catch (SQLException e) {
            System.err.println("Error listing stocks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public List<StockEntity> getAllStocks(String noEvidenceField, java.util.Date date, VendorEntity vendorEntity){
        try {
            return stockDAO.getAllStocks(noEvidenceField, date, vendorEntity);
        } catch (SQLException e) {
            System.err.println("Error listing stocks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<StockEntity> getAllStocksStatic() {
        try {
            return stockDAO.getAllStocksStatic();
        } catch (SQLException e) {
            System.err.println("Error listing stocks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public List<StockEntity> getAllStocksStatic(String product, CategoryEntity categoryEntity) {
        try {
            return stockDAO.getAllStocksStatic(product, categoryEntity);
        } catch (SQLException e) {
            System.err.println("Error listing stocks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateStock(StockEntity stockEntity) {
        try {
            stockDAO.updateStock(stockEntity);
        } catch (SQLException e) {
            System.err.println("Error updating stock: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteStock(int id) {
        try {
            stockDAO.deleteStock(id);
        } catch (SQLException e) {
            System.err.println("Error deleting stock: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
