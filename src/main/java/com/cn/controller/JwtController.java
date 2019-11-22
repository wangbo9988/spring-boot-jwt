package com.cn.controller;

import com.cn.bean.User;
import com.cn.element.UserLoginToken;
import com.cn.mapper.UserMapper;

import com.cn.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
@RestController
public class JwtController {

    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public Object login(User user) {
        System.out.println("登陆验证：" + user.getUsername());
        Map<String, Object> map = new HashMap<>();
        User userFromBase = userMapper.findUserByName(user.getUsername());
        if (userFromBase == null) {
            map.put("message", "登陆失败，用户名不存在");
        } else {
            if (!userFromBase.getPassword().equals(user.getPassword())) {
                map.put("message", "登陆失败，密码错误");
            } else {
                // 颁发token
                String token = new TokenUtils().getToken(userFromBase);
                System.out.println(userFromBase.toString());
                System.out.println(token);
                map.put("token", token);
                map.put("user", userFromBase);
            }
        }
        return map;
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {
        return "你已通过认证";
    }
}
