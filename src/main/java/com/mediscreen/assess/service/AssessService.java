package com.mediscreen.assess.service;

import com.mediscreen.assess.model.Assess;
import java.util.List;

public interface AssessService {
    /**
     * Give assess for a patient
     * @param patientId id of the patient
     * @return assess for the patient
     */
    public Assess getAssessByPatientId(long patientId);

    /**
     * Give a list of assess for all patient with the same family name
     * @param famillyName family name
     * @return list of assess
     */
    public List<Assess> getAssessByFamilyName(String famillyName);
 }
