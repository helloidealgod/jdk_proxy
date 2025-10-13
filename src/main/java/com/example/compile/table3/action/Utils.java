package com.example.compile.table3.action;


import com.example.compile.table3.tree.impl.SyntaxE;
import com.example.compile.table3.tree.impl.SyntaxTyp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.compile.table3.Main.*;

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
            if (null == token) {
                token = "$";
            }
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
        if ("{Es}".equals(top)) {
            syntax = new SyntaxE();
            stack.pop();
        } else if ("{Ee}".equals(top)) {
            syntax.execute("{Ee}");
            syntaxList.add(syntax);
            syntax = null;
            stack.pop();
        } else if ("{Es'}".equals(top)) {
            syntax.execute(top);
            stack.pop();
        } else if ("{Ee'}".equals(top)) {
            syntax.execute(top);
            stack.pop();
        } else if ("{TypS}".equals(top)) {
            syntax = new SyntaxTyp();
            stack.pop();
        } else if ("{TypE}".equals(top)) {
            syntaxList.add(syntax);
            syntax = null;
            stack.pop();
        } else {
            stack.pop();
        }
        return stack.getTop();
    }
}
