package com.example.elegant.impl;

import com.example.elegant.BeanUtils;
import com.example.elegant.PayAnnotation;
import com.example.elegant.Strategy;
import com.example.elegant.dao.SysUserInfoMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;

@PayAnnotation(2)
//public class CSBStrategy extends BeanUtils implements Strategy{
public class CSBStrategy implements Strategy{

    /**
     * spring框架，在某个地方使用new后就不生效了，所以，sysUserInfoMapper,无法注入
     * 所以需要继承BeanUtils，BeanUtils在新建对象时会获取spring的applicationContext
     * 然后遍历有Resource注解的Filed，并给它new一个对象，从而实现手动注入
     */
    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Override
    public BigDecimal getPay(Integer type) {
        if (null == sysUserInfoMapper) System.out.println("注入失败");
        else System.out.println("注入成功");
        return BigDecimal.valueOf(0.85);
    }
}
