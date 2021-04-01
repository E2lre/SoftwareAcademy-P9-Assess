package com.mediscreen.assess.controller;

import com.mediscreen.assess.controller.exception.AssessIdNotFoundException;
import com.mediscreen.assess.model.Assess;
import com.mediscreen.assess.service.AssessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AssessController {
    private static final Logger logger = LogManager.getLogger(AssessController.class);
    @Autowired
    private AssessService assessService;

    /*---------------------------  GET Asses by patient Id -----------------------------*/

    /**
     * Give a assess for a patient
     * @param patientId patient id
     * @return assess for the patient id
     * @throws AssessIdNotFoundException 404 if patient is not found
     */
    @GetMapping(value = "/assess/id")
    @ResponseStatus(HttpStatus.OK)
    public Assess getAssessByPatientId(@RequestParam(name = "id")  long patientId) throws AssessIdNotFoundException {
        logger.info("getAssessById start "+patientId);
        Assess finalResult = null;
        String errorMessage =null;
        finalResult = assessService.getAssessByPatientId(patientId);
        if (finalResult == null) {
            errorMessage = "No patient id " + patientId;
            logger.error(errorMessage);
            throw new AssessIdNotFoundException(errorMessage);
        }

        logger.info("getAssessById finish");
        return finalResult;
    }

    /*---------------------------  GET Asses by patient family name -----------------------------*/

    /**
     * Give assesses list for a patient list with the same family name
     * @param familyName family name
     * @return list of assess
     * @throws AssessIdNotFoundException 404 if no body with family name
     */
    @GetMapping(value = "/assess/familyName")
    @ResponseStatus(HttpStatus.OK)
    public List<Assess> getAssessByFamilyName(@RequestParam(name = "familyName")  String familyName) throws AssessIdNotFoundException {
        logger.info("getAssessByFamilyName start " +familyName);
        List<Assess> finalResultList = null;
        String errorMessage =null;
        if (!familyName.isEmpty()) {
            finalResultList = assessService.getAssessByFamilyName(familyName);
            if (finalResultList == null) {
                errorMessage = "No Assess for patient  " + familyName;
                logger.error(errorMessage);
                throw new AssessIdNotFoundException(errorMessage);
            }
        } else {
            errorMessage = "familyName is Empty " ;
            logger.error(errorMessage);
            throw new AssessIdNotFoundException(errorMessage);

        }

        logger.info("getAssessByFamilyName finish");
        return finalResultList;
    }
}
