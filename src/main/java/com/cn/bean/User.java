package com.cn.bean;

import lombok.*;

/**
 * @Description 实体类User
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String user_id;
    private String username;
    private String password;

//    public User() {
//    }
//
//    public User(String userId, String username, String password) {
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "userId='" + userId + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
