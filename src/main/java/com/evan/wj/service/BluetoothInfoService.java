package com.evan.wj.service;

import com.evan.wj.dao.BluetoothInfoDAO;
import com.evan.wj.dao.UserAccountDAO;
import com.evan.wj.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class BluetoothInfoService {
    @Autowired
    BluetoothInfoDAO bluetoothInfoDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    UserAccountService userAccountService;

    //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
    //可以改用Sort.by获得Sort对象
    public List<BluetoothInfo> list(){
        return bluetoothInfoDAO.findAll(Sort.by(Sort.Direction.ASC, "userid"));
    }

    public void addOrUpdate(BluetoothInfo bluetoothInfo){
        bluetoothInfoDAO.save(bluetoothInfo);
    }

    //根据用户id查找记录
    public List<BluetoothInfo> searchByUserid(Integer userid){
        List<BluetoothInfo> list = bluetoothInfoDAO.findAllByUserid(userid);
        return list;
    }

    public void insertOne(BluetoothInfo bluetoothInfo){
        bluetoothInfoDAO.save(bluetoothInfo);
    }

    //根据内容插入一条记录
    public BluetoothInfo insertOne(Integer userid, String connect_date,
                         String connect_time, String self_mac,
                         String connect_mac){
        BluetoothInfo bluetoothInfo = new BluetoothInfo();
        bluetoothInfo.setUserid(userid);
        bluetoothInfo.setConnect_date(connect_date);
        bluetoothInfo.setConnect_time(connect_time);
        bluetoothInfo.setSelf_mac(self_mac);
        bluetoothInfo.setConnect_mac(connect_mac);
        bluetoothInfoDAO.saveAndFlush(bluetoothInfo);
        return bluetoothInfo;
    }



    public void deleteByPK(Integer userid, String connect_date){
        UseridAndBluetooth primaryKey = new UseridAndBluetooth();
        primaryKey.setUserid(userid);
        primaryKey.setConnect_date(connect_date);
        bluetoothInfoDAO.deleteById(primaryKey);
    }

    public Map getMap(BluetoothInfo bluetoothInfo){
        Map<String, String> map = new HashMap<String, String>();
        map.put("connect_date",bluetoothInfo.getConnect_date());
        map.put("connect_time",bluetoothInfo.getConnect_time());
        map.put("self_mac",bluetoothInfo.getSelf_mac());
        map.put("connect_mac",bluetoothInfo.getConnect_mac());
        return map;
    }

    public List<Map> getBroadcastKeys(String date){
        List<UserAccount> userAccountList = userAccountDAO.findAllByHealth("确诊");
        List<Map> mapList =  new ArrayList<Map>();
        for(UserAccount userAccount : userAccountList ){
            int userid = userAccount.getUserid();
            List<BluetoothInfo> bluetoothInfoList1 =  bluetoothInfoDAO.getByUseridAndDate(userid,date);
            for(BluetoothInfo bluetoothInfo : bluetoothInfoList1){
                Map<String, String> map = getMap(bluetoothInfo);
                mapList.add(map);
            }
        }
        return mapList;
    }

    //获得所有确诊患者的蓝牙连接信息
    public List<BluetoothInfo> getPatientBluetoothIfno(String date){
        List<UserAccount> userAccountList = userAccountDAO.findAllByHealth("确诊");
        List<BluetoothInfo> bluetoothInfoList = new ArrayList<>();
        for(UserAccount userAccount : userAccountList){
            int userid = userAccount.getUserid();
            List<BluetoothInfo> bluetoothInfoList1 =  bluetoothInfoDAO.getByUseridAndDate(userid,date);
            bluetoothInfoList.addAll(bluetoothInfoList1);
        }
        return bluetoothInfoList;
    }

    public void setRisk(String userid,double risk){
        UserAccount userAccount = userAccountService.findOne(Integer.valueOf(userid));
        userAccount.setRisk(risk);
        userAccountDAO.saveAndFlush(userAccount);
    }
}