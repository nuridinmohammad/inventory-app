package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.config.Database;
import com.multibahana.inventoryapp.entities.EvidenceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvidenceDAOImpl implements EvidenceDAO {

    @Override
    public Integer addEvidence(EvidenceEntity evidence) throws SQLException {
        String sql = "INSERT INTO evidences (code) VALUES (?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evidence.getCode());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); 
                    }
                }
            }
            return null; 
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public EvidenceEntity getEvidenceById(Integer id) throws SQLException {
        String sql = "SELECT * FROM evidences WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new EvidenceEntity(rs.getInt("id"), rs.getString("code"));
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<EvidenceEntity> getAllEvidences() throws SQLException {
        List<EvidenceEntity> evidences = new ArrayList<>();
        String sql = "SELECT * FROM evidences";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                evidences.add(new EvidenceEntity(rs.getInt("id"), rs.getString("code")));
            }
        }
        return evidences;
    }

    @Override
    public void updateEvidence(EvidenceEntity evidence) throws SQLException {
        String sql = "UPDATE evidences SET code = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evidence.getCode());
            stmt.setInt(2, evidence.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteEvidence(Integer id) throws SQLException {
        String sql = "DELETE FROM evidences WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
