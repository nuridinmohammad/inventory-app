package com.multibahana.inventoryapp.entities;

import java.util.Date;

/**
 * Represents a product-in entity.
 * 
 * @author mohammadnuridin
 */
public class ProductInEntity {

    private Integer id;
    private Integer productId;
    private Integer vendorId;
    private Integer evidenceId;
    private Date date;
    private Integer quantity;

    public ProductInEntity(Integer id, Integer productId, Integer vendorId, Integer evidenceId, Date date, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.vendorId = vendorId;
        this.evidenceId = evidenceId;
        this.date = date;
        this.quantity = quantity;
    }

    public ProductInEntity(Integer productId, Integer vendorId, Integer evidenceId, Date date, Integer quantity) {
        this.productId = productId;
        this.vendorId = vendorId;
        this.evidenceId = evidenceId;
        this.date = date;
        this.quantity = quantity;
    }

    public ProductInEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return productId.toString();
    }
}
