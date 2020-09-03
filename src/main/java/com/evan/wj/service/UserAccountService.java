package com.evan.wj.service;

import com.evan.wj.dao.UserAccountDAO;
import com.evan.wj.pojo.UserAccount;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    UserAccountDAO userAccountDAO;

    //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
    //可以改用Sort.by获得Sort对象
    public List<UserAccount> list(){
        return userAccountDAO.findAll(Sort.by(Sort.Direction.ASC, "userid"));
    }

    /*
    * 添加或修改一条记录
    * 主键（用户id）不可修改
    * */
    public int addOrUpdate(UserAccount userAccount){
        UserAccount userAccount1 = userAccountDAO.findById(userAccount.getUserid()).orElse(null);
        if(userAccount1 == null){
            //如果是空，则是插入动作
            userAccountDAO.saveAndFlush(userAccount);
            return 1;
        }
        else{
            //如果非空，则是修改动作
            userAccount1.setPassword(userAccount.getPassword());
            userAccount1.setTel(userAccount.getTel());
            userAccount1.setIDnumber(userAccount.getIDnumber());
            userAccount1.setName(userAccount.getName());
            userAccount1.setHealth(userAccount.getHealth());
            userAccount1.setRisk(userAccount.getRisk());
            userAccount1.setLogin_status(userAccount.getLogin_status());
            userAccountDAO.saveAndFlush(userAccount1);
            return 0;
        }
    }

    //根据用户id删除对应的一条记录
    public void deleteByUserid(Integer userid){
        userAccountDAO.deleteById(userid);
    }

    //根据用户id查找记录
    public List<UserAccount> searchByUserid(Integer userid) {
        List<UserAccount> list = userAccountDAO.findAllByUserid(userid);
        return list;
    }

    //根据手机号和用户密码注册一个账号
    public int registerOne(String tel, String password){
        List<UserAccount> list = userAccountDAO.findAllByTel(tel);
        if (list.size() == 0){
            list = userAccountDAO.findAll(Sort.by(Sort.Direction.DESC, "userid"));
            Integer user_id = list.get(0).getUserid();
            Integer new_userid = user_id + 1;

            UserAccount new_userAccount = new UserAccount();
            new_userAccount.setUserid(new_userid);
            new_userAccount.setPassword(password);
            new_userAccount.setTel(tel);
            userAccountDAO.saveAndFlush(new_userAccount);
            return 0;
        }
        else{
            return 1;
        }
    }

    //根据手机号和用户密码登录一个用户账号
    public int loginOne(String tel, String password){
        List<UserAccount> list = userAccountDAO.findAllByTel(tel);
        if (list.size() == 1){
            UserAccount userAccount = list.get(0);
            if(userAccount.getPassword().equals(password)) {
                userAccount.setLogin_status("1");
                userAccountDAO.saveAndFlush(userAccount);
                return 0;   //登录成功
                /*if (userAccount.getLogin_status().equals("0")) {
                    userAccount.setLogin_status("1");
                    userAccountDAO.saveAndFlush(userAccount);
                    return 0;   //登录成功
                } else return 2;  //已经在线*/
            }
            else
                return 1;   //密码错误
        }
        else{
            return 1;       //未注册
        }
    }

    //根据用户id退出账号
    public int logoutOne(Integer userid){
        Optional<UserAccount> findResult = userAccountDAO.findById(userid);
        UserAccount userAccount = findResult.get();
        if (userAccount.getLogin_status().equals("1")){
            findResult.get().setLogin_status("0");
            userAccountDAO.saveAndFlush(findResult.get());
            return 0;   //退出账号成功
        }
        else{
            return 1;   //未登录
        }
    }

    public Integer getUseridByTel(String tel){
        List<UserAccount> list = userAccountDAO.findAllByTel(tel);
        UserAccount userAccount = list.get(0);
        Integer userid = userAccount.getUserid();
        return userid;
    }

    //根据用户id查找一条记录
    public UserAccount findOne(Integer userid) {
        Optional<UserAccount> findResult = userAccountDAO.findById(userid);
        return findResult.get();
    }

    //根据表项修改一条记录
    public int updateOne(String tel, String IDnumber, String name, String health, double risk){
        List<UserAccount> userAccountList = userAccountDAO.findAllByTel(tel);
        if (userAccountList.size() != 1)
            return 1;
        UserAccount userAccount = userAccountList.get(0);
        userAccount.setName(name);
        userAccount.setIDnumber(IDnumber);
        userAccount.setHealth(health);
        userAccount.setRisk(risk);
        userAccountDAO.saveAndFlush(userAccount);
        return 0;
    }
   /* public void updateOne(UserAccount userAccount){
        UserAccount userAccount1 = userAccountDAO.findById(userAccount.getUserid()).orElse(null);
        userAccount1.setPassword(userAccount.getPassword());
        userAccount1.setTel(userAccount.getTel());
        userAccount1.setIDnumber(userAccount.getIDnumber());
        userAccount1.setName(userAccount.getName());
        userAccount1.setHealth(userAccount.getHealth());
        userAccount1.setRisk(userAccount.getRisk());
        userAccount1.setLogin_status(userAccount.getLogin_status());
        userAccountDAO.saveAndFlush(userAccount1);
    }*/

}
