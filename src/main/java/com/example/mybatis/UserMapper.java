package com.example.mybatis;

public interface UserMapper {
    @Select("select count(0) from user_info where del_flag = false")
    int selectUserCount(int id);
}
