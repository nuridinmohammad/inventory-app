package com.multibahana.inventoryapp.daoimplements;

import com.multibahana.inventoryapp.config.Database;
import com.multibahana.inventoryapp.entities.CategoryRelationEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.multibahana.inventoryapp.dao.CategoryRelationDAO;

public class CategoryRelationDAOImpl implements CategoryRelationDAO {

    @Override
    public void addCategoryRelation(CategoryRelationEntity relation) throws SQLException {
        String sql = "INSERT INTO category_relations (ancestor_id, descendant_id, name) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, relation.getAncestorId());
            pstmt.setInt(2, relation.getDescendantId());
            pstmt.setString(3, relation.getName());
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
    public CategoryRelationEntity getCategoryRelationByIds(Integer ancestorId, Integer descendantId) throws SQLException {
        String query = "SELECT * FROM category_relations WHERE ancestor_id = ? AND descendant_id = ?";
        CategoryRelationEntity folderRelation = null;
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, ancestorId);
            stmt.setLong(2, descendantId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    folderRelation = new CategoryRelationEntity(rs.getInt("ancestor_id"), rs.getInt("descendant_id"), rs.getString("name"));
                }
            }
        }
        return folderRelation;
    }

    @Override
    public List<CategoryRelationEntity> getAllCategoryRelations() throws SQLException {
        List<CategoryRelationEntity> relations = new ArrayList<>();
        String sql = "SELECT * FROM category_relations";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                relations.add(new CategoryRelationEntity(
                        rs.getInt("ancestor_id"),
                        rs.getInt("descendant_id"),
                        rs.getString("name"))
                );
            }
        }
        return relations;
    }

    @Override
    public List<CategoryRelationEntity> getAllCategoryRelationsByAncestorId(Integer ancestorId) throws SQLException {
        List<CategoryRelationEntity> relations = new ArrayList<>();
        String sql = "SELECT * FROM category_relations WHERE ancestor_id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ancestorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                relations.add(new CategoryRelationEntity(
                        rs.getInt("ancestor_id"),
                        rs.getInt("descendant_id"),
                        rs.getString("name")
                ));
            }
        }
        return relations;
    }

    @Override
    public void updateCategoryRelation(CategoryRelationEntity relation) throws SQLException {
        String selectSql = "SELECT * FROM category_relations WHERE descendant_id = ? FOR UPDATE";
        String updateSql = "UPDATE category_relations SET name = ?, ancestor_id = ? WHERE descendant_id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, relation.getDescendantId());
            selectStmt.executeQuery();

            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, relation.getName());
            updateStmt.setInt(2, relation.getAncestorId());
            updateStmt.setInt(3, relation.getDescendantId());
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
    public void deleteCategoryRelation(Integer ancestorId, Integer descendantId) throws SQLException {
        String selectSql = "SELECT * FROM category_relations WHERE ancestor_id = ? AND descendant_id = ? FOR UPDATE";
        String deleteSql = "DELETE FROM category_relations WHERE ancestor_id = ? AND descendant_id = ?";

        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, ancestorId);
            selectStmt.setInt(2, descendantId);
            selectStmt.executeQuery();

            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, ancestorId);
            deleteStmt.setInt(2, descendantId);
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
