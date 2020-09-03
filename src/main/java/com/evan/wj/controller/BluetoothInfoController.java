package com.evan.wj.controller;

import com.evan.wj.pojo.BluetoothInfo;
import com.evan.wj.service.BluetoothInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.evan.wj.forecast.RiskPrediction;

@RestController
public class BluetoothInfoController {
    @Autowired
    BluetoothInfoService bluetoothInfoService;

    @GetMapping("/api/bluetoothInfo")
    public List<BluetoothInfo> list() throws Exception{
        return bluetoothInfoService.list();
    }

    @PostMapping("/api/bluetoothInfo")
    public BluetoothInfo addOrUpdate(@RequestBody BluetoothInfo bluetoothInfo) throws Exception{
        bluetoothInfoService.addOrUpdate(bluetoothInfo);
        return bluetoothInfo;
    }

    //根据用户id查找对应记录
    @GetMapping("/api/bluetoothInfo/searchByUserid")
    public List<BluetoothInfo> searchByUserid(@RequestParam("userid") String userid) throws Exception {
        if (userid.equals(""))
            return bluetoothInfoService.list();
        Integer userid_ = Integer.valueOf(userid);
        return bluetoothInfoService.searchByUserid(userid_);
    }

    //根据蓝牙信息删除对应的记录
    @PostMapping("/api/bluetoothInfo/delete")
    public void delete(@RequestBody BluetoothInfo bluetoothInfo) throws Exception{
        bluetoothInfoService.deleteByPK(bluetoothInfo.getUserid(), bluetoothInfo.getConnect_date());
    }

/*    @PostMapping("/api/bluetoothInfo/insertOne")
    public BluetoothInfo insertOne(@RequestBody BluetoothInfo bluetoothInfo) throws Exception{
        bluetoothInfoService.insertOne(bluetoothInfo);
        return bluetoothInfo;
    }*/

    //根据属性插入一条记录
    @PostMapping("/api/bluetoothInfo/insertOne")
    public BluetoothInfo insertOne(@RequestParam("userid") String userid,
                                   @RequestParam("connect_date") String connect_date,
                                   @RequestParam("connect_time") String connect_time,
                                   @RequestParam("self_mac") String self_mac,
                                   @RequestParam("connect_mac") String connect_mac) throws Exception{
        Integer userid1 = Integer.valueOf(userid);
        return bluetoothInfoService.insertOne(userid1,connect_date,connect_time,self_mac,connect_mac);
    }

    //根据日期获得过去一段时间的广播键
    @PostMapping("/api/bluetoothInfo/getBroadcastKeys")
    public List<Map> getBroadcastKeys(@RequestParam("date") String date) throws Exception{
        return bluetoothInfoService.getBroadcastKeys(date);
    }

    //根据用户id、mac地址和日期获进行预测并获得预测结果
    @PostMapping("/api/bluetoothInfo/getForecast")
    public double getForecast(@RequestParam("userid") String userid,
                              @RequestParam("selfmac") String selfmac,
                              @RequestParam("date") String date) throws Exception{
        RiskPrediction riskPrediction = new RiskPrediction();
        List<BluetoothInfo> bluetoothInfoList = bluetoothInfoService.getPatientBluetoothIfno(date);
        double result = riskPrediction.foreCast(userid,selfmac,bluetoothInfoList);
        bluetoothInfoService.setRisk(userid, result);
        return result;
    }

}
