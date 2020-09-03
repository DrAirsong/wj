package com.evan.wj.dao;

import com.evan.wj.pojo.PatientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientInfoDAO extends JpaRepository<PatientInfo, Integer> {
    List<PatientInfo> findAllByUserid(Integer userid);

}
