package com.multibahana.inventoryapp.controllers;

import com.multibahana.inventoryapp.dao.ProductDAO;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;

import java.sql.SQLException;
import java.util.List;

public class ProductController {

    private ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void addProduct(ProductEntity product) {
        try {
            productDAO.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductEntity getProductById(Integer id) {
        try {
            return productDAO.getProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductEntity> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductEntity> getAllProducts(String searchValue, CategoryEntity category) {
        try {
            return productDAO.getAllProducts(searchValue, category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductEntity> getAllProductsByCategoryId(Integer id) {
        try {
            return productDAO.getAllProductsByCategoryId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateProduct(ProductEntity product) {
        try {
            productDAO.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStockProduct(ProductEntity product) {
        try {
            productDAO.updateStockProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Integer id) {
        try {
            productDAO.deleteProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductByCategoryId(Integer id) {
        try {
            productDAO.deleteProductsByCategoryId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
