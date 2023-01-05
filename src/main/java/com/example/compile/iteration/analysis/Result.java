package com.example.compile.iteration.analysis;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public boolean success;
    /*变量类型、函数返回类型*/
    public int type;
    /*语句类型*/
    public int statementType;
    /*类型长度*/
    public int width;
    /*变量名、函数名*/
    public String name;
    /*初始值字符串*/
    public String initValue;
    public List<Result> subList = new ArrayList<>();
    /*形参列表*/
    public List<Result> formalParameterList = new ArrayList<>();

    public String getName(){
        return name;
    }
}
