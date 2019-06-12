package com.example.mybatis.binding;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
    T callback(ResultSet resultSet,String className);
}
