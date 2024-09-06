package com.multibahana.inventoryapp.daoimplements;

import com.multibahana.inventoryapp.dao.VendorDAO;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.config.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAOImpl implements VendorDAO {

    @Override
    public void addVendor(VendorEntity vendor) throws SQLException {
        String sql = "INSERT INTO vendors (name, address) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getAddress());
            pstmt.executeUpdate();

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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
    public VendorEntity getVendorById(Integer id) throws SQLException {
        String sql = "SELECT * FROM vendors WHERE id = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new VendorEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
            }
        }
        return null;
    }

    @Override
    public List<VendorEntity> getAllVendors() throws SQLException {
        List<VendorEntity> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendors";
        try (Statement stmt = Database.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                VendorEntity vendor = new VendorEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address")
                );
                vendors.add(vendor);
            }
        }
        return vendors;
    }

    @Override
    public List<VendorEntity> getAllVendors(String name, String address) throws SQLException {
        List<VendorEntity> vendors = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM vendors WHERE 1=1");

        if (name != null && !name.isEmpty()) {
            sql.append(" AND LOWER(name) LIKE ?");
        }
        if (address != null && !address.isEmpty()) {
            sql.append(" AND LOWER(address) LIKE ?");
        }

        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (name != null && !name.isEmpty()) {
                stmt.setString(paramIndex++, "%" + name.toLowerCase() + "%");
            }
            if (address != null && !address.isEmpty()) {
                stmt.setString(paramIndex++, "%" + address.toLowerCase() + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    VendorEntity vendor = new VendorEntity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("address")
                    );
                    vendors.add(vendor);
                }
            }
        }

        return vendors;
    }

    @Override
    public void updateVendor(VendorEntity vendor) throws SQLException {
        String selectSql = "SELECT id FROM vendors WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE vendors SET name = ?, address = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, vendor.getId());
            selectStmt.executeQuery();

            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, vendor.getName());
            updateStmt.setString(2, vendor.getAddress());
            updateStmt.setInt(3, vendor.getId());
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
    public void deleteVendor(Integer id) throws SQLException {
        String selectSql = "SELECT id FROM vendors WHERE id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM vendors WHERE id = ?";

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
