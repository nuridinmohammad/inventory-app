package com.multibahana.inventoryapp.controllers;

import com.multibahana.inventoryapp.entities.CategoryRelationEntity;
import com.multibahana.inventoryapp.dao.CategoryRelationDAO;

import java.sql.SQLException;
import java.util.List;

public class CategoryRelationController {

    private final CategoryRelationDAO categoryRelationDAO;

    public CategoryRelationController(CategoryRelationDAO categoryRelationDAO) {
        this.categoryRelationDAO = categoryRelationDAO;
    }

    public void addCategoryRelation(CategoryRelationEntity relation) {
        try {
            categoryRelationDAO.addCategoryRelation(relation);
        } catch (SQLException e) {
            System.err.println("Error adding category relation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public CategoryRelationEntity getCategoryRelationByIds(int ancestorId, int descendantId) {
        try {
            return categoryRelationDAO.getCategoryRelationByIds(ancestorId, descendantId);
        } catch (SQLException e) {
            System.err.println("Error retrieving category relation: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<CategoryRelationEntity> getAllCategoryRelations() {
        try {
            return categoryRelationDAO.getAllCategoryRelations();
        } catch (SQLException e) {
            System.err.println("Error listing category relations: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<CategoryRelationEntity> getAllCategoryRelationsByAncestorId(Integer ancestorId) {
        try {
            return categoryRelationDAO.getAllCategoryRelationsByAncestorId(ancestorId);
        } catch (SQLException e) {
            System.err.println("Error listing category relations by ancestor ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateCategoryRelation(CategoryRelationEntity relation) {
        try {
            categoryRelationDAO.updateCategoryRelation(relation);
        } catch (SQLException e) {
            System.err.println("Error updating category relation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCategoryRelation(Integer ancestorId, Integer descendantId) {
        try {
            categoryRelationDAO.deleteCategoryRelation(ancestorId, descendantId);
        } catch (SQLException e) {
            System.err.println("Error deleting category relation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
