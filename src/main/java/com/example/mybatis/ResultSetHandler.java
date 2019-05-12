package com.example.mybatis;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {
    public T callback(ResultSet resultSet);
}
