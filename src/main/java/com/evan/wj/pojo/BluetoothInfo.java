package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "bluetoothinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@IdClass(com.evan.wj.pojo.UseridAndBluetooth.class)
public class BluetoothInfo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // @ManyToOne
    @Column(name = "userid")
    Integer userid;

    @Id
    @Column(name = "connect_date")
    String connect_date;

    String connect_time;
    String self_mac;
    String connect_mac;

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

    public String getConnect_time() {
        return connect_time;
    }

    public void setConnect_time(String connect_time) {
        this.connect_time = connect_time;
    }

    public String getSelf_mac() {
        return self_mac;
    }

    public void setSelf_mac(String self_mac) {
        this.self_mac = self_mac;
    }

    public String getConnect_mac() {
        return connect_mac;
    }

    public void setConnect_mac(String connect_mac) {
        this.connect_mac = connect_mac;
    }
}
