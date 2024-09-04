package com.multibahana.inventoryapp.entities;

/**
 * Represents an evidence entity.
 *
 * @author mohammadnuridin
 */
public class EvidenceEntity {

    private Integer id;
    private String code;

    public EvidenceEntity(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public EvidenceEntity(String code) {
        this.code = code;
    }

    public EvidenceEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
