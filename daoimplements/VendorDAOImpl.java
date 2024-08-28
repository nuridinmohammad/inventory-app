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
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getAddress());
            pstmt.executeUpdate();
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
        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
    public void updateVendor(VendorEntity vendor) throws SQLException {
        String sql = "UPDATE vendors SET name = ?, address = ? WHERE id = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, vendor.getName());
            pstmt.setString(2, vendor.getAddress());
            pstmt.setInt(3, vendor.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteVendor(Integer id) throws SQLException {
        String sql = "DELETE FROM vendors WHERE id = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
