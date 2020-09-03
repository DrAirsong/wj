package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "patientinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PatientInfo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    Integer userid;

    String diagnosis_time;
    String diagnosis_location;
    String diagnosis_hospital;
    String diagnosis_case;
    String audit_status = "待审核";
    String audit_mind = null;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDiagnosis_time() {
        return diagnosis_time;
    }

    public void setDiagnosis_time(String diagnosis_time) {
        this.diagnosis_time = diagnosis_time;
    }

    public String getDiagnosis_location() {
        return diagnosis_location;
    }

    public void setDiagnosis_location(String diagnosis_location) {
        this.diagnosis_location = diagnosis_location;
    }

    public String getDiagnosis_hospital() {
        return diagnosis_hospital;
    }

    public void setDiagnosis_hospital(String diagnosis_hospital) {
        this.diagnosis_hospital = diagnosis_hospital;
    }

    public String getDiagnosis_case() {
        return diagnosis_case;
    }

    public void setDiagnosis_case(String diagnosis_case) {
        this.diagnosis_case = diagnosis_case;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getAudit_mind() {
        return audit_mind;
    }

    public void setAudit_mind(String audit_mind) {
        this.audit_mind = audit_mind;
    }

}
