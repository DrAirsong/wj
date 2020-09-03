package com.evan.wj.controller;

import com.evan.wj.pojo.BluetoothInfo;
import com.evan.wj.pojo.PatientInfo;
import com.evan.wj.pojo.UserAccount;
import com.evan.wj.service.PatientInfoService;
import com.evan.wj.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientInfoController {
    @Autowired
    PatientInfoService patientInfoService;
    @Autowired
    UserAccountService userAccountService;

    //@CrossOrigin
    @GetMapping("/api/patientInfo")
    public List<PatientInfo> list() throws Exception{
        return patientInfoService.list();
    }

    //@CrossOrigin
    @PostMapping("/api/patientInfo")
    public PatientInfo addOrUpdate(@RequestBody PatientInfo patientInfo) throws Exception{
        patientInfoService.addOrUpdate(patientInfo);
        return patientInfo;
    }

    //根据用户id查找对应记录
    @GetMapping("/api/patientInfo/searchByUserid")
    public List<PatientInfo> searchByUserid(@RequestParam("userid") String userid) throws Exception {
        if (userid.equals(""))
            return patientInfoService.list();
        Integer userid_ = Integer.valueOf(userid);
        return patientInfoService.searchByUserid(userid_);
    }

    //根据表项删除一条记录
    @PostMapping("/api/patientInfo/delete")
    public void delete(@RequestBody PatientInfo patientInfo) throws Exception{
        patientInfoService.deleteByUserid(patientInfo.getUserid());
    }

    //根据用户tel查找一条记录
    @PostMapping("/api/patientInfo/findOne")
    public PatientInfo findOne(@RequestParam("tel") String tel) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        return patientInfoService.findOne(userid);
    }

    //根据属性内容插入一条记录
    @PostMapping("/api/patientInfo/insertOne")
    public int  insertOne(
            @RequestParam("tel") String tel,
            @RequestParam("diagnosis_time") String diagnosis_time,
            @RequestParam("diagnosis_location") String diagnosis_location,
            @RequestParam("diagnosis_hospital") String diagnosis_hospital,
            @RequestParam("diagnosis_case") String diagnosis_case) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        int result = patientInfoService.insertOne(userid, diagnosis_time,diagnosis_location,
                diagnosis_hospital, diagnosis_case);
        return result;
    }
}
