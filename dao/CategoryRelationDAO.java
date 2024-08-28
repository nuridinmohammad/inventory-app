package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.CategoryRelationEntity;
import java.sql.SQLException;
import java.util.List;

public interface CategoryRelationDAO {

    void addCategoryRelation(CategoryRelationEntity relation) throws SQLException;

    CategoryRelationEntity getCategoryRelationByIds(Integer ancestorId, Integer descendantId) throws SQLException;

    List<CategoryRelationEntity> getAllCategoryRelations() throws SQLException;

    List<CategoryRelationEntity> getAllCategoryRelationsByAncestorId(Integer id) throws SQLException;

    void updateCategoryRelation(CategoryRelationEntity relation) throws SQLException;

    void deleteCategoryRelation(Integer ancestorId, Integer descendantId) throws SQLException;
}
