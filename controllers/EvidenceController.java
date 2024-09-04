package com.multibahana.inventoryapp.controller;

import com.multibahana.inventoryapp.dao.EvidenceDAO;
import com.multibahana.inventoryapp.entities.EvidenceEntity;
import java.sql.SQLException;
import java.util.List;

public class EvidenceController {

    private EvidenceDAO evidenceDAO;

    public EvidenceController(EvidenceDAO evidenceDAO) {
        this.evidenceDAO = evidenceDAO;
    }

    public Integer addEvidence(EvidenceEntity evidence) {
        try {
            return evidenceDAO.addEvidence(evidence);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public EvidenceEntity getEvidenceById(Integer id) {
        try {
            return evidenceDAO.getEvidenceById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EvidenceEntity> getAllEvidences() {
        try {
            return evidenceDAO.getAllEvidences();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateEvidence(EvidenceEntity evidence) {
        try {
            evidenceDAO.updateEvidence(evidence);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvidence(Integer id) {
        try {
            evidenceDAO.deleteEvidence(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
