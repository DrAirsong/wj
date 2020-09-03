package com.evan.wj.controller;

import com.evan.wj.pojo.PatientInfo;
import com.evan.wj.pojo.RecoveryInfo;
import com.evan.wj.pojo.UserAccount;
import com.evan.wj.service.RecoveryInfoService;
import com.evan.wj.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecoveryInfoController {
    @Autowired
    RecoveryInfoService recoveryInfoService;
    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/api/recoveryInfo")
    public List<RecoveryInfo> list() throws Exception{
        return recoveryInfoService.list();
    }

    @PostMapping("/api/recoveryInfo")
    public RecoveryInfo addOrUpdate(@RequestBody RecoveryInfo recoveryInfo) throws Exception{
        recoveryInfoService.addOrUpdate(recoveryInfo);
        return recoveryInfo;
    }

    //根据用户id查找对应的一条记录
    @GetMapping("/api/recoveryInfo/searchByUserid")
    public List<RecoveryInfo> searchByUserid(@RequestParam("userid") String userid) throws Exception {
        if (userid.equals(""))
            return recoveryInfoService.list();
        Integer userid_ = Integer.valueOf(userid);
        return recoveryInfoService.searchByUserid(userid_);
    }

    //根据表项删除一条记录
    @PostMapping("/api/recoveryInfo/delete")
    public void delete(@RequestBody RecoveryInfo recoveryInfo) throws Exception{
        recoveryInfoService.deleteByUserid(recoveryInfo.getUserid());
    }

    //根据用户id查找一条记录
    @PostMapping("/api/recoveryInfo/findOne")
    public RecoveryInfo findOne(@RequestParam("tel") String tel) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        return recoveryInfoService.findOne(userid);
    }

    //根据属性内容插入一条记录
    @PostMapping("/api/recoveryInfo/insertOne")
    public int  insertOne(
            @RequestParam("tel") String tel,
            @RequestParam("recovery_time") String recovery_time,
            @RequestParam("recovery_location") String recovery_location,
            @RequestParam("recovery_hospital") String recovery_hospital,
            @RequestParam("recovery_case") String recovery_case) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        int result = recoveryInfoService.insertOne(userid, recovery_time,recovery_location,
                recovery_hospital,recovery_case);
        return result;
    }


}
