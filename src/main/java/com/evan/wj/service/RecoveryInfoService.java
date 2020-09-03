package com.evan.wj.service;

import com.evan.wj.dao.HealthInfoDAO;
import com.evan.wj.dao.RecoveryInfoDAO;
import com.evan.wj.dao.UserAccountDAO;
import com.evan.wj.pojo.HealthInfo;
import com.evan.wj.pojo.RecoveryInfo;
import com.evan.wj.pojo.UserAccount;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecoveryInfoService {
    @Autowired
    RecoveryInfoDAO recoveryInfoDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    HealthInfoDAO healthInfoDAO;
    @Autowired
    UserAccountService userAccountService;

    //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
    //可以改用Sort.by获得Sort对象
    public List<RecoveryInfo> list(){
        return recoveryInfoDAO.findAll(Sort.by(Sort.Direction.ASC, "userid"));
    }

    //管理员编辑
    public void addOrUpdate(RecoveryInfo recoveryInfo){
        //如果审核状态变为通过，则要修改用户账号信息表和用户健康信息表
        if(recoveryInfo.getAudit_status().equals("通过")){
            UserAccount userAccount = userAccountService.findOne(recoveryInfo.getUserid());
            userAccount.setHealth("健康");
            userAccountDAO.save(userAccount);

            HealthInfo healthInfo = new HealthInfo();
            healthInfo.setUserid(recoveryInfo.getUserid());
            healthInfo.setTime(recoveryInfo.getRecovery_time());
            healthInfo.setAction("康复");
            healthInfoDAO.save(healthInfo);
        }
        recoveryInfoDAO.save(recoveryInfo);
    }

    //根据用户id查找记录
    public List<RecoveryInfo> searchByUserid(Integer userid){
        List<RecoveryInfo> list = recoveryInfoDAO.findAllByUserid(userid);
        return list;
    }

    public void deleteByUserid(Integer userid){
        recoveryInfoDAO.deleteById(userid);
    }

    public RecoveryInfo findOne(Integer userid) {
        Optional<RecoveryInfo> findResult = recoveryInfoDAO.findById(userid);
        return findResult.get();
    }

    public int insertOne(Integer userid, String recovery_time, String recovery_location,
                         String recovery_hospital, String recovery_case){
        List<RecoveryInfo> recoveryInfoList = recoveryInfoDAO.findAllByUserid(userid);
        if(recoveryInfoList.size()!=0)  //已经有一条记录了
            return 1;
        RecoveryInfo recoveryInfo = new RecoveryInfo();
        recoveryInfo.setUserid(userid);
        recoveryInfo.setRecovery_time(recovery_time);
        recoveryInfo.setRecovery_location(recovery_location);
        recoveryInfo.setRecovery_hospital(recovery_hospital);
        recoveryInfo.setRecovery_case(recovery_case);
        recoveryInfo.setAudit_status("待审核");
        recoveryInfo.setAudit_mind(null);
        recoveryInfoDAO.saveAndFlush(recoveryInfo);
        return 0;
    }

/*    public void insertOne(RecoveryInfo recoveryInfo){
        recoveryInfoDAO.save(recoveryInfo);
    }*/

}