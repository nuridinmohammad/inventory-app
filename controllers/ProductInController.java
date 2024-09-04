package com.multibahana.inventoryapp.controller;

import com.multibahana.inventoryapp.dao.ProductInDAO;
import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ProductInController {

    private ProductInDAO productInDAO;

    public ProductInController(ProductInDAO productInDAO) {
        this.productInDAO = productInDAO;

    }

    public void addProductIn(ProductInEntity productIn) {
        try {
            productInDAO.addProductIn(productIn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProductInEntity getProductInById(Integer id) {
        try {
            return productInDAO.getProductInById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductInEntity> getAllProductIn() {
        try {
            return productInDAO.getAllProductIn();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
   
    public List<ProductInEntity> getAllProductIn(String searchValue, Date date, VendorEntity vendor)  {
        try {
            return productInDAO.getAllProductIn(searchValue, date, vendor);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateProductIn(ProductInEntity productIn) {
        try {
            productInDAO.updateProductIn(productIn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductIn(Integer id) {
        try {
            productInDAO.deleteProductIn(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
