package com.example.compile.iteration.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Cloneable, Serializable {
    public boolean success;
    /*变量类型、函数返回类型*/
    public int type;
    public String typeStr;
    /*语句类型*/
    public int statementType;
    public String statementTypeStr;
    /*类型长度*/
    public int width;
    /*变量名、函数名*/
    public String name;
    /*数组长度*/
    public String arrayLength;
    /*初始值字符串*/
    public String initValue;
    public List<Result> subList = new ArrayList<>();
    /*形参列表*/
    public List<Result> formalParameterList = new ArrayList<>();
    /*指针、数组 用*/
    public Result rel;

    public String getName() {
        return name;
    }

    @Override
    public Result clone() {
        Result result = null;
        try {
            result = (Result) super.clone();
            if (null != this.rel) {
                result.rel = this.rel.clone();
            }
        } catch (Exception e) {
            result = new Result();
        }
        return result;
    }
}
