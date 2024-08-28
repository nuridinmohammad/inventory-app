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
    try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, relation.getAncestorId());
        pstmt.setInt(2, relation.getDescendantId());
        pstmt.setString(3, relation.getName());
        pstmt.executeUpdate();
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
        String sql = "UPDATE category_relations SET name = ?, ancestor_id = ? WHERE descendant_id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, relation.getName());
            pstmt.setInt(2, relation.getAncestorId());
            pstmt.setInt(3, relation.getDescendantId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteCategoryRelation(Integer ancestorId, Integer descendantId) throws SQLException {
        String sql = "DELETE FROM category_relations WHERE ancestor_id = ? AND descendant_id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ancestorId);
            pstmt.setInt(2, descendantId);
            pstmt.executeUpdate();
        }
    }
}
