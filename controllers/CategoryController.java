package com.multibahana.inventoryapp.controllers;

import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.dao.CategoryDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CategoryController {

    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public Integer addCategory(CategoryEntity categoryEntity) {
        try {
            return categoryDAO.addCategory(categoryEntity);
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public CategoryEntity getCategory(Integer id) {
        try {
            return categoryDAO.getCategoryById(id);
        } catch (SQLException e) {
            System.err.println("Error retrieving category: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, CategoryEntity> getAllCategories() {
        try {
            return categoryDAO.getAllCategories();
        } catch (SQLException e) {
            System.err.println("Error listing categories: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateCategory(int id, String name) {
        try {
            CategoryEntity category = new CategoryEntity(id, name);
            categoryDAO.updateCategory(category);
        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCategory(Integer id) {
        try {
            categoryDAO.deleteCategory(id);
        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
