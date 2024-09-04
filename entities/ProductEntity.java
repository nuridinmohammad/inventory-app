package com.multibahana.inventoryapp.entities;

/**
 * Represents a product entity.
 *
 * @author mohammadnuridin
 */
public class ProductEntity {

    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer categoryId;
    private String productCode;
    private Double sellPrice; 

    public ProductEntity(Integer id, String name, Double price, Integer stock, Integer categoryId, String productCode, Double sellPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.productCode = productCode;
        this.sellPrice = sellPrice;
    }

    public ProductEntity(String name, Double price, Integer stock, Integer categoryId, String productCode, Double sellPrice) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
        this.productCode = productCode;
        this.sellPrice = sellPrice;
    }

    public ProductEntity(Integer id, String name, Double price, Integer stock, Double sellPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellPrice = sellPrice;
    }

    public ProductEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return name;
    }
}
