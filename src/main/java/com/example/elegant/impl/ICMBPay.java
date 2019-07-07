package com.example.elegant.impl;

import com.example.elegant.PayAnnotation;
import com.example.elegant.Strategy;
import com.example.elegant.dao.SysUserInfoMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;

@PayAnnotation(1)
public class ICMBPay implements Strategy {
    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Override
    public BigDecimal getPay(Integer type) {
        if (null == sysUserInfoMapper) System.out.println("注入失败");
        else System.out.println("注入成功");
        return BigDecimal.valueOf(0.95);
    }
}
