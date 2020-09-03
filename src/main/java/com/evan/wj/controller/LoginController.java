package com.evan.wj.controller;

import com.evan.wj.result.Result;
import com.evan.wj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import com.evan.wj.pojo.Admin;

@RestController
public class LoginController {

    @Autowired
    AdminService userService;

    //根据表项登录管理员账号
    @CrossOrigin
    @PostMapping(value = "api/login")
    public Result login(@RequestBody Admin requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String name = requestUser.getName();

        name = HtmlUtils.htmlEscape(name);

        Admin admin = userService.get(name, requestUser.getPassword());

        if (null == admin) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}