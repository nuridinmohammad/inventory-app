package com.multibahana.inventoryapp.entities;

import java.util.Date;

public class StockEntity {

    private Integer id;
    private String noEvidence;
    private String categoryName;
    private String productName;
    private Date dateReceipt;
    private Integer amount;
    private Integer productId;
    private Integer vendorId;

    public StockEntity(Integer id, String noEvidence, Date dateReceipt, Integer amount, Integer productId, Integer vendorId) {
        this.id = id;
        this.noEvidence = noEvidence;
        this.dateReceipt = dateReceipt;
        this.amount = amount;
        this.productId = productId;
        this.vendorId = vendorId;
    }

    public StockEntity(String noEvidence, Date dateReceipt, Integer amount, Integer productId, Integer vendorId) {
        this.noEvidence = noEvidence;
        this.dateReceipt = dateReceipt;
        this.amount = amount;
        this.productId = productId;
        this.vendorId = vendorId;
    }

    public StockEntity(Integer id, String noEvidence, Date dateReceipt) {
        this.id = id;
        this.noEvidence = noEvidence;
        this.dateReceipt = dateReceipt;
    }

    public StockEntity(Integer productId, String productName, Integer totalAmount, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.amount = totalAmount;
        this.categoryName = categoryName;
    }

    public StockEntity() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoEvidence() {
        return noEvidence;
    }

    public void setNoEvidence(String noEvidence) {
        this.noEvidence = noEvidence;
    }

    public Date getDateReceipt() {
        return dateReceipt;
    }

    public void setDateReceipt(Date dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return noEvidence;
    }
}
