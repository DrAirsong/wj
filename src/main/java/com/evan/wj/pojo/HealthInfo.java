package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "healthinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@IdClass(com.evan.wj.pojo.UseridAndHealthTime.class)
public class HealthInfo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@ManyToOne
    @Column(name = "userid")
    Integer userid;

    @Id
    @Column(name = "time")
    String time;

    String action;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
