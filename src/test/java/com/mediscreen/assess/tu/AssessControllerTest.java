package com.mediscreen.assess.tu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.assess.model.Assess;
import com.mediscreen.assess.service.AssessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
        assess = new Assess();
        assess.setPatientId(patientIdConst);
        assess.setPatientFirstName(patientFirstNameConst);
        assess.setPatientLastName(patientLastNameConst);
        assess.setPatientAge(patientAgeConst);
        assess.setDiabetsAssessmentId(diabetsAssessmentIdConst);
        assess.setDiabetsAssessmentValue(diabetsAssessmentValueConst);

    }
    /*---------------------------  Get Assess by patient id  note -----------------------------*/
    @Test
    public void getAssessByPatientId_existingPatientIdSend_assessIsReturn() throws Exception {
        //Given
        Mockito.when(assessService.getAssessByPatientId(anyLong())).thenReturn(assess);


        //WHEN THEN
        mockMvc.perform(get("/assess/id?id="+patientIdConst))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientFirstName").value(patientFirstNameConst))
                .andExpect(jsonPath("$.patientLastName").value(patientLastNameConst))
                .andExpect(jsonPath("$.patientAge").value(patientAgeConst))
                .andExpect(jsonPath("$.diabetsAssessmentId").value(diabetsAssessmentIdConst))
                .andExpect(jsonPath("$.diabetsAssessmentValue").value(diabetsAssessmentValueConst));

    }
    @Test
    public void getAssessByPatientId_inexistingPatientIdSend_errorIsReturn() throws Exception {
        //Given
        Mockito.when(assessService.getAssessByPatientId(anyLong())).thenReturn(null);


        //WHEN THEN
        mockMvc.perform(get("/assess/id?id="+inexistingPatientIdConst))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    /*---------------------------  Get Assess by patient id  note -----------------------------*/
    @Test
    public void getAssessByFamilyName_existingPatientFamilyNameSend_assessIsReturn() throws Exception {
        //Given
        List<Assess> assessList = new ArrayList<>();
        assessList.add(assess);
        Mockito.when(assessService.getAssessByFamilyName(anyString())).thenReturn(assessList);

        //WHEN THEN
        mockMvc.perform(get("/assess/familyName?familyName="+existingFamilyNameConst))
                .andDo(print())
                .andExpect(status().isOk());
        //todo .andExpect(jsonPath("$.data.roles.length()").value(correctRoles.size()));
                /*.andExpect(jsonPath("$.patientFirstName").value(patientFirstNameConst))
                .andExpect(jsonPath("$.patientLastName").value(patientLastNameConst))
                .andExpect(jsonPath("$.patientAge").value(patientAgeConst))
                .andExpect(jsonPath("$.diabetsAssessmentId").value(diabetsAssessmentIdConst))
                .andExpect(jsonPath("$.diabetsAssessmentValue").value(diabetsAssessmentValueConst));*/

    }
    @Test
    public void getAssessByFamilyName_inexistingPatientFamilyNameSend_errorIsReturn() throws Exception {
        //Given
        Mockito.when(assessService.getAssessByFamilyName(anyString())).thenReturn(null);

        //WHEN THEN
        mockMvc.perform(get("/assess/familyName?familyName="+inexistingFamilyNameConst))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAssessByFamilyName_emptyPatientFamilyNameSend_errorIsReturn() throws Exception {
        //Given
        Mockito.when(assessService.getAssessByFamilyName(anyString())).thenReturn(null);

        //WHEN THEN
        mockMvc.perform(get("/assess/familyName?familyName="))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    /*------------------------ utility ---------------------------------*/
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
