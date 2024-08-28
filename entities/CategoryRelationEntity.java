/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.entities;

/**
 *
 * @author mohammadnuridin
 */
public class CategoryRelationEntity {

    private Integer ancestorId;
    private Integer descendantId;
    private String name;

    public CategoryRelationEntity(Integer ancestorId, Integer descendantId, String name) {
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(int ancestorId) {
        this.ancestorId = ancestorId;
    }

    public int getDescendantId() {
        return descendantId;
    }

    public void setDescendantId(int descendantId) {
        this.descendantId = descendantId;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
