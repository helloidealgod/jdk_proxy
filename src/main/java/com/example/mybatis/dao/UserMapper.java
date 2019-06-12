package com.example.mybatis.dao;

import com.example.mybatis.annotation.Select;
import com.example.mybatis.model.UserInfo;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    @Select("select uid from user_info where del_flag = false and uid = 1")
    Integer selectUserId(int id);

    @Select("select user_name from user_info where del_flag = false and uid = 1")
    String selectUserName(int id);

    @Select("select del_flag from user_info where del_flag = false and uid = 1")
    Boolean selectDelFlag(int id);

    @Select("select create_time from user_info where del_flag = false and uid = 1")
    Date selectCreateTime(int id);

    @Select("select * from user_info where del_flag = false and uid = 1")
    UserInfo selectUserInfo(UserInfo userInfo);

    @Select("select distinct * from user_info where del_flag = false")
    List<UserInfo> selectUserInfoList(UserInfo userInfo);

    @Select("select uid from user_info where del_flag = false")
    List<Integer> selectUserIdList(int id);

    @Select("select user_name from user_info where del_flag = false")
    List<String> selectUserNameList(int id);

    @Select("select del_flag from user_info where del_flag = false")
    List<Boolean> selectDelFlagList(int id);

    @Select("select create_time from user_info where del_flag = false")
    List<Date> selectCreateTimeList(int id);
}
