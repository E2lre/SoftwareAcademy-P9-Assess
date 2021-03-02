package com.mediscreen.assess.service;

import com.mediscreen.assess.model.Assess;

import java.util.List;

public interface AssessService {
    public Assess getAssessByPatientId(long patientId);
    public List<Assess> getAssessByFamilyName(String famillyName);
 }
