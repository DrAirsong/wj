package com.evan.wj.pojo;

import java.io.Serializable;

public class UseridAndBluetooth implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userid;
    private String connect_date;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getConnect_date() {
        return connect_date;
    }

    public void setConnect_date(String connect_date) {
        this.connect_date = connect_date;
    }
}
