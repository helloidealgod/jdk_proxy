package com.example.compile.table3.action;

import com.example.compile.table3.name.NameInfo;
import com.example.compile.table3.middle.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.compile.table3.Main.*;
import static com.example.compile.table3.middle.Utils.resultList;
import static com.example.compile.table3.name.NameTableUtils.*;
import static com.example.compile.table3.name.NameTableUtils.nameTable;
import static com.example.compile.table3.operate.OprateUtils.*;

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

    /**
     * {aSeg}
     * {aTyp -2}
     * {aE -2}
     * {aNa -5}
     * {aConsv 入栈}
     * {aNa2 入栈}
     * {aFcall}
     * {aFna -4}
     * {aE1 -4}
     *
     * @param top
     * @param symbol
     * @param token
     * @return
     */
    public static String doAction(String top, String symbol, String token) {
        if ("{aNa1}".equals(top)) {
            stack.pop();
            NameInfo nameTable1 = findNameTable(token);
            if (null == nameTable1) {
                System.out.println("找不到符号：" + token);
            }
            return stack.getTop().expr;
        } else if ("{aNa}".equals(top)) {
            //{aNa -5}
            Segment segment = stack.stack.get(stack.size() - 6);
            segment.varName = token;
            stack.pop();
            generaNameTable(token, "int");
            for (int i = 0; i < nameTable.size(); i++) {
                System.out.println("nameTable:" + nameTable.get(i).name);
            }
            return stack.getTop().expr;
        } else if ("{aE}".equals(top)) {
            //{aE -2}
            Segment segment = stack.stack.get(stack.size() - 3);
            if (!opStack.isEmpty()) {
                Result result = operate(opStack.pop());
                if (null != result) {
                    //结果入栈
                    valStack.push(result);
                }
            }
            Result e = valStack.pop();
            segment.e = e;
            stack.pop();
            return stack.getTop().expr;
        } else if ("{aE1}".equals(top)) {
            //{aE1 -5}
            Segment aE1 = stack.getTop();
            Segment segment = stack.stack.get(stack.size() - 6);
            while (aE1.opStackIndex < opStack.size() - 1) {
                Result result = operate(opStack.pop());
                if (null != result) {
                    //结果入栈
                    valStack.push(result);
                }
            }
            Result e = valStack.pop();
            segment.funcVars.add(e);
            stack.pop();
            return stack.getTop().expr;
        } else if ("{aConsv}".equals(top)) {
            //{aConsv 入栈}
            stack.pop();
            valStack.push(new Result("int", "", token));
            return stack.getTop().expr;
        } else if ("{aFna}".equals(top)) {
            //{aFna -6}
            Segment segment = stack.stack.get(stack.size() - 7);
            segment.funName = token;
            stack.pop();
            return stack.getTop().expr;
        } else if ("{aNa2}".equals(top)) {
            //{aNa2 入栈}
            stack.pop();
            valStack.push(new Result("int", token, null));
            return stack.getTop().expr;
        } else if ("{aFcall}".equals(top)) {
            Segment aFcall = stack.pop();
            Result exprOp = new Result("int", "call", aFcall.funName, aFcall.funcVars);
            resultList.add(exprOp);
            valStack.push(new Result("int", exprOp.resultName, null));
            return stack.getTop().expr;
        } else if ("{aTyp}".equals(top)) {
            //{aTyp -2}
            Segment segment = stack.stack.get(stack.size() - 3);
            segment.type = token;
            stack.pop();
            return stack.getTop().expr;
        } else if ("{aSeg}".equals(top)) {
            stack.pop();
            return stack.getTop().expr;
        } else if ("{aF(}".equals(top)) {
            return stack.pop().expr;
        } else if ("{aF)}".equals(top)) {
            return stack.pop().expr;
        } else if ("{aOpm}".equals(top)) {
            Segment segment = stack.stack.get(stack.size() - 3);
            segment.valStackIndex = valStack.size() - 1;
            segment.opStackIndex = opStack.size() - 1;
            opStack.push("#Fun");
            stack.pop();
            return stack.getTop().expr;
        } else {
            return top;
        }
    }
}
