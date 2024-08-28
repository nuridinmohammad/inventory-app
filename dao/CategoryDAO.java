package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.CategoryEntity;
import java.sql.SQLException;
import java.util.Map;

public interface CategoryDAO {

    Integer addCategory(CategoryEntity category) throws SQLException;

    CategoryEntity getCategoryById(Integer id) throws SQLException;

    Map<Integer, CategoryEntity> getAllCategories() throws SQLException;

    void updateCategory(CategoryEntity category) throws SQLException;

    void deleteCategory(Integer id) throws SQLException;
}
