package com.cn.mapper;

import com.cn.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author Wangbo
 * @Date 2019/11/21
 * @Version V1.0
 **/
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User WHERE username=#{username}")
    public User findUserByName(String username);

    @Select("SELECT * FROM User WHERE user_id=#{id}")
    public User getUserById(String id);
}
