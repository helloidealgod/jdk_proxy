package com.example.compile.table3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
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
        int[][] stateMap = {{-3, 4, 1, 3, -2, 16, 18, 2, 0, 0, 12, 6, 0, 9, -2, 14, 3, 0, 0, 26, 24, 30, 0, 0, 3, 0, -2, 0, -2, 3, -2, 3, 0, 21, 0, 0},
                {1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {-1, -2, -2, -2, -2, -1, -1, -2, -1, -1, -1, -1, -1, -1, 3, -1, 3, -1, -1, -1, -1, -1, -1, -2, 3, -1, -2, -1, -2, 3, -2, 3, -1, -1, -1, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 5, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -1, -2, 7, -2, -2, -2, -2, -1, -2, -2, -2, 8, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, 10, -2, -2, -1, -2, -2, -2, 11, 34, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -2, -2, -1, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -1, -1, -2, -1, -2, -2, -2, -1, -2, -2, -2, 13, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 15, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 17, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, 19, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 20, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -1, -1, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 23, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, 22, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -1, -2, -2, -2, -2, -1, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, 25, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -1, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, 27, 28, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 29, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, 32, 31, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -1, -2, -1, -2, -2, -2, -2}};
        return stateMap;
    }

    public static String getToken() throws IOException {
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
    public static Stack stack = new Stack();
    // 操作符栈
    public static Stack opStack = new Stack();
    // 值栈
    public static Stack valStack = new Stack();
    //表达式集
    public static String[] exprs = {"E",
            "Le",
            "Lt'",
            "Lf",
            "Ce",
            "Ct'",
            "Cf",
            "Fe",
            "Fe'",
            "Ft",
            "Ft'",
            "F",
            ")",
            "temp"
    };
    //符号集
    public static String[] tokens = {"id",
            "(",
            ")",
            "&&",
            "||",
            "!",
            "<",
            "<=",
            ">",
            ">=",
            "==",
            "!=",
            "+",
            "-",
            "*",
            "/",
            "%",
            ";"
    };
    //语法驱动表
    public static String[][] actionMap = {
/*E*/   {"pop;push Le", "pop;push Le", "error", "error", "error", "pop;push Le", "error", "error", "error", "error", "error", "error", "error", "pop;push Le", "error", "error", "error", "error", ""},
/*Le*/  {"pop;push Lf,Lt'", "pop;push Lf,Lt'", "error", "error", "error", "pop;push !,Le", "error", "error", "error", "error", "error", "error", "error", "pop;push Lf,Lt'", "error", "error", "error", "error", ""},
/*Lt'*/ {"pop;", "pop;", "pop;", "pop;push &&,Lf,Lt'", "pop;push ||,Lf,Lt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", ""},
/*Lf*/  {"pop;push Ce", "pop;push (,Le,)", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push Ce", "error", "error", "error", "error", ""},
/*Ce*/  {"pop;push Cf,Ct'", "pop;push Cf,Ct'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push Cf,Ct'", "error", "error", "error", "error", ""},
/*Ct'*/ {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push <,Cf,Ct'", "pop;push <=,Cf,Ct'", "pop;push >,Cf,Ct'", "pop;push >=,Cf,Ct'", "pop;push ==,Cf,Ct'", "pop;push !=,Cf,Ct'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", ""},
/*Cf*/  {"pop;push Fe", "pop;push (,Ce,)", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push Fe", "error", "error", "error", "error", ""},
/*Fe*/  {"pop;push Ft,Fe'", "pop;push Ft,Fe'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push -,Fe", "error", "error", "error", "error", ""},
/*Fe'*/ {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push +,Ft',Fe'", "pop;push -,Ft',Fe'", "pop;", "pop;", "pop;", "pop;", ""},
/*Ft*/  {"pop;push F,Ft'", "pop;push F,Ft'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", ""},
/*Ft'*/ {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push *,F,Ft'", "pop;push /,F,Ft'", "pop;push %,F,Ft'", "pop;", ""},
/*F*/   {"pop;push id", "pop;push (,Fe,)", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", ""},
/*)*/   {"push E", "push E", "pop;push temp;", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "push temp", "error"},
/*temp*/{"error", "error", "pop;", "pop;push &&,Lf,Lt'", "pop;push ||,Lf,Lt'", "pop;push !,Le", "pop;push <,Cf,Ct'", "pop;push <=,Cf,Ct'", "pop;push >,Cf,Ct'", "pop;push >=,Cf,Ct'", "pop;push ==,Cf,Ct'", "pop;push !=,Cf,Ct'", "pop;push !=,Cf,Ct'", "pop;push +,Ft',Fe'", "pop;push *,F,Ft'", "pop;push /,F,Ft'", "pop;push %,F,Ft'", "pop;"},
    };

    public static int getTokensIndex(String token) {
        int index = -1;
        for (int i = 0; i < tokens.length; i++) {
            if (token.equals(tokens[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int getExprIndex(String stackTop) {
        int index = -1;
        for (int i = 0; i < exprs.length; i++) {
            if (stackTop.equals(exprs[i])) {
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
            System.out.println("");
        }
        action = actionMap[exprIndex][tokensIndex];
        return action;
    }

    /**
     * 判断是否为操作符
     *
     * @param token
     * @return
     */
    public static boolean isOperate(String token) {
        boolean result = false;
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%")
                || token.equals("<") || token.equals("<=") || token.equals(">") || token.equals(">=") || token.equals("==") || token.equals("!=")
                || token.equals("&&") || token.equals("||") || token.equals("!")
                || token.equals("(") || token.equals(")")) {
            result = true;
        }
        return result;
    }

    /**
     * 获取操作符的优先级
     *
     * @param token
     * @return
     */
    public static int getOperateLevel(String token) {
        int level = 0;
        if (token.equals(")")) {
            level = 0;
        } else if (token.equals("&&") || token.equals("||") || token.equals("!")) {
            level = 1;
        } else if (token.equals("<") || token.equals("<=")
                || token.equals(">") || token.equals(">=")
                || token.equals("==") || token.equals("!=")) {
            level = 2;
        } else if (token.equals("+") || token.equals("-")) {
            level = 3;
        } else if (token.equals("*") || token.equals("/") || token.equals("%")) {
            level = 4;
        } else if (token.equals("(")) {
            level = 5;
        }
        return level;
    }

    /**
     * 比较两个操作符的优先级
     *
     * @param operate1
     * @param operate2
     * @return <=0, operate1优先级高,先进行operate1计算
     * >0, operate2优先级高,先进行operate2压栈
     */
    public static int operateCompare(String operate1, String operate2) {
        if ("(".equals(operate1) && ")".equals(operate2)) {
            return 0;
        } else if ("(".equals(operate1)) {
            //(+ 先进行operate2压栈
            return 1;
        } else if (")".equals(operate1)) {
            //)+ 先进行operate1计算
            return -1;
        } else if ("(".equals(operate2)) {
            //+( 先进行operate2压栈
            return 1;
        } else if (")".equals(operate2)) {
            //+) 先进行operate1计算
            return -1;
        }
        int level1 = getOperateLevel(operate1);
        int level2 = getOperateLevel(operate2);
        return level2 - level1;
    }

    /**
     * 操作符计算
     *
     * @param op
     * @return
     */
    public static String operate(String op) {
        if ("(".equals(op)) {
            return "";
        } else if (")".equals(op)) {
            if ("(".equals(opStack.getTop())) {
                opStack.pop();
            } else {
                System.out.println("error");
            }
            return "";
        }
        if (1 > valStack.size()) {
            System.out.println("error val length < 1");
        }
        String val2 = valStack.pop();
        String val1 = null;
        if (!valStack.isEmpty()) {
            val1 = valStack.pop();
        }
        if ("-".equals(op) && null == val1) {
            val1 = "0";
        }
        String result = operate(op, val1, val2);
        //System.out.println("\n" + op + " " + val1 + " " + val2 + " = " + result);
        operateCommandList.add(op + " " + val1 + " " + val2 + " = " + result);
        return result;
    }

    public static String operate(String op, String val1, String val2) {
        String result = null;
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%")
                || op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">=") || op.equals("==") || op.equals("!=")) {
            Integer int1 = Integer.valueOf(val1);
            Integer int2 = Integer.valueOf(val2);
            if (op.equals("+")) {
                result = String.valueOf(int1 + int2);
            } else if (op.equals("-")) {
                result = String.valueOf(int1 - int2);
            } else if (op.equals("*")) {
                result = String.valueOf(int1 * int2);
            } else if (op.equals("/")) {
                result = String.valueOf(int1 / int2);
            } else if (op.equals("%")) {
                result = String.valueOf(int1 % int2);
            } else if (op.equals("<")) {
                result = String.valueOf(int1 < int2);
            } else if (op.equals("<=")) {
                result = String.valueOf(int1 <= int2);
            } else if (op.equals(">")) {
                result = String.valueOf(int1 > int2);
            } else if (op.equals(">=")) {
                result = String.valueOf(int1 >= int2);
            } else if (op.equals("==")) {
                result = String.valueOf(int1 == int2);
            } else if (op.equals("!=")) {
                result = String.valueOf(int1 != int2);
            }
        } else if (op.equals("&&") || op.equals("||") || op.equals("!")) {
            boolean b1 = false;
            boolean b2 = false;
            if ("true".equals(val1) || "false".equals(val1)) {
                b1 = "true".equals(val1);
            } else {
                System.out.println("error val1 is not boolean");
            }
            if ("true".equals(val2) || "false".equals(val2)) {
                b2 = "true".equals(val2);
            } else {
                System.out.println("error val2 is not boolean");
            }

            if (op.equals("&&")) {
                result = String.valueOf(b1 && b2);
            } else if (op.equals("||")) {
                result = String.valueOf(b1 || b2);
            }
        }
        return result;
    }

    public static String tokenToSymbol(String token) {
        String symbol = null;
        if (null != token && token.matches("\\d*")) {
            symbol = "id";
        } else {
            symbol = token;
        }
        return symbol;
    }

    public static List<String> operateCommandList = new ArrayList<>();

    public static void translationUnit() throws IOException {
        String symbol = null;
        String token = getToken();
        StringBuilder symbolLine = new StringBuilder("");
        boolean isError = false;
        do {
            symbol = tokenToSymbol(token);
            if (null == symbol) {
                System.out.println("end of reading!");
                return;
            } else if (";".equals(symbol) && stack.isEmpty()) {
                //是结束符 pop 操作符栈 进行运算
                while (!opStack.isEmpty()) {
                    //运算
                    String result = operate(opStack.pop());
                    //结果入栈
                    valStack.push(result);
                }
                if (!valStack.isEmpty()) {
                    String val = valStack.pop();
                    System.out.println("解析：" + symbolLine.toString());
                    for (int i = 0; i < operateCommandList.size(); i++) {
                        System.out.println(operateCommandList.get(i));
                    }
                    System.out.println("result = " + val);
                }
                operateCommandList.clear();
                symbolLine = new StringBuilder("");
                token = getToken();
            } else if (!symbol.equals("") && stack.isEmpty()) {
                stack.push("E");
            } else if (compare(stack.getTop(), symbol)) {
                //System.out.print(token);
                symbolLine.append(token);
                stack.pop();
                //是数值 压入值栈
                if (token.matches("\\d*")) {
                    valStack.push(token);
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
                            } else {
                                //运算
                                String result = operate(opStack.pop());
                                //结果入栈
                                valStack.push(result);
                            }
                        } else {
                            //前面无运算符或当前运算符优先级高于前一个，压入操作符栈
                            opStack.push(token);
                            flag = false;
                        }
                    }
                }
                //当前符号为),栈内无内容(无表达式内容)说明刚刚pop(即刚刚解析完表达式
                if (")".equals(token) && opStack.isEmpty()) {
                    stack.push("temp");
                }
                token = getToken();
            } else {
                //根据栈顶元素与当前token查找下一步动作
                String actions = getAction(stack.getTop(), symbol);
                String[] commands = actions.split(";");
                for (int i = 0; i < commands.length; i++) {
                    String[] split = commands[i].split(" ");
                    String command = split[0];
                    String param = split.length > 1 ? split[1] : null;
                    if (command.equals("error")) {
                        isError = true;
                    } else if (command.equals("pop")) {
                        stack.pop();
                    } else if (command.contains("push")) {
                        stack.push(param);
                    }
                }
            }
        } while (!isError);
        if (isError) {
            System.out.println("=======================error=======================");
            System.out.println("解析：" + symbolLine.toString() + " _" + token + "_");
            for (int i = 0; i < operateCommandList.size(); i++) {
                System.out.println(operateCommandList.get(i));
            }
            System.out.println("=======================error=======================");
        }
    }
}
