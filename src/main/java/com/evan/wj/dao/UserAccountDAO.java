package com.evan.wj.dao;

import com.evan.wj.pojo.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountDAO extends JpaRepository<UserAccount, Integer> {

    List<UserAccount> findAllByUserid(Integer userid);
    List<UserAccount> findAllByTel(String tel);
    List<UserAccount> findAllByHealth(String health);
}

