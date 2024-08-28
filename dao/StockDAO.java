package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.StockEntity;
import java.sql.SQLException;
import java.util.List;

public interface StockDAO {

    Integer addStock(StockEntity stock) throws SQLException;

    StockEntity getStockById(Integer id) throws SQLException;

    List<StockEntity> getAllStocks() throws SQLException;
    
    List<StockEntity> getAllStocksStatic() throws SQLException;

    void updateStock(StockEntity stock) throws SQLException;

    void deleteStock(Integer id) throws SQLException;
}
