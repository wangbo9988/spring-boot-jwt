package com.cn.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cn.bean.User;

/**
 * @Description
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
public class TokenUtils {

    //    token的生成方法,Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
    //    withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
    public String getToken(User user) {
        String token = "";
        System.out.println(user.getUser_id());
        token = JWT.create().withAudience(user.getUser_id()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
