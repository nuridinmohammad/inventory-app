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

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = Database.getConnection();

            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getCategoryId());
            stmt.setString(4, product.getProductCode());
            stmt.setDouble(5, product.getSellPrice());

            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }

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
        String selectSql = "SELECT id FROM products WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE products SET name = ?, price = ?, category_id = ?, product_code = ?, stock = ?, sell_price = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();

            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, product.getId());
            selectStmt.executeQuery();

            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, product.getName());
            updateStmt.setDouble(2, product.getPrice());
            updateStmt.setInt(3, product.getCategoryId());
            updateStmt.setString(4, product.getProductCode());
            updateStmt.setInt(5, product.getStock());
            updateStmt.setDouble(6, product.getSellPrice());
            updateStmt.setInt(7, product.getId());
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
    public void deleteProduct(Integer id) throws SQLException {
        String selectSql = "SELECT id FROM products WHERE id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM products WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Mengunci baris yang akan dihapus
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, id);
            selectStmt.executeQuery();  // Menjalankan query ini untuk mengunci baris

            // Menghapus baris
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
        String selectSql = "SELECT id FROM products WHERE category_id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM products WHERE category_id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Mengunci baris yang akan dihapus
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, categoryId);
            rs = selectStmt.executeQuery();  // Menjalankan query ini untuk mengunci baris

            // Menghapus baris
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, categoryId);
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

    @Override
    public void updateStockProduct(ProductEntity product) throws SQLException {
        String selectSql = "SELECT id FROM products WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE products SET stock = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Mengunci baris yang akan diperbarui
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, product.getId());
            selectStmt.executeQuery();  // Menjalankan query ini untuk mengunci baris

            // Memperbarui stok produk
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, product.getStock());
            updateStmt.setInt(2, product.getId());
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

}
