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

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, category.getName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    conn.commit();
                    return generatedKeys.getInt(1);
                }
            }
            conn.rollback();
            throw new SQLException("Creating category failed, no ID obtained.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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
        String selectSql = "SELECT id FROM categories WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE categories SET name = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, category.getId());
            selectStmt.executeQuery();

            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, category.getName());
            updateStmt.setInt(2, category.getId());
            updateStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (updateStmt != null) {
                try {
                    updateStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteCategory(Integer id) throws SQLException {
        String selectSql = "SELECT id FROM categories WHERE id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM categories WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, id);
            selectStmt.executeQuery();

            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deleteStmt != null) {
                try {
                    deleteStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
