package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "recoveryinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class RecoveryInfo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    Integer userid;

    String recovery_time;
    String recovery_location;
    String recovery_hospital;
    String recovery_case;
    String audit_status = "待审核";
    String audit_mind = null;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRecovery_time() {
        return recovery_time;
    }

    public void setRecovery_time(String recovery_time) {
        this.recovery_time = recovery_time;
    }

    public String getRecovery_location() {
        return recovery_location;
    }

    public void setRecovery_location(String recovery_location) {
        this.recovery_location = recovery_location;
    }

    public String getRecovery_hospital() {
        return recovery_hospital;
    }

    public void setRecovery_hospital(String recovery_hospital) {
        this.recovery_hospital = recovery_hospital;
    }

    public String getRecovery_case() {
        return recovery_case;
    }

    public void setRecovery_case(String recovery_case) {
        this.recovery_case = recovery_case;
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
