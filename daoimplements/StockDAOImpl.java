package com.multibahana.inventoryapp.daoimplements;

import com.multibahana.inventoryapp.dao.StockDAO;
import com.multibahana.inventoryapp.entities.StockEntity;
import com.multibahana.inventoryapp.config.Database;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    @Override
    public Integer addStock(StockEntity stock) throws SQLException {
        String sql = "INSERT INTO stocks (no_evidence, date, amount, product_id, vendor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, stock.getNoEvidence());
            stmt.setDate(2, new java.sql.Date(stock.getDateReceipt().getTime()));
            stmt.setInt(3, stock.getAmount());
            stmt.setInt(4, stock.getProductId());
            stmt.setInt(5, stock.getVendorId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating stock failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public StockEntity getStockById(Integer id) throws SQLException {
        String sql = "SELECT * FROM stocks WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StockEntity(
                            rs.getInt("id"),
                            rs.getString("no_evidence"),
                            rs.getDate("date"),
                            rs.getInt("amount"),
                            rs.getInt("product_id"),
                            rs.getInt("vendor_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<StockEntity> getAllStocks() throws SQLException {
        List<StockEntity> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stocks";
        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stocks.add(new StockEntity(
                        rs.getInt("id"),
                        rs.getString("no_evidence"),
                        rs.getDate("date"),
                        rs.getInt("amount"),
                        rs.getInt("product_id"),
                        rs.getInt("vendor_id")
                ));
            }
        }
        return stocks;
    }

    @Override
    public List<StockEntity> getAllStocks(String noEvidenceField, java.util.Date date, VendorEntity vendorEntity) throws SQLException {
        List<StockEntity> stocks = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.id, s.no_evidence, s.date, s.amount, s.product_id, s.vendor_id "
                + "FROM stocks s JOIN products p ON p.id = s.product_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (noEvidenceField != null && !noEvidenceField.isEmpty()) {
            sql.append(" AND (LOWER(s.no_evidence) LIKE ? OR LOWER(p.name) LIKE ?)");
            params.add("%" + noEvidenceField.toLowerCase() + "%");
            params.add("%" + noEvidenceField.toLowerCase() + "%");
        }

        if (date != null) {
            sql.append(" AND s.date = ?");
            params.add(new java.sql.Date(date.getTime()));
        }

        if (vendorEntity != null) {
            sql.append(" AND s.vendor_id = ?");
            params.add(vendorEntity.getId());
        }

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(new StockEntity(
                            rs.getInt("id"),
                            rs.getString("no_evidence"),
                            rs.getDate("date"),
                            rs.getInt("amount"),
                            rs.getInt("product_id"),
                            rs.getInt("vendor_id")
                    ));
                }
            }
        }

        return stocks;
    }

    @Override
    public void updateStock(StockEntity stock) throws SQLException {
        String sql = "UPDATE stocks SET no_evidence = ?, date = ?, amount = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stock.getNoEvidence());
            stmt.setDate(2, new java.sql.Date(stock.getDateReceipt().getTime()));
            stmt.setInt(3, stock.getAmount());
            stmt.setInt(4, stock.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteStock(Integer id) throws SQLException {
        String sql = "DELETE FROM stocks WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<StockEntity> getAllStocksStatic() throws SQLException {
        List<StockEntity> stocks = new ArrayList<>();

        String sql = "SELECT p.id AS product_id, p.name AS product_name, SUM(s.amount) AS total_amount, c.name AS category "
                + "FROM stocks s "
                + "JOIN products p ON p.id = s.product_id "
                + "JOIN categories c ON c.id = p.category_id "
                + "GROUP BY p.id, p.name, c.name "
                + "ORDER BY total_amount DESC";

        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                stocks.add(new StockEntity(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("total_amount"),
                        rs.getString("category")
                ));
            }
        }

        return stocks;
    }

    @Override
    public List<StockEntity> getAllStocksStatic(String productEntity, CategoryEntity categoryEntity) throws SQLException {
        List<StockEntity> stocks = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT p.id AS product_id, p.name AS product_name, SUM(s.amount) AS total_amount, c.name AS category "
                + "FROM stocks s "
                + "JOIN products p ON p.id = s.product_id "
                + "JOIN categories c ON c.id = p.category_id "
        );

        List<Object> params = new ArrayList<>();

        boolean whereAdded = false;
        if (productEntity != null && !productEntity.isEmpty()) {
            sql.append("WHERE LOWER(p.name) LIKE ? ");
            params.add("%" + productEntity + "%");
            whereAdded = true;
        }
        if (categoryEntity != null) {
            sql.append(whereAdded ? "AND " : "WHERE ").append("c.id = ? ");
            params.add(categoryEntity.getId());
        }

        sql.append("GROUP BY p.id, p.name, c.name ");
        sql.append("ORDER BY total_amount DESC");

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stocks.add(new StockEntity(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("total_amount"),
                            rs.getString("category")
                    ));
                }
            }
        }

        return stocks;
    }

}
