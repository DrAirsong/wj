package com.evan.wj.controller;

import com.evan.wj.pojo.HealthInfo;
import com.evan.wj.pojo.PatientInfo;
import com.evan.wj.service.HealthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HealthInfoController {
    @Autowired
    HealthInfoService healthInfoService;

    @GetMapping("/api/healthInfo")
    public List<HealthInfo> list() throws Exception{
        return healthInfoService.list();
    }

    //@CrossOrigin
    @PostMapping("/api/healthInfo")
    public HealthInfo addOrUpdate(@RequestBody HealthInfo healthInfo) throws Exception{
        healthInfoService.addOrUpdate(healthInfo);
        return healthInfo;
    }

    //根据用户id查找对应记录
    @GetMapping("/api/healthInfo/searchByUserid")
    public List<HealthInfo> searchByUserid(@RequestParam("userid") String userid) throws Exception {
        if (userid.equals(""))
            return healthInfoService.list();
        Integer userid_ = Integer.valueOf(userid);
        return healthInfoService.searchByUserid(userid_);
    }

    //根据表项删除对应的记录
    @PostMapping("/api/healthInfo/delete")
    public void delete(@RequestBody HealthInfo healthInfo) throws Exception{
        healthInfoService.deleteByPK(healthInfo.getUserid(), healthInfo.getTime());
    }

}
