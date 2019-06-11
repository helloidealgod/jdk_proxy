package com.example.mybatis;

import java.sql.ResultSet;
import java.util.List;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/6/11 15:41
 */
public class ListHandler implements ResultSetHandler<List>{
    @Override
    public List callback(ResultSet resultSet, String className) {
        return null;
    }
}
