package com.example.mybatis;

public class MyTest {
    public static void main(String[] argv){
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        String count = mapper.selectUserCount(1);
        System.out.println("count = "+count);
        UserInfo userInfo = new UserInfo(1,
                "jiang xiankun",
                "15675266052",
                "123456");
        UserInfo result = mapper.selectUserCountByObj(userInfo);
    }
}
