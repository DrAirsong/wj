package com.evan.wj.forecast;

import com.evan.wj.pojo.BluetoothInfo;
import com.evan.wj.service.BluetoothInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RiskPrediction {

    @Autowired
    BluetoothInfoService bluetoothInfoService;

    static double[][] risktable= {
            {0,0.35,0.57,0.72,0.82,0.88,0.92,0.95,0.96,0.97,0.98},
            {0,0.35,0.57,0.72,0.82,0.88,0.92,0.95,0.96,0.97,0.98},
            {0,0.3,0.49,0.65,0.76,0.83,0.88,0.91,0.94,0.95,0.97},
            {0,0.3,0.49,0.65,0.76,0.83,0.88,0.91,0.94,0.95,0.97},
            {0,0.25,0.43,0.57,0.68,0.76,0.82,0.86,0.9,0.92,0.94},
            {0,0.25,0.43,0.57,0.68,0.76,0.82,0.86,0.9,0.92,0.94},
            {0,0.2,0.36,0.49,0.59,0.67,0.73,0.83,0.86,0.89,0.91},
            {0,0.2,0.36,0.49,0.59,0.67,0.73,0.83,0.86,0.89,0.91},
            {0,0.15,0.27,0.38,0.47,0.55,0.62,0.68,0.72,0.76,0.8},
            {0,0.15,0.27,0.38,0.47,0.55,0.62,0.68,0.72,0.76,0.8},
            {0,0.1,0.19,0.27,0.34,0.41,0.46,0.52,0.57,0.61,0.65},
            {0,0.1,0.19,0.27,0.34,0.41,0.46,0.52,0.57,0.61,0.65},
            {0,0.05,0.09,0.14,0.18,0.23,0.27,0.30,0.33,0.37,0.40},
            {0,0.05,0.09,0.14,0.18,0.23,0.27,0.30,0.33,0.37,0.40}
    };

    private static final String URL="jdbc:mysql://39.97.163.234:3306/white_jotter";
    private static final String NAME="root";
    private static final String PASSWORD="123456";

    /*public static void main(String[] args) throws Exception{
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库的连接
        Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String Connect_date,Diff,louserid,self_mac,connect_mac,loconnect_time;
        Patient patient = new Patient();
        Bluetoothinfo user = new Bluetoothinfo("11","test");//user 为申请预测的用户，需要他的id和selfmac


        ResultSet cloudco;
        String sql = "SELECT b.userid,b.connect_date,b.connect_time,b.self_mac,b.connect_mac from bluetoothinfo b JOIN user_account u ON b.userid = u.userid where u.health = 3 and b.connect_mac = 'test'";
        cloudco = stmt.executeQuery(sql);
        while(cloudco.next())
        {
            Connect_date = cloudco.getString("connect_date");
            louserid = cloudco.getString("userid");
            loconnect_time = cloudco.getString("connect_time");
            self_mac = cloudco.getString("self_mac");
            connect_mac  = cloudco.getString("connect_mac");
            patient.add(new Bluetoothinfo(louserid,self_mac,connect_mac,Connect_date,loconnect_time));  //patient为Bluetoothinfo类的数组，用来存储select的结果
        }
        patient.show();
        user.riskpredict(patient,user,risktable);
    }*/

    public double foreCast(String userid, String selfmac,List<BluetoothInfo> bluetoothInfoList ){
        double risk;
        String Connect_date,Diff,louserid,self_mac,connect_mac,loconnect_time;
        Patient patient = new Patient();
        Bluetoothinfo user = new Bluetoothinfo(userid,selfmac);//user 为申请预测的用户，需要他的id和selfmac

        for(int i=0; i<bluetoothInfoList.size(); i++){
            BluetoothInfo patientBluetoothInfo = bluetoothInfoList.get(i);

            louserid = patientBluetoothInfo.getUserid().toString();
            self_mac = patientBluetoothInfo.getSelf_mac();
            connect_mac = patientBluetoothInfo.getConnect_mac();
            Connect_date = patientBluetoothInfo.getConnect_date();
            loconnect_time = patientBluetoothInfo.getConnect_time();

            patient.add(new Bluetoothinfo(louserid,self_mac,connect_mac,Connect_date,loconnect_time));  //patient为Bluetoothinfo类的数组，用来存储select的结果
        }

        risk = user.riskpredict(patient,user,risktable);
        return risk;
    }
}

class Bluetoothinfo{
    String userid;
    String selfmac;
    String connectmac;
    String connectdate;
    int connecttime;

    public Bluetoothinfo(String userid, String selfmac, String connectmac, String connectdate, String connecttime) {
        this.userid = userid;
        this.selfmac = selfmac;
        this.connectmac = connectmac;
        this.connectdate = connectdate;
        this.connecttime = Integer.parseInt(connecttime);
    }

    public Bluetoothinfo(String userid, String selfmac, String connectmac, String connectdate, int connecttime) {
        this.userid = userid;
        this.selfmac = selfmac;
        this.connectmac = connectmac;
        this.connectdate = connectdate;
        this.connecttime = connecttime;
    }

    public Bluetoothinfo(String userid, String selfmac) {
        this.userid = userid;
        this.selfmac = selfmac;
    }

    public String getConnectmac() {
        return connectmac;
    }

    public void setConnectmac(String connectmac) {
        this.connectmac = connectmac;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getConnectdate() {
        return connectdate;
    }

    public void setConnectdate(String connectdate) {
        this.connectdate = connectdate;
    }


    public int getConnecttime() {
        return connecttime;
    }

    public void setConnecttime(int connecttime) {
        this.connecttime = connecttime;
    }


    public String getSelfmac() {
        return selfmac;
    }

    public void setSelfmac(String selfmac) {
        this.selfmac = selfmac;
    }


    public static long DateStringToLong(String inVal) {
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = inputFormat.parse(inVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public double riskpredict(Patient patient,Bluetoothinfo user,double[][] risktable)
    {
        double risk = 1;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long now = DateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        long record;
        Date re;
        int diff,time;
        for(int i = 0;i < patient.count ; i++)
        {
            record = DateStringToLong(patient.bluetoothinfos[i].connectdate);
            diff = (int) ((now - record) / (1000 * 60 * 60 * 24));
            if ((patient.bluetoothinfos[i].connectmac.equals( user.selfmac))&&(diff < 14)&&(patient.bluetoothinfos[i].connecttime >= 60))
            {
                time = patient.bluetoothinfos[i].connecttime/60;
                if (time > 10) time = 10;
                risk *= risktable[diff][time];
            }
        }

        if (risk == 1.0)risk = 0;
        System.out.println(risk);
        return risk;
    }

}

class Patient {
    Bluetoothinfo[] bluetoothinfos = new Bluetoothinfo[10];
    int count = 0;

    public void add(Bluetoothinfo m) {
// 当数组空间不足，对数组长度进行动态扩展
        if (count >= bluetoothinfos.length) {
// 新数组的长度（当前数组长度的一半左右）
            int newLen = (bluetoothinfos.length * 3) / 2 + 1; // 一定要注意这里的分析
            bluetoothinfos = Arrays.copyOf(bluetoothinfos, newLen);
        }
        bluetoothinfos[count] = m; // 添加进数组
        count++;// 每添加一个对象，count自增1
    }

    public void show(){

        for(int i = 0;i<count;i++){
            System.out.println(bluetoothinfos[i].userid+"  "+bluetoothinfos[i].selfmac+"  "+bluetoothinfos[i].connectmac+"  "+bluetoothinfos[i].connectdate + "  " + bluetoothinfos[i].connecttime);
        }
    }
}

