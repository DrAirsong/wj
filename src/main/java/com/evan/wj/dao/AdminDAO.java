package com.evan.wj.dao;

import com.evan.wj.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDAO extends JpaRepository<Admin,Integer> {

    Admin findByName(String name);

    Admin getByNameAndPassword(String name, String password);
}


