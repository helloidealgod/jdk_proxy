package com.example.mybatis;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Handler implements ResultSetHandler<String> {
    @Override
    public String callback(ResultSet resultSet) {
        try {
            while (resultSet.next())
                return resultSet.getString("user_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
