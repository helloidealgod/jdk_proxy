package com.example.mybatis;

public interface UserMapper {
    @Select("select user_name from user_info where del_flag = false and uid = 1")
    String selectUserCount(int id);

    @Select("select * from user_info where del_flag = false and uid = 1")
    UserInfo selectUserCountByObj(UserInfo userInfo);
}
