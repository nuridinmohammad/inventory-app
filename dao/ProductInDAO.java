package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ProductInDAO {

    void addProductIn(ProductInEntity productIn) throws SQLException;

    ProductInEntity getProductInById(Integer id) throws SQLException;

    List<ProductInEntity> getAllProductIn() throws SQLException;
    
    List<ProductInEntity> getAllProductIn(String searchValue, Date date, VendorEntity vendor) throws SQLException;

    void updateProductIn(ProductInEntity productIn) throws SQLException;

    void deleteProductIn(Integer id) throws SQLException;
}
