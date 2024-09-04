package com.multibahana.inventoryapp.daoimplements;

import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.config.Database;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.multibahana.inventoryapp.dao.CategoryDAO;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public Integer addCategory(CategoryEntity category) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public CategoryEntity getCategoryById(Integer id) throws SQLException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CategoryEntity(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    @Override
    public Map<Integer, CategoryEntity> getAllCategories() throws SQLException {
        Map<Integer, CategoryEntity> categoriesMap = new HashMap<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = Database.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                CategoryEntity category = new CategoryEntity(id, name);
                categoriesMap.put(id, category);
            }
        }
        return categoriesMap;
    }

    @Override
    public void updateCategory(CategoryEntity category) throws SQLException {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteCategory(Integer id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}