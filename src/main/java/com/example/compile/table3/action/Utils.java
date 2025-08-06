package com.example.compile.table3.action;

import com.example.compile.table3.name.NameInfo;
import com.example.compile.table3.operate.SegmentExprOp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.compile.table3.Main2.*;
import static com.example.compile.table3.name.NameTableUtils.*;
import static com.example.compile.table3.name.NameTableUtils.nameTable;
import static com.example.compile.table3.operate.OprateUtils.opStack;
import static com.example.compile.table3.operate.OprateUtils.valStack;

public class Utils {
    public static void doPush(String[] params) throws IOException {
        if (null == params) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replaceAll("comma", ",").replaceAll("semi", ";");
        }

        Integer nextTokens = Integer.valueOf(params[0]);
        String[] pushParams = null;
        boolean match = true;
        boolean isEqual = true;
        List<String> symbolList = new ArrayList<>();
        if (nextTokens == 0) {
            //push 0,Cf,Ct';
            pushParams = Arrays.copyOfRange(params, 1, params.length);
        } else if (nextTokens == 1) {
            match = false;
            //push 1,eq,(,Na;
            pushParams = Arrays.copyOfRange(params, 3, params.length);
            symbolList.add(params[2]);
            if ("ne".equals(params[1])) {
                isEqual = false;
            }
        } else if (nextTokens == 2) {
            match = false;
            //push 2,ne,(,Na,(,EList,);
            pushParams = Arrays.copyOfRange(params, 4, params.length);
            symbolList.add(params[2]);
            symbolList.add(params[3]);
            if ("ne".equals(params[1])) {
                isEqual = false;
            }
        }
        //读取下N个token
        List<String> tokenList = new ArrayList<>();
        for (int i = nextTokens; i > 0; i--) {
            String token = getToken();
            if (null != token) {
                tokenList.add(token);
            }
        }
        if (tokenList.size() != symbolList.size()) {
            match = true;
        } else {
            for (int i = 0; i < symbolList.size(); i++) {
                if (2 == nextTokens) {
                    boolean b = isEqual == symbolList.get(i).equals(tokenToSymbol(tokenList.get(i)));
                    if (b) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                } else {
                    boolean b = isEqual == tokenList.get(i).equals(symbolList.get(i));
                    if (b) {
                        match = true;
                    } else {
                        match = false;
                        break;
                    }
                }
            }
        }
        //将N个token压回去
        for (int j = tokenList.size() - 1; j >= 0; j--) {
            tokenStack.push(tokenList.get(j));
        }
        if (null == pushParams) {
            return;
        }
        if (!match) {
            return;
        }
        for (int j = pushParams.length - 1; j >= 0; j--) {
            stack.push(pushParams[j]);
        }
    }

    public static String doAction(String top, String symbol, String token) {
        if ("{aNa1}".equals(top)) {
            stack.pop();
            NameInfo nameTable1 = findNameTable(token);
            if (null == nameTable1) {
                System.out.println("找不到符号：" + token);
            }
            return stack.getTop().name;
        } else if ("{aNa}".equals(top)) {
            stack.pop();
            generaNameTable(token, "int");
            for (int i = 0; i < nameTable.size(); i++) {
                System.out.println("nameTable:" + nameTable.get(i).name);
            }
            return stack.getTop().name;
        } else if ("{aE}".equals(top)) {
            stack.pop();
            if (!valStack.isEmpty()) {
                SegmentExprOp e = valStack.pop();
                System.out.println(" " + e.value);
            }
            if (!opStack.isEmpty()) {
                String pop = opStack.pop();
                System.out.println(" " + pop);
            }
            return stack.getTop().name;
        } else if ("{aE1}".equals(top)) {
            stack.pop();
            if (!valStack.isEmpty()) {
                SegmentExprOp e = valStack.pop();
                System.out.println(" " + e.value);
            }
            if (!opStack.isEmpty()) {
                String pop = opStack.pop();
                System.out.println(" " + pop);
            }
            return stack.getTop().name;
        } else if ("{aConsv}".equals(top)) {
            stack.pop();
            valStack.push(new SegmentExprOp("int", "", token));
            return stack.getTop().name;
        } else if ("{aFna}".equals(top)) {
            stack.pop();
            return stack.getTop().name;
        } else if ("{aNa2}".equals(top)) {
            stack.pop();
            valStack.push(new SegmentExprOp("int", token, null));
            return stack.getTop().name;
        } else if ("{aFcall}".equals(top)) {
            stack.pop();
            return stack.getTop().name;
        } else {
            return top;
        }
    }
}
