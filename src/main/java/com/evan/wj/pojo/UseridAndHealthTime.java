package com.evan.wj.pojo;

import java.io.Serializable;

/*
* 这是一个复合主键类
* 用于useraccount表和healthinfo表
* */
public class UseridAndHealthTime implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userid;
    private String time;


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
}
