package com.multibahana.inventoryapp.dao;

import com.multibahana.inventoryapp.entities.EvidenceEntity;

import java.sql.SQLException;
import java.util.List;

public interface EvidenceDAO {

    Integer addEvidence(EvidenceEntity evidence) throws SQLException;

    EvidenceEntity getEvidenceById(Integer id) throws SQLException;

    List<EvidenceEntity> getAllEvidences() throws SQLException;

    void updateEvidence(EvidenceEntity evidence) throws SQLException;

    void deleteEvidence(Integer id) throws SQLException;
}
