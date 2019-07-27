package com.example.elegant;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Set;

@Service
public class BeanUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanUtils() {
        Reflections reflections = new Reflections(this.getClass(), new FieldAnnotationsScanner());

        Set<Field> fields = reflections.getFieldsAnnotatedWith(javax.annotation.Resource.class);

        for (Field field : fields) {
            try {
                String simpleName = field.getType().getSimpleName();
                String beanName = toLowerCaseFirstLetter(simpleName);
                Object bean = applicationContext.getBean(beanName);
                if (null == bean) return;
                field.setAccessible(true);
                field.set(this, bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //首字母转小写
    public String toLowerCaseFirstLetter(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public String toUpperCaseFirstLetter(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
