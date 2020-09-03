package com.evan.wj.controller;

import com.evan.wj.pojo.UserAccount;
import com.evan.wj.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;

    @GetMapping("/api/userAccount")
    public List<UserAccount> list() throws Exception{
        return userAccountService.list();
    }

    @PostMapping("/api/userAccount")
    public UserAccount addOrUpdate(@RequestBody UserAccount userAccount) throws Exception{
        userAccountService.addOrUpdate(userAccount);
        return userAccount;
    }

    // 根据表项删除对应的一条记录
    @PostMapping("/api/userAccount/delete")
    public void delete(@RequestBody UserAccount userAccount) throws Exception{
        userAccountService.deleteByUserid(userAccount.getUserid());
    }

    //根据用户id查找对应记录
    @GetMapping("/api/userAccount/searchByUserid")
    public List<UserAccount> searchByUserid(@RequestParam("userid") String userid) throws Exception {
        if (userid.equals(""))
            return userAccountService.list();
        Integer userid_ = Integer.valueOf(userid);
        return userAccountService.searchByUserid(userid_);
    }
    //----------------以下是对客户端的接口----------------//
    //根据手机号和用户密码注册一个账号
    //返回值：0表示注册成功，1表示手机号已经注册
    @PostMapping("/api/userAccount/registerOne")
    public  int registerOne(
            @RequestParam("tel") String tel,
            @RequestParam("password") String password) throws Exception{
        return userAccountService.registerOne(tel, password);
    }

    //根据手机号和用户密码登录一个用户账号
    //返回值：0表示登录成功，1表示未注册，2表示已经在线
    @PostMapping("/api/userAccount/loginOne")
    public  int loginOne(
            @RequestParam("tel") String tel,
            @RequestParam("password") String password) throws Exception{
        return userAccountService.loginOne(tel, password);
    }

    //根据用户tel登出
    //返回值：0表示退出账号成功，1表示未登录
    @PostMapping("/api/userAccount/logoutOne")
    public int logoutOne(@RequestParam("tel") String tel) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        return userAccountService.logoutOne(userid);
    }

    //根据用户tel查找一条记录
    @PostMapping("/api/userAccount/findOne")
    public UserAccount findOne(@RequestParam("tel") String tel) throws Exception{
        Integer userid = userAccountService.getUseridByTel(tel);
        return userAccountService.findOne(userid);
    }

    //根据内容修改一条记录
    @PostMapping("/api/userAccount/updateOne")
    public int updateOne(
            @RequestParam("tel") String tel,
            @RequestParam("idnumber") String IDnumber,
            @RequestParam("name") String name,
            @RequestParam("health") String health,
            @RequestParam("risk") double risk) throws Exception{
        int result = userAccountService.updateOne(tel, IDnumber, name, health, risk);
        return result;
    }

}
