package com.evan.wj.service;

import com.evan.wj.dao.AdminDAO;
import com.evan.wj.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminService {
    @Autowired
    AdminDAO adminDAO;

    public boolean isExist(String name) {
        Admin admin = getByName(name);
        return null!=admin;
    }

    public Admin getByName(String name) {
        return adminDAO.findByName(name);
    }

    public Admin get(String name, String password){
        return adminDAO.getByNameAndPassword(name, password);
    }

    public void add(Admin admin) {
        adminDAO.save(admin);
    }
}

