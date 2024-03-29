package com.example.compile.iteration.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Result implements Cloneable, Serializable {
    public String id = UUID.randomUUID().toString();
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
    public String returnStr;
    /*初始值字符串*/
    public String initValue;
    public List<Result> subList = new ArrayList<>();
    /*形参列表*/
    public List<Result> formalParameterList = new ArrayList<>();
    /*指针、数组 用*/
    public Result rel;
    public List<Result> operateList = new ArrayList<>();

    /*运算类型*/
    public int operationType;
    public String operationTypeStr;
    public Result operation1;
    public Result operation2;


    public String getName() {
        return name;
    }

    @Override
    public Result clone() {
        Result result = null;
        try {
            result = (Result) super.clone();
            result.id = UUID.randomUUID().toString();
            if (null != this.rel) {
                result.rel = this.rel.clone();
            }
            result.operateList = new ArrayList<>();
            for (Result item : this.operateList) {
                Result clone = item.clone();
                clone.operation1 = item.operation1.clone();
                clone.operation2 = item.operation2.clone();
                result.operateList.add(clone);
            }
        } catch (Exception e) {
            result = new Result();
        }
        return result;
    }
}
