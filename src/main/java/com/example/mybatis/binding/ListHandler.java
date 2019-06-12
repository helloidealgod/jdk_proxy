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
public class ListHandler implements ResultSetHandler<List> {
    @Override
    public List callback(ResultSet resultSet, String className) {
        List list = new ArrayList();
        try {
            if (className.toLowerCase().contains("int")) {
                while (resultSet.next()) {
                    list.add(resultSet.getInt(1));
                }
                return list;
            } else if (className.toLowerCase().contains("string")) {
                while (resultSet.next()) {
                    list.add(resultSet.getString(1));
                }
                return list;
            } else if (className.toLowerCase().contains("boolean")) {
                while (resultSet.next()) {
                    list.add(resultSet.getBoolean(1));
                }
                return list;
            } else if (className.toLowerCase().contains("short")) {
                while (resultSet.next()) {
                    list.add(resultSet.getShort(1));
                }
                return list;
            } else if (className.toLowerCase().contains("float")) {
                while (resultSet.next()) {
                    list.add(resultSet.getFloat(1));
                }
                return list;
            } else if (className.toLowerCase().contains("double")) {
                while (resultSet.next()) {
                    list.add(resultSet.getDouble(1));
                }
                return list;
            } else if (className.toLowerCase().contains("long")) {
                while (resultSet.next()) {
                    list.add(resultSet.getLong(1));
                }
                return list;
            } else if (className.toLowerCase().contains("date")) {
                while (resultSet.next()) {
                    list.add(resultSet.getTimestamp(1));
                }
                return list;
            } else if (className.toLowerCase().contains("byte")) {
                while (resultSet.next()) {
                    list.add(resultSet.getByte(1));
                }
                return list;
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
                while (resultSet.next()) {
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
                    list.add(object);
                    object = Class.forName(className).newInstance();
                }
                return list;
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
