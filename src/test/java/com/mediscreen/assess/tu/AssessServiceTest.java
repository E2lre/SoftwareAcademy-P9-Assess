package com.mediscreen.assess.tu;

import com.mediscreen.assess.model.Assess;
import com.mediscreen.assess.service.AssessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AssessServiceTest {

    @Autowired
    private AssessService assessService;

    private long patientIdConst = 1l;
    private String patientFirstNameConst = "James";
    private String patientLastNameConst = "Bond";
    private int patientAgeConst = 34; //dans Casino Royale, mais 37 dans Moonraker ... va comprendre
    private int diabetsAssessmentIdConst = 1;
    private String diabetsAssessmentValueConst = "None";
    private String existingFamilyNameConst = patientLastNameConst;
    private long inexistingPatientIdConst = 0l;
    private String inexistingFamilyNameConst = "Scaramanga";

    private Assess assess;

    @BeforeEach
    public void setUpEach() {
        assess = new Assess(patientIdConst,patientFirstNameConst,patientLastNameConst,patientAgeConst,diabetsAssessmentIdConst,diabetsAssessmentValueConst);

    }
/*--------------------------------- getAssessByPatientId --------------------------*/
    @Test
    public void getAssessByPatientId_existingPatientIdSend_assessIsReturn(){
        //GIVEN
       // Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAgeConst);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentIdConst);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValueConst);

    }
    @Test
    public void getAssessByPatientId_inexistingPatientIdSend_nullIsReturn(){
        //GIVEN
        // Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(inexistingPatientIdConst);
        //THEN
        assertThat(assessResult).isNull();
    }

    /*--------------------------------- getAssessByFamilyName --------------------------*/
    @Test
    public void getAssessByFamilyName_existingPatientFamilyNameSend_assessIsReturn(){
        //GIVEN
        // Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);

        //WHEN
        List<Assess> assessResultList =  assessService.getAssessByFamilyName(existingFamilyNameConst);
        //THEN
        assertThat(assessResultList).isNotNull();
        assertThat(assessResultList).size().isEqualTo(1);
    }
    @Test
    public void getAssessByFamilyName_inexistingPatientFamilyNameSend_nullIsReturn(){
        //GIVEN
        // Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);

        //WHEN
        List<Assess> assessResultList =  assessService.getAssessByFamilyName(inexistingFamilyNameConst);
        //THEN
        assertThat(assessResultList).isNull();
    }
}
