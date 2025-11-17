package com.example.compile.table3;

import com.example.compile.table3.constant.Constant;
import com.example.compile.table3.stack.Stack;
import com.example.compile.table3.tree.Syntax;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.compile.table3.action.Utils.doPush;

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
    public static Stack stack = new Stack();
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

    public static List<Syntax> syntaxList = new ArrayList<>();

    public static void translationUnit() throws IOException {
        String symbol = null;
        String token = getToken();
        StringBuilder symbolLine = new StringBuilder("");
        boolean isError = false;
        ASTNode root = new ASTNode("Stmts");
        java.util.Stack<ASTNode> nodeStack = new java.util.Stack<>();
        nodeStack.push(root);
        do {
            symbol = tokenToSymbol(token);
            if ("$".equals(symbol) && stack.isEmpty()) {
                ASTNode peek = nodeStack.peek();
                return;
            } else if (!symbol.equals("") && stack.isEmpty()) {
                stack.push(topSym);
            } else if (compare(stack.getTop(), symbol)) {
                //System.out.print("3--->");
                //stack.print();
                symbolLine.append(token);
                System.out.println(token);
                //匹配并出栈
                stack.pop();
                //判断是否是动作
                String top = stack.getTop();
                if (top.startsWith("{")) {
                    //是动作，执行动作，弹出栈顶
                    stack.pop();
                    if ("{endForstList}".equals(top)) {
                        while (!"ForstList".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop ForstList
                        ASTNode leaf = new ASTNode(token);
                        nodeStack.peek().addChild(leaf);
                    } else if ("{endE}".equals(top)) {
                        while (!"E".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop E
                        ASTNode leaf = new ASTNode(token);
                        nodeStack.peek().addChild(leaf);
                    } else if ("{endForetList}".equals(top)) {
                        while (!"ForetList".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop ForetList
                        ASTNode leaf = new ASTNode(token);
                        nodeStack.peek().addChild(leaf);
                    } else if ("{endForBlock}".equals(top)) {
                        while (!"Block".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop Block
                        nodeStack.pop();//pop for
                    } else if ("{endWhileBlock}".equals(top)) {
                        while (!"Block".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop Block
                        nodeStack.pop();//pop while
                    } else if ("{endIf}".equals(top)) {
                        while (!"if".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        //nodeStack.pop();//pop if
                        String stackTop = stack.getTop();
                        if ("{endIfStmt}".equals(stackTop)) {
                            stack.pop();
                            while (!"if".equals(nodeStack.peek().type)) {
                                nodeStack.pop();
                            }
                            nodeStack.pop();//pop if
                        }
                    } else if ("{endElse}".equals(top)) {
                        while (!"else".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        String stackTop = stack.getTop();
                        if ("{endIfStmt}".equals(stackTop)) {
                            stack.pop();
                            while (!"if".equals(nodeStack.peek().type)) {
                                nodeStack.pop();
                            }
                            nodeStack.pop();//pop if
                        }
                    } else if ("{ForstComma}".equals(top)) {
                        while (!"Forst".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop Forst
                    } else if ("{ForetComma}".equals(top)) {
                        while (!"Foret".equals(nodeStack.peek().type)) {
                            nodeStack.pop();
                        }
                        nodeStack.pop();//pop Foret
                    } else {
                        ASTNode leaf = new ASTNode(token);
                        nodeStack.peek().addChild(leaf);
                    }
                    top = stack.getTop();
                } else if (";".equals(symbol) && "Stmt'".equals(top)) {
                    while (!"Block".equals(nodeStack.peek().type)) {
                        nodeStack.pop();
                    }
                } else if ("{".equals(token) || "else".equals(token)) {

                } else {
                    ASTNode leaf = new ASTNode(token);
                    nodeStack.peek().addChild(leaf);
                }
                //判断是否需要新建节点
                if ("for".equals(top) || "ForstList".equals(top) || "Forst".equals(top)
                        || "ForetList".equals(top) || "Foret".equals(top)
                        || "Block".equals(top) || "E".equals(top)) {
                    ASTNode node = new ASTNode(top);
                    nodeStack.peek().addChild(node);
                    nodeStack.push(node);
                }
                token = getToken();
                System.out.print("4--->");
                stack.print();
            } else {
                String top = stack.getTop();
                //System.out.print("0--->");
                //stack.print();
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
                String top1 = stack.getTop();
                //判断是否需要新建节点
                if ("for".equals(top1) || "ForstList".equals(top1) || "Forst".equals(top1)
                        || "ForetList".equals(top1) || "Foret".equals(top1)
                        || "Block".equals(top1) || "E".equals(top1)
                        || "Typ".equals(top1) || "Funcall".equals(top1)
                        || "while".equals(top1)
                        || "if".equals(top1) || "else".equals(top1)) {
                    ASTNode forNode = new ASTNode(top1);
                    nodeStack.peek().addChild(forNode);
                    nodeStack.push(forNode);
                }
                System.out.print("1--->");
                stack.print();
            }
        }
        while (!isError);
        if (isError) {
            System.out.println("=======================error=======================");
            System.out.println("解析：" + symbolLine.toString() + " _" + token + "_");
            System.out.println("=======================error=======================");
        }
    }
}
