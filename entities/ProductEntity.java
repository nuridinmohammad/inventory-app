package com.multibahana.inventoryapp.entities;

/**
 *
 * @author mohammadnuridin
 */
public class ProductEntity {

    private Integer id;
    private String name;
    private Double price;
    private Integer categoryId;

    public ProductEntity(Integer id, String name, Double price, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductEntity(String name, Double price, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductEntity(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return name;
    }
}
