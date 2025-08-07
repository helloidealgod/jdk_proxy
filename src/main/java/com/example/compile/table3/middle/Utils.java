package com.example.compile.table3.middle;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int tempIndex = 0;
    public static int funTempIndex = 0;
    public static List<Result> resultList = new ArrayList<>();

    public static void printResult() {
        for (Result result : resultList) {
            System.out.print(result.op + " ");
            if ("call".equals(result.op)) {
                System.out.print(result.funName + " ");
                for (Result var : result.funcVars) {
                    System.out.print((StringUtils.isEmpty(var.value) ? var.resultName : var.value) + " ");
                }
                System.out.println(result.resultName);
            } else {
                System.out.print((StringUtils.isEmpty(result.e1.value) ? result.e1.resultName : result.e1.value) + " ");
                System.out.print((StringUtils.isEmpty(result.e2.value) ? result.e2.resultName : result.e2.value) + " ");
                System.out.println(result.resultName);
            }
        }
    }
}
