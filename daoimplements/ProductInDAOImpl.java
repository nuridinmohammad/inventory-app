package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.config.Database;
import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductInDAOImpl implements ProductInDAO {

    @Override
    public void addProductIn(ProductInEntity productIn) throws SQLException {
        String sql = "INSERT INTO products_in (product_id, vendor_id, evidence_id, date, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productIn.getProductId());
            stmt.setInt(2, productIn.getVendorId());
            stmt.setInt(3, productIn.getEvidenceId());
            stmt.setDate(4, new java.sql.Date(productIn.getDate().getTime()));
            stmt.setInt(5, productIn.getQuantity());
            stmt.executeUpdate();
        }
    }

    @Override
    public ProductInEntity getProductInById(Integer id) throws SQLException {
        String sql = "SELECT * FROM products_in WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductInEntity(
                            rs.getInt("id"),
                            rs.getInt("product_id"),
                            rs.getInt("vendor_id"),
                            rs.getInt("evidence_id"),
                            rs.getDate("date"),
                            rs.getInt("quantity")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<ProductInEntity> getAllProductIn() throws SQLException {
        List<ProductInEntity> productIns = new ArrayList<>();
        String sql = "SELECT * FROM products_in";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productIns.add(new ProductInEntity(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("vendor_id"),
                        rs.getInt("evidence_id"),
                        rs.getDate("date"),
                        rs.getInt("quantity")
                ));
            }
        }
        return productIns;
    }

    @Override
    public List<ProductInEntity> getAllProductIn(String searchValue, Date date, VendorEntity vendor) throws SQLException {
        List<ProductInEntity> productIns = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT pi.* FROM products_in pi "
                + "JOIN products p ON pi.product_id = p.id "
                + "JOIN evidences e ON pi.evidence_id = e.id "
                + "WHERE 1=1"
        );

        // Add conditions to the query based on the parameters
        if (searchValue != null && !searchValue.isEmpty()) {
            sql.append(" AND (LOWER(p.name) LIKE ? OR e.code LIKE ?)");
        }
        if (date != null) {
            sql.append(" AND pi.date = ?");
        }
        if (vendor != null) {
            sql.append(" AND pi.vendor_id = ?");
        }

        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (searchValue != null && !searchValue.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchValue + "%");
                stmt.setString(paramIndex++, "%" + searchValue + "%");
            }
            if (date != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(date.getTime()));
            }
            if (vendor != null) {
                stmt.setInt(paramIndex++, vendor.getId());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productIns.add(new ProductInEntity(
                            rs.getInt("id"),
                            rs.getInt("product_id"),
                            rs.getInt("vendor_id"),
                            rs.getInt("evidence_id"),
                            rs.getDate("date"),
                            rs.getInt("quantity")
                    ));
                }
            }
        }
        return productIns;
    }

    @Override
    public void updateProductIn(ProductInEntity productIn) throws SQLException {
        String sql = "UPDATE products_in SET product_id = ?, vendor_id = ?, evidence_id = ?, date = ?, quantity = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productIn.getProductId());
            stmt.setInt(2, productIn.getVendorId());
            stmt.setInt(3, productIn.getEvidenceId());
            stmt.setDate(4, new java.sql.Date(productIn.getDate().getTime()));
            stmt.setInt(5, productIn.getQuantity());
            stmt.setInt(6, productIn.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteProductIn(Integer id) throws SQLException {
        String sql = "DELETE FROM products_in WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
