package com.evan.wj.service;

import com.evan.wj.dao.HealthInfoDAO;
import com.evan.wj.dao.PatientInfoDAO;
import com.evan.wj.dao.UserAccountDAO;
import com.evan.wj.pojo.HealthInfo;
import com.evan.wj.pojo.PatientInfo;
import com.evan.wj.pojo.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientInfoService {
    @Autowired
    PatientInfoDAO patientInfoDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    HealthInfoDAO healthInfoDAO;
    @Autowired
    UserAccountService userAccountService;

    //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
    //可以改用Sort.by获得Sort对象
    public List<PatientInfo> list(){
        return patientInfoDAO.findAll(Sort.by(Sort.Direction.ASC, "userid"));
    }

    //管理员编辑

    public void addOrUpdate(PatientInfo patientInfo){
        //如果审核状态变为通过，则要修改用户账号信息表和用户健康信息表
        if(patientInfo.getAudit_status().equals("通过")){
            UserAccount userAccount = userAccountService.findOne(patientInfo.getUserid());
            userAccount.setHealth("确诊");
            userAccountDAO.save(userAccount);

            HealthInfo healthInfo = new HealthInfo();
            healthInfo.setUserid(patientInfo.getUserid());
            healthInfo.setTime(patientInfo.getDiagnosis_time());
            healthInfo.setAction("确诊");
            healthInfoDAO.save(healthInfo);
        }
        patientInfoDAO.save(patientInfo);
    }

    //根据用户id查找记录
    public List<PatientInfo> searchByUserid(Integer userid){
        List<PatientInfo> list = patientInfoDAO.findAllByUserid(userid);
        return list;
    }

    public void deleteByUserid(Integer userid){
        patientInfoDAO.deleteById(userid);
    }

    //根据用户id查找一条记录
    public PatientInfo findOne(Integer userid) {
        Optional<PatientInfo> findResult = patientInfoDAO.findById(userid);
        return findResult.get();
    }

    public int insertOne(Integer userid, String diagnosis_time, String diagnosis_location,
                         String diagnosis_hospital, String diagnosis_case){
        /*List<UserAccount> userAccountList = userAccountDAO.findAllByTel(tel);
        if (userAccountList.size() != 1)
            return 1;
        UserAccount userAccount = userAccountList.get(0);
        Integer userid = userAccount.getUserid();*/
        List<PatientInfo> patientInfoList = patientInfoDAO.findAllByUserid(userid);
        if(patientInfoList.size()!=0)   //已经有一条记录了
            return 1;
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setUserid(userid);
        patientInfo.setDiagnosis_time(diagnosis_time);
        patientInfo.setDiagnosis_location(diagnosis_location);
        patientInfo.setDiagnosis_hospital(diagnosis_hospital);
        patientInfo.setDiagnosis_case(diagnosis_case);
        patientInfo.setAudit_status("待审核");
        patientInfo.setAudit_mind(null);
        patientInfoDAO.saveAndFlush(patientInfo);
        return 0;
    }
    /*public void insertOne(PatientInfo patientInfo){
        patientInfoDAO.save(patientInfo);
    }*/
}
