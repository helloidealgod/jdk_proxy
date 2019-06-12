package com.example.mybatis;

import com.example.mybatis.annotation.Select;
import com.example.mybatis.binding.ListHandler;
import com.example.mybatis.binding.ObjectHandler;
import com.example.mybatis.config.JdbcConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapperInvocationHandler implements InvocationHandler {
    private Object object;

    public MapperInvocationHandler() {
    }

    public MapperInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String returnClassName = method.getReturnType().getName();

//        Class returnClass = method.getReturnType();
//        if (returnClass.getName().toLowerCase().contains("list")){
//            Type genericReturnType = method.getGenericReturnType();
//            //获取返回值的泛型参数
//            if(genericReturnType instanceof ParameterizedType){
//                Type[] actualTypeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
//                for (Type type : actualTypeArguments) {
//                    System.out.println(type);
//                }
//            }
//        }

        boolean annotationPresent = method.isAnnotationPresent(Select.class);
        if (annotationPresent) {
            Select select = method.getAnnotation(Select.class);
            JdbcConfig jdbcConfig = new JdbcConfig();
            if(returnClassName.toLowerCase().contains("list")){
                Type genericReturnType = method.getGenericReturnType();
                if(genericReturnType instanceof ParameterizedType) {
                    Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                    if (actualTypeArguments.length == 1) {
                        String actualTypeName = ((Class)actualTypeArguments[0]).getName();
                        return jdbcConfig.excuteQuery(select.value(), new ListHandler(), actualTypeName, null);
                    }
                }
                return new ArrayList();
            }else{
                return jdbcConfig.excuteQuery(select.value(),new ObjectHandler(),returnClassName,null);
            }
        }
        return null;
    }
}
