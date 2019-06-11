package com.example.mybatis;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
            Object object = Class.forName(className).newInstance();
            Class clazz = object.getClass();
            Field[] fields = clazz.getFields();
            List<String> columnNames = new ArrayList<String>();
            List<String> columnTypes = new ArrayList<String>();
            for (Field field : fields) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                ColumnType columnTypeAnnotation = field.getAnnotation(ColumnType.class);
                if (null != columnAnnotation) {
                    System.out.println("sql result key_name:" + columnAnnotation.value());
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
                    }
                    if ("VARCHAR".equals(columnTypes.get(i))) {
                        fields[i].set(object, resultSet.getInt(columnNames.get(i)));
                    }
                    if ("TINYINT".equals(columnTypes.get(i))) {
                        fields[i].set(object, resultSet.getInt(columnNames.get(i)));
                    }
                    if ("TIMESTAMP".equals(columnTypes.get(i))) {
                        fields[i].set(object, resultSet.getInt(columnNames.get(i)));
                    }
                }
            }
            return object;
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
