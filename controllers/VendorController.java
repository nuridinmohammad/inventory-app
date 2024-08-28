package com.multibahana.inventoryapp.controllers;

import com.multibahana.inventoryapp.dao.VendorDAO;
import com.multibahana.inventoryapp.entities.VendorEntity;

import java.sql.SQLException;
import java.util.List;

public class VendorController {

    private final VendorDAO vendorDAO;

    public VendorController(VendorDAO vendorDAO) {
        this.vendorDAO = vendorDAO;
    }

    public void addVendor(VendorEntity vendor) {
        try {
            vendorDAO.addVendor(vendor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VendorEntity getVendorById(Integer id) {
        try {
            return vendorDAO.getVendorById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VendorEntity> getAllVendors() {
        try {
            return vendorDAO.getAllVendors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateVendor(VendorEntity vendor) {
        try {
            vendorDAO.updateVendor(vendor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVendor(Integer id) {
        try {
            vendorDAO.deleteVendor(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
