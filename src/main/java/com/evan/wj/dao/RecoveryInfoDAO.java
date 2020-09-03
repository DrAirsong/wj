package com.evan.wj.dao;

import com.evan.wj.pojo.RecoveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecoveryInfoDAO extends JpaRepository<RecoveryInfo, Integer> {
    List<RecoveryInfo> findAllByUserid(Integer userid);

}
