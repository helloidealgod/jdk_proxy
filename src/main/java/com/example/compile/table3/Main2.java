package com.example.compile.table3;

import com.example.compile.table3.action.Segment;
import com.example.compile.table3.action.Utils;
import com.example.compile.table3.operate.SegmentExprOp;
import com.example.compile.table3.stack.SegmentStack;
import com.example.compile.table3.stack.Stack;

import java.io.*;

import static com.example.compile.table3.action.Utils.doPush;
import static com.example.compile.table3.operate.OprateUtils.*;

public class Main2 {
    public static PushbackReader pr;
    public static int[][] stateMap = initStateMap();

    public static int getIndex(char c) {
        int index = c;
        if (47 >= index && 32 <= index) {
            index -= 32;
        } else if (57 >= index && 48 <= index) {
            index = 16;
        } else if (58 <= index && 64 >= index) {
            index -= 41;
        } else if (90 >= index && 65 <= index) {
            index = 24;
        } else if (96 >= index && 91 <= index) {
            index -= 66;
        } else if (122 >= index && 97 <= index) {
            index = 31;
        } else if (126 >= index && 123 <= index) {
            index -= 91;
        } else {
            index = 0;
        }
        return index;
    }

    public static int[][] initStateMap() {
        return Constant.stateMap;
    }

    public static final String topSym = "Stmts";

    public static String getToken() throws IOException {
        if (!tokenStack.isEmpty()) {
            return tokenStack.pop();
        }
        int read;
        char c;
        StringBuilder token = new StringBuilder("");
        int status = 0;
        int index;
        while ((read = pr.read()) != -1) {
            c = (char) read;
            index = getIndex(c);
            status = stateMap[status][index];
            if (0 == status) {
                token.append(c);
                return token.toString();
            } else if (-1 == status) {
                pr.unread(c);
                return token.toString();
            } else if (-2 == status) {
                System.out.println("error:" + token.toString() + " " + c);
                break;
            } else if (-3 == status) {
                status = 0;
            } else {
                token.append(c);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            File f = new File("");
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/expression.c"));
            pr = new PushbackReader(reader);
            translationUnit();
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 栈
    public static SegmentStack stack = new SegmentStack();
    public static Stack tokenStack = new Stack();

    public static int getTokensIndex(String token) {
        int index = -1;
        for (int i = 0; i < Constant.tokens.length; i++) {
            if (token.equals(Constant.tokens[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int getExprIndex(String stackTop) {
        int index = -1;
        for (int i = 0; i < Constant.exprs.length; i++) {
            if (stackTop.equals(Constant.exprs[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static boolean compare(String stackTop, String token) {
        boolean result = false;
        if (stackTop.equals(token)) {
            result = true;
        }
        return result;
    }

    public static String getAction(String stackTop, String token) {
        String action = null;
        int tokensIndex = getTokensIndex(token);
        int exprIndex = getExprIndex(stackTop);
        if (-1 == tokensIndex || -1 == exprIndex) {
            System.out.println("[stackTop,token]=[" + stackTop + "," + token + "]");
        }
        action = Constant.actionMap[exprIndex][tokensIndex];
        return action;
    }

    public static String tokenToSymbol(String token) {
        String symbol = "$";
        if (null != token && token.matches("\\d*")) {
            symbol = "consv";
        } else if (null != token && token.matches("void|char|short|int|long|float")) {
            symbol = "Typ";
        } else if (null != token && token.matches("do|for|if|while|else")) {
            symbol = token;
        } else if (null != token && token.matches("[A-Za-z]+[A-Za-z0-9]*")) {
            symbol = "Na";
        } else if (null != token) {
            symbol = token;
        }
        return symbol;
    }

    public static void translationUnit() throws IOException {
        String symbol = null;
        String token = getToken();
        StringBuilder symbolLine = new StringBuilder("");
        boolean isError = false;
        do {
            symbol = tokenToSymbol(token);
            if ("$".equals(symbol) && stack.isEmpty()) {
                System.out.println("语法解析结束！");
                if (!valStack.isEmpty()) {
                    SegmentExprOp e = valStack.pop();
                    System.out.println(" " + e.value);
                }
                if (!opStack.isEmpty()) {
                    String pop = opStack.pop();
                    System.out.println(" " + pop);
                }
                return;
            } else if (!symbol.equals("") && stack.isEmpty()) {
                stack.push(topSym);
            } else if (compare(stack.getTop().expr, symbol)) {
                stack.print();
                symbolLine.append(token);
                System.out.println(token);
                stack.pop();
                Segment top = stack.getTop();
                String action = Utils.doAction(top.expr, symbol, token);
                if (",".equals(token)) {
                } else if ("{aF(}".equals(action) || "{aF)}".equals(action)) {
                } else if (isOperate(token)) {
                    boolean flag = true;
                    while (flag) {
                        //是运算符 与前一个运算符做优先级比较 高于前一个 压入操作符栈 ，低于前一个 pop 操作符栈，进行运算
                        int level = 0;
                        if (!opStack.isEmpty() && (level = operateCompare(opStack.getTop(), token)) <= 0) {
                            //前面运算符优先级高于或等于当前运算符优先级，pop 操作符栈 进行运算
                            if (0 == level && "(".equals(opStack.getTop())) {
                                opStack.pop();
                                flag = false;
                            } else if (0 == level && ",".equals(opStack.getTop())) {
                                opStack.push(token);
                                flag = false;
                            } else {
                                //运算
                                SegmentExprOp result = operate(opStack.pop());
                                if (null != result) {
                                    //结果入栈
                                    valStack.push(result);
                                }
                            }
                        } else {
                            //前面无运算符或当前运算符优先级高于前一个，压入操作符栈
                            opStack.push(token);
                            flag = false;
                        }
                    }
                }
                token = getToken();
                stack.print();
            } else {
                //如果栈顶是do action 动作
                String top = stack.getTop().expr;
                stack.print();
                while (top.startsWith("{")) {
                    top = Utils.doAction(top, symbol, token);
                }
                if (top.equals(symbol)) {
                    continue;
                }
                //根据栈顶元素与当前token查找下一步动作
                String actions = getAction(top, symbol);
                String[] commands = actions.split(";");
                //do push
                for (int i = 0; i < commands.length; i++) {
                    String[] split = commands[i].split(" ");
                    String command = split[0];
                    String param = split.length > 1 ? split[1] : null;
                    if (command.equals("error")) {
                        isError = true;
                    } else if (command.equals("pop")) {
                        stack.pop();
                    } else if (command.contains("push")) {
                        doPush(null == param ? null : param.split(","));
                    }
                }
                stack.print();
            }
        } while (!isError);
        if (isError) {
            System.out.println("=======================error=======================");
            System.out.println("解析：" + symbolLine.toString() + " _" + token + "_");
            System.out.println("=======================error=======================");
        }
    }
}
