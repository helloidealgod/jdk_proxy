package com.example.mybatis;

public class MyTest {
    public static void main(String[] argv){
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        String count = mapper.selectUserCount(1);
        System.out.println("count = "+count);
    }
}
