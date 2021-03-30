package com.mediscreen.assess.model;

import com.sun.istack.NotNull;

public class Assess {
    private long patientId;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private int diabetsAssessmentId;
    private String diabetsAssessmentValue;

    public Assess() {
    }

    public Assess(long patientId, String patientFirstName, String patientLastName, int patientAge, int diabetsAssessmentId, String diabetsAssessmentValue) {
        this.patientId = patientId;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAge = patientAge;
        this.diabetsAssessmentId = diabetsAssessmentId;
        this.diabetsAssessmentValue = diabetsAssessmentValue;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public int getDiabetsAssessmentId() {
        return diabetsAssessmentId;
    }

    public void setDiabetsAssessmentId(int diabetsAssessmentId) {
        this.diabetsAssessmentId = diabetsAssessmentId;
    }

    public String getDiabetsAssessmentValue() {
        return diabetsAssessmentValue;
    }

    public void setDiabetsAssessmentValue(String diabetsAssessmentValue) {
        this.diabetsAssessmentValue = diabetsAssessmentValue;
    }

/*    @Override
    public String toString() {
        return "Assess{" +
                "patientId=" + patientId +
                ", patientFirstName='" + patientFirstName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", patientAge=" + patientAge +
                ", diabetsAssessmentId=" + diabetsAssessmentId +
                ", diabetsAssessmentValue='" + diabetsAssessmentValue + '\'' +
                '}';
    }*/
}
