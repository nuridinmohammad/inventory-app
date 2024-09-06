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

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, evidence.getCode());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    conn.commit();
                    return generatedKeys.getInt(1);
                }
            }
            conn.rollback();
            return null;
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
        String selectSql = "SELECT id FROM evidences WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE evidences SET code = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, evidence.getId());
            selectStmt.executeQuery();

            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, evidence.getCode());
            updateStmt.setInt(2, evidence.getId());
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
    public void deleteEvidence(Integer id) throws SQLException {
        String selectSql = "SELECT id FROM evidences WHERE id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM evidences WHERE id = ?";

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
