package com.mediscreen.assess.service;

import com.mediscreen.assess.model.Assess;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessServiceImpl implements AssessService{
    @Override
    public Assess getAssessByPatientId(long patientId) {
        return null;
    }

    @Override
    public List<Assess> getAssessByFamilyName(String famillyName) {
        return null;
    }
}
