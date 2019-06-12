package com.example.mybatis;

import com.example.mybatis.dao.UserMapper;
import com.example.mybatis.model.UserInfo;

import java.util.Date;
import java.util.List;

public class MyTest {
    public static void main(String[] argv) {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        Integer userId = mapper.selectUserId(1);
        System.out.println("userId = " + userId);
        String userName = mapper.selectUserName(1);
        System.out.println("userName = " + userName);
        Boolean delFlag = mapper.selectDelFlag(1);
        System.out.println("delFlag = " + delFlag);
        Date createTime = mapper.selectCreateTime(1);
        System.out.println("createTime = " + createTime);
        UserInfo userInfo = new UserInfo(1, "jiang xiankun", "15675266052", "123456");
        UserInfo userInfo1 = mapper.selectUserInfo(userInfo);
        System.out.println("UserInfo = " + userInfo1.toString());
        List<UserInfo> userInfoList = mapper.selectUserInfoList(userInfo);
        List<Integer> userIdList = mapper.selectUserIdList(1);
        List<String> userNameList = mapper.selectUserNameList(1);
        List<Boolean> delFlagList = mapper.selectDelFlagList(1);
        List<Date> createTimeList = mapper.selectCreateTimeList(1);
        System.out.println("UserInfoList = " + userInfoList.size());
    }
}
