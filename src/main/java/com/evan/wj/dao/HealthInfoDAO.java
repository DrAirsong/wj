package com.evan.wj.dao;

import com.evan.wj.pojo.HealthInfo;
import com.evan.wj.pojo.UserAccount;
import com.evan.wj.pojo.UseridAndHealthTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthInfoDAO extends JpaRepository<HealthInfo, UseridAndHealthTime> {
    List<HealthInfo> findAllByUserid(Integer userid);

}
