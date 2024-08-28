package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    void addProduct(ProductEntity product) throws SQLException;

    ProductEntity getProductById(Integer id) throws SQLException;

    List<ProductEntity> getAllProducts() throws SQLException;

    List<ProductEntity> getAllProducts(String searchValue, Integer min, Integer max, CategoryEntity category) throws SQLException;

    void updateProduct(ProductEntity product) throws SQLException;

    void deleteProduct(Integer id) throws SQLException;

    void deleteProductsByCategoryId(Integer id) throws SQLException;

    List<ProductEntity> getAllProductsByCategoryId(Integer id) throws SQLException;
}
