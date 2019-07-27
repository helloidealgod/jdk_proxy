package com.example.elegant;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

public class StrategyFactory {
    public static StrategyFactory strategyFactory = new StrategyFactory();

    public static HashMap<Integer, String> hashMap = new HashMap();

    static {
        Reflections reflection = new Reflections("com.example.elegant.impl");
        Set<Class<?>> typesAnnotatedWith = reflection.getTypesAnnotatedWith(PayAnnotation.class);
        for(Class clazz : typesAnnotatedWith){
            PayAnnotation payAnnotation = (PayAnnotation) clazz.getAnnotation(PayAnnotation.class);
            hashMap.put(payAnnotation.value(),clazz.getCanonicalName());
        }

    }

    public static StrategyFactory getInstance() {
        return strategyFactory;
    }

    public Strategy create(Integer type) throws Exception{
        String s = hashMap.get(type);
        Class clazz = Class.forName(s);
        return (Strategy) clazz.newInstance();
    }
}
