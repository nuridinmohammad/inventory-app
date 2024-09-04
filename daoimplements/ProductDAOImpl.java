package com.multibahana.inventoryapp.daoimplements;

import com.multibahana.inventoryapp.dao.ProductDAO;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.config.Database;
import com.multibahana.inventoryapp.entities.CategoryEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(ProductEntity product) throws SQLException {
        String sql = "INSERT INTO products (name, price, category_id, product_code, sell_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getCategoryId());
            stmt.setString(4, product.getProductCode());
            stmt.setDouble(5, product.getSellPrice());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public ProductEntity getProductById(Integer id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductEntity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getInt("category_id"),
                            rs.getString("product_code"),
                            rs.getDouble("sell_price")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<ProductEntity> getAllProducts() throws SQLException {
        List<ProductEntity> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new ProductEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id"),
                        rs.getString("product_code"),
                        rs.getDouble("sell_price")
                ));
            }
        }
        return products;
    }

    @Override
    public List<ProductEntity> getAllProducts(String searchValue, CategoryEntity category) throws SQLException {
        List<ProductEntity> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM products");

        boolean hasCondition = false;

        if (searchValue != null && !searchValue.isEmpty()) {
            sql.append(" WHERE LOWER(name) LIKE ? OR product_code = ?");
            hasCondition = true;
        }

        if (category != null && category.getId() != null) {
            if (hasCondition) {
                sql.append(" AND");
            } else {
                sql.append(" WHERE");
            }
            sql.append(" category_id = ?");
        }

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (searchValue != null && !searchValue.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchValue.toLowerCase() + "%");
                stmt.setString(paramIndex++, searchValue);
            }

            if (category != null && category.getId() != null) {
                stmt.setInt(paramIndex++, category.getId());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductEntity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getInt("category_id"),
                            rs.getString("product_code"),
                            rs.getDouble("sell_price")
                    ));
                }
            }
        }

        return products;
    }

    @Override
    public void updateProduct(ProductEntity product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, category_id = ?, product_code = ?, stock = ?, sell_price = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getCategoryId());
            stmt.setString(4, product.getProductCode());
            stmt.setInt(5, product.getStock());
            stmt.setDouble(6, product.getSellPrice());
            stmt.setInt(7, product.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(Integer id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ProductEntity> getAllProductsByCategoryId(Integer id) throws SQLException {
        List<ProductEntity> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductEntity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getInt("category_id"),
                            rs.getString("product_code"),
                            rs.getDouble("sell_price")
                    ));
                }
            }
        }
        return products;
    }

    @Override
    public void deleteProductsByCategoryId(Integer categoryId) throws SQLException {
        String sql = "DELETE FROM products WHERE category_id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateStockProduct(ProductEntity product) throws SQLException {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getStock());
            stmt.setInt(2, product.getId());
            stmt.executeUpdate();
        }
    }
}
