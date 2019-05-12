package com.example.mybatis;

import java.sql.Connection;

import static com.example.mybatis.JdbcConfig.getConnection;

public class MyTest {
    public static void main(String[] argv){
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        int count = mapper.selectUserCount(1);
        System.out.println("count = "+count);
        Connection connection = getConnection();
        if(null != connection){
            System.out.println("链接成功！");
        }else {
            System.out.println("链接失败！");
        }
    }
}
