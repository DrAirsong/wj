package com.evan.wj.service;

import com.evan.wj.dao.HealthInfoDAO;
import com.evan.wj.pojo.HealthInfo;
import com.evan.wj.pojo.PatientInfo;
import com.evan.wj.pojo.UseridAndHealthTime;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthInfoService {
    @Autowired
    HealthInfoDAO healthInfoDAO;

    //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
    //可以改用Sort.by获得Sort对象
    public List<HealthInfo> list(){
        return healthInfoDAO.findAll(Sort.by(Sort.Direction.ASC, "userid"));
    }

    public void addOrUpdate(HealthInfo healthInfo){
        UseridAndHealthTime primaryKey = new UseridAndHealthTime();
        primaryKey.setUserid(healthInfo.getUserid());
        primaryKey.setTime(healthInfo.getTime());

        HealthInfo healthInfo1 = new HealthInfo();
        healthInfo1.setUserid(primaryKey.getUserid());
        healthInfo1.setTime(primaryKey.getTime());
        healthInfo1.setAction(healthInfo.getAction());

        healthInfoDAO.save(healthInfo1);
    }

    //根据用户id查找记录
    public List<HealthInfo> searchByUserid(Integer userid){
        List<HealthInfo> list = healthInfoDAO.findAllByUserid(userid);
        return list;
    }

    public void deleteByPK(Integer userid, String time){
        UseridAndHealthTime primaryKey = new UseridAndHealthTime();
        primaryKey.setUserid(userid);
        primaryKey.setTime(time);
        healthInfoDAO.deleteById(primaryKey);
    }

}
