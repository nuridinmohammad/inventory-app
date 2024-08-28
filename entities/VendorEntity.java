package com.multibahana.inventoryapp.entities;

public class VendorEntity {
    
    private Integer id;
    private String name;
    private String address;

    public VendorEntity(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public VendorEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public VendorEntity(Integer id) {
        this.id = id;
    }

    public VendorEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public VendorEntity() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }
}
