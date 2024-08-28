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
        String sql = "INSERT INTO products (name, price, category_id) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getCategoryId());
            stmt.executeUpdate();

            // Optionally, retrieve generated keys if needed
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1)); // Set the generated ID to the product
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
                            rs.getInt("category_id")
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
                        rs.getInt("category_id")
                ));
            }
        }
        return products;
    }

    @Override
    public List<ProductEntity> getAllProducts(String searchValue, Integer min, Integer max, CategoryEntity category) throws SQLException {
        List<ProductEntity> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM products");

        boolean hasCondition = false; 
        if (searchValue != null && !searchValue.isEmpty()) {
            sql.append(" WHERE LOWER(name) LIKE ?");
            hasCondition = true;
        }
        if (min != null) {
            sql.append(hasCondition ? " AND" : " WHERE").append(" price >= ?");
            hasCondition = true;
        }
        if (max != null) {
            sql.append(hasCondition ? " AND" : " WHERE").append(" price <= ?");
            hasCondition = true;
        }
        if (category != null && category.getId() != null) {
            sql.append(hasCondition ? " AND" : " WHERE").append(" category_id = ?");
        }

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (searchValue != null && !searchValue.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchValue.toLowerCase() + "%");
            }
            if (min != null) {
                stmt.setInt(paramIndex++, min);
            }
            if (max != null) {
                stmt.setInt(paramIndex++, max);
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
                            rs.getInt("category_id")
                    ));
                }
            }
        }

        return products;
    }

    @Override
    public void updateProduct(ProductEntity product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, category_id = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getCategoryId());
            stmt.setInt(4, product.getId());
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
                            rs.getInt("category_id")
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
}
