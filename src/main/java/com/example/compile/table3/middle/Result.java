package com.example.compile.table3.middle;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public String type;
    public String op;
    public String resultName;
    public String value;

    public Result e1;
    public Result e2;

    public String funName;
    public List<Result> funcVars = new ArrayList<>();

    public Result(String type, String resultName, String value) {
        this.type = type;
        this.resultName = resultName;
        this.value = value;
    }

    public Result(String type, String op, Result e1, Result e2) {
        this.type = type;
        this.op = op;
        this.e1 = e1;
        this.e2 = e2;
        this.resultName = "temp_" + Utils.tempIndex++;
    }

    public Result(String type, String op, String funName, List<Result> funcVars) {
        this.type = type;
        this.op = op;
        this.funName = funName;
        this.funcVars = funcVars;
        this.resultName = "funTemp_" + Utils.funTempIndex++;
    }
}
