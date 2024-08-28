package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.VendorEntity;
import java.sql.SQLException;
import java.util.List;

public interface VendorDAO {

    void addVendor(VendorEntity vendor) throws SQLException;

    VendorEntity getVendorById(Integer id) throws SQLException;

    List<VendorEntity> getAllVendors() throws SQLException;
    
    List<VendorEntity> getAllVendors(String name, String address) throws SQLException;

    void updateVendor(VendorEntity vendor) throws SQLException;

    void deleteVendor(Integer id) throws SQLException;
}
