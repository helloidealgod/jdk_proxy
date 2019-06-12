package com.example.mybatis.binding;

import com.example.mybatis.annotation.Column;
import com.example.mybatis.annotation.ColumnType;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/6/11 15:41
 */
public class ObjectHandler implements ResultSetHandler<Object> {
    @Override
    public Object callback(ResultSet resultSet, String className) {
        try {
            if (!resultSet.next()) return null;
            if (className.toLowerCase().contains("int")) {
                return resultSet.getInt(1);
            } else if (className.toLowerCase().contains("string")) {
                return resultSet.getString(1);
            } else if (className.toLowerCase().contains("boolean")) {
                return resultSet.getBoolean(1);
            } else if (className.toLowerCase().contains("short")) {
                return resultSet.getShort(1);
            } else if (className.toLowerCase().contains("float")) {
                return resultSet.getFloat(1);
            } else if (className.toLowerCase().contains("double")) {
                return resultSet.getDouble(1);
            } else if (className.toLowerCase().contains("long")) {
                return resultSet.getLong(1);
            } else if (className.toLowerCase().contains("date")) {
                return resultSet.getTimestamp(1);
            } else if (className.toLowerCase().contains("byte")) {
                return resultSet.getByte(1);
            } else {
                Object object = Class.forName(className).newInstance();
                Class clazz = object.getClass();
                Field[] fields = clazz.getDeclaredFields();
                List<String> columnNames = new ArrayList<String>();
                List<String> columnTypes = new ArrayList<String>();
                Column columnAnnotation = null;
                ColumnType columnTypeAnnotation = null;
                for (Field field : fields) {
                    field.setAccessible(true);
                    columnAnnotation = field.getAnnotation(Column.class);
                    columnTypeAnnotation = field.getAnnotation(ColumnType.class);
                    if (null != columnAnnotation) {
                        columnNames.add(columnAnnotation.value());
                    }
                    if (null != columnTypeAnnotation) {
                        columnTypes.add(columnTypeAnnotation.value());
                    }
                }
                do {
                    for (int i = 0; i < columnNames.size(); i++) {
                        if ("INTEGER".equals(columnTypes.get(i))) {
                            fields[i].set(object, resultSet.getInt(columnNames.get(i)));
                        } else if ("VARCHAR".equals(columnTypes.get(i))) {
                            fields[i].set(object, resultSet.getString(columnNames.get(i)));
                        } else if ("TINYINT".equals(columnTypes.get(i))) {
                            fields[i].set(object, resultSet.getBoolean(columnNames.get(i)));
                        } else if ("TIMESTAMP".equals(columnTypes.get(i))) {
                            fields[i].set(object, resultSet.getTimestamp(columnNames.get(i)));
                        }
                    }
                } while (resultSet.next());
                return object;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
