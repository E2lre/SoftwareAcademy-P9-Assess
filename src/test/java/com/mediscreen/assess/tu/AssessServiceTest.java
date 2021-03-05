package com.mediscreen.assess.tu;

import com.mediscreen.assess.model.Assess;
import com.mediscreen.assess.model.external.Note;
import com.mediscreen.assess.model.external.Patient;
import com.mediscreen.assess.proxies.NotesProxy;
import com.mediscreen.assess.proxies.PatientProxy;
import com.mediscreen.assess.service.AssessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AssessServiceTest {

    @Autowired
    private AssessService assessService;

    @MockBean
    private PatientProxy patientProxy;

    @MockBean
    private NotesProxy notesProxy;

    private long patientIdConst = 1l;
    private String patientFirstNameConst = "James";
    private String patientLastNameConst = "Bond";
    private int patientAgeConst = 34; //dans Casino Royale, mais 37 dans Moonraker ... va comprendre
    private int patientAge =0;
    private LocalDate patientBirthdateLess30;
    private LocalDate patientBirthdateMore30;
    private LocalDate patientBirthdate30;
    private String patientAddress = "10 Downing Street";
    private String patientPhone = "007-007-007";
    private String patientSexM = "M";
    private String patientSexF = "F";
    private int diabetsAssessmentIdConst = 1;
    private int diabetsAssessmentId;
    private String diabetsAssessmentValueConst = "None";
    private String diabetsAssessmentValue;
    private String existingFamilyNameConst = patientLastNameConst;
    private long inexistingPatientIdConst = 0l;
    private String inexistingFamilyNameConst = "Scaramanga";
    private int score;

    private Assess assess;
    private Patient patient;
    private Note note;

    @BeforeEach
    public void setUpEach() {
        assess = new Assess(patientIdConst,patientFirstNameConst,patientLastNameConst,patientAgeConst,diabetsAssessmentIdConst,diabetsAssessmentValueConst);
        patient = new Patient(patientIdConst,patientFirstNameConst,patientLastNameConst,patientBirthdateMore30,patientSexM,patientAddress,patientPhone);
        patientBirthdate30 = LocalDate.now().minusYears(30);
        patientBirthdateLess30 = LocalDate.now().minusYears(29);
        patientBirthdateMore30 = LocalDate.now().minusYears(31);
        diabetsAssessmentId = diabetsAssessmentIdConst;
        diabetsAssessmentValue = diabetsAssessmentValueConst;
    }
/*--------------------------------- getAssessByPatientId --------------------------*/
@Test
public void getAssessByPatientId_inexistingPatientIdSend_nullIsReturn(){
    //GIVEN
    Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(null);

    //WHEN
    Assess assessResult =  assessService.getAssessByPatientId(inexistingPatientIdConst);
    //THEN
    assertThat(assessResult).isNull();
}
/*---None---*/
    @Test
    public void getAssessByPatientId_existingPatientIdManNoRisk_NoneIsReturn(){
        //GIVEN
        diabetsAssessmentId = 0;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexM);
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValueConst);

    }

    @Test
    public void getAssessByPatientId_existingPatientIdWomanOneRisk_NoneIsReturn(){
        //GIVEN
        diabetsAssessmentId = 1;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexF);
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValueConst);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdMan2RiskLess30_NoneIsReturn(){
        //GIVEN
        diabetsAssessmentId = 2;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "None";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman2RiskLess30_NoneIsReturn(){
        //GIVEN
        diabetsAssessmentId = 2;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "None";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    /*---Borderline---*/
    @Test
    public void getAssessByPatientId_existingPatientIdMan2RiskMore30_BorderlineIsReturn(){
        //GIVEN
        diabetsAssessmentId = 2;
        patient.setBirthdate(patientBirthdateMore30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "Borderline";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman2RiskMore30_BorderlineIsReturn(){
        //GIVEN
        diabetsAssessmentId = 2;
        patient.setBirthdate(patientBirthdateMore30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "Borderline";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdMan2Risk30_BorderlineIsReturn(){
        //GIVEN
        diabetsAssessmentId = 2;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "Borderline";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman2Risk30_BorderlineIsReturn(){
        //GIVEN
        diabetsAssessmentId = 3;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "Borderline";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman3Risk30_BorderlineIsReturn(){
        //GIVEN
        diabetsAssessmentId = 3;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "Borderline";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    /*---In Danger---*/
    @Test
    public void getAssessByPatientId_existingPatientIdMan3RiskLess30_InDangerIsReturn(){
        //GIVEN
        diabetsAssessmentId = 3;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "In danger";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman4RiskLess30_InDangerIsReturn(){
        //GIVEN
        diabetsAssessmentId = 4;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "In danger";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdMan6Riskmore30_InDangerIsReturn(){
        //GIVEN
        diabetsAssessmentId = 6;
        patient.setBirthdate(patientBirthdateMore30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "In danger";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman6Risk30_InDangerIsReturn(){
        //GIVEN
        diabetsAssessmentId = 6;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "In danger";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }

    /*---Early onset---*/
    @Test
    public void getAssessByPatientId_existingPatientIdMan5RiskLess30_EarlyOnsetIsReturn(){
        //GIVEN
        diabetsAssessmentId = 5;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "Early onset";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman7RiskLess30_EarlyOnsetIsReturn(){
        //GIVEN
        diabetsAssessmentId = 7;
        patient.setBirthdate(patientBirthdateLess30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "Early onset";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdMan8RiskMore30_EarlyOnsetIsReturn(){
        //GIVEN
        diabetsAssessmentId = 8;
        patient.setBirthdate(patientBirthdateMore30);
        patient.setSex(patientSexM);
        diabetsAssessmentValue = "Early onset";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    @Test
    public void getAssessByPatientId_existingPatientIdWoman8Risk30_EarlyOnsetIsReturn(){
        //GIVEN
        diabetsAssessmentId = 9;
        patient.setBirthdate(patientBirthdate30);
        patient.setSex(patientSexF);
        diabetsAssessmentValue = "Early onset";
        patientAge = Period.between(patient.getBirthdate(),LocalDate.now()).getYears();
        Mockito.when(patientProxy.getPatientById((anyLong()))).thenReturn(patient);
        Mockito.when(notesProxy.getScoreByPatientIdAndTriggers(anyLong(),anyList())).thenReturn(diabetsAssessmentId);

        //WHEN
        Assess assessResult =  assessService.getAssessByPatientId(patientIdConst);
        //THEN
        assertThat(assessResult).isNotNull();
        assertThat(assessResult.getPatientLastName()).isEqualTo(patientLastNameConst);
        assertThat(assessResult.getPatientFirstName()).isEqualTo(patientFirstNameConst);
        assertThat(assessResult.getPatientAge()).isEqualTo(patientAge);
        assertThat(assessResult.getDiabetsAssessmentId()).isEqualTo(diabetsAssessmentId);
        assertThat(assessResult.getDiabetsAssessmentValue()).isEqualTo(diabetsAssessmentValue);

    }
    /*--------------------------------- getAssessByFamilyName --------------------------*/
    //@Test
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
