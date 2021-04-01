package com.mediscreen.assess.service;

import com.mediscreen.assess.model.Assess;
import com.mediscreen.assess.model.external.Patient;
import com.mediscreen.assess.proxies.NotesProxy;
import com.mediscreen.assess.proxies.PatientProxy;
import com.mediscreen.assess.proxies.exception.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AssessServiceImpl implements AssessService{
    private static final Logger logger = LogManager.getLogger(AssessServiceImpl.class);
    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private NotesProxy notesProxy ;

    private List triggersConst = Arrays.asList("Hémoglobine A1C","Microalbumine","Taille","Poids","Fumeur","Anormal", "Cholestérol","Vertige","Rechute","Réaction","Anticorps");

    @Override
    public Assess getAssessByPatientId(long patientId) {

        Assess result = null;
        Patient patient =null;

        try {
            patient = patientProxy.getPatientById(patientId);
        } catch (FeignException e) {
            logger.error( "error on patient proxy : " +e.getMessage());
            return null;
        }
        if (patient != null) {
            result = scoring(patient,triggersConst);

            }
        return result;
   }

    @Override
    public List<Assess> getAssessByFamilyName(String famillyName) {
        List<Assess> assessListResult = new ArrayList<>();
        List<Patient> patientList ;

        try {
             patientList = patientProxy.getPatientByFamilyName (famillyName);
        } catch (FeignException e) {
            logger.error( "error on patient proxy : " +e.getMessage());
            return assessListResult;
        }

        if (patientList != null)  {
            for (Patient patient : patientList) {
                assessListResult.add(scoring(patient, triggersConst));
            }
        }
        logger.info("test");
        return assessListResult;
    }

    private Assess scoring (Patient patient, List<String> triggers) {
        Assess assessResult = new Assess();

        int score = notesProxy.getScoreByPatientIdAndTriggers(patient.getId(), triggersConst);

        assessResult.setDiabetsAssessmentId(score);

        assessResult.setPatientFirstName(patient.getFirstName());
        assessResult.setPatientLastName(patient.getLastName());
        assessResult.setPatientId(patient.getId());
        assessResult.setPatientAge(Period.between(patient.getBirthdate(), LocalDate.now()).getYears());

        assessResult.setDiabetsAssessmentValue("None");
        List<Integer> triggerLimit = new ArrayList<>();

        if (assessResult.getPatientAge() >= 30) {
            triggerLimit.add(7); //Early onset
            triggerLimit.add(5); //In danger
            triggerLimit.add(1); //Borderline
        } else {
            if ((patient.getSex() == "M") || (patient.getSex() == "m")) {
                triggerLimit.add(4); //Early onset
                triggerLimit.add(2); //In danger
                triggerLimit.add(99); //Borderline
            } else {
                triggerLimit.add(6); //Early onset
                triggerLimit.add(3); //In danger
                triggerLimit.add(99); //Borderline
            }

        }

        if (assessResult.getDiabetsAssessmentId() > triggerLimit.get(0)) {
            assessResult.setDiabetsAssessmentValue("Early onset");
        } else {
            if (assessResult.getDiabetsAssessmentId() > triggerLimit.get(1)) {
                assessResult.setDiabetsAssessmentValue("In danger");
            } else {
                if (assessResult.getDiabetsAssessmentId() > triggerLimit.get(2)) {
                    assessResult.setDiabetsAssessmentValue("Borderline");
                }
            }
        }
        return assessResult;
    }
}
