package com.example.compile.table3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    // 操作符栈
    public static Stack opStack = new Stack();
    // 值栈
    public static Stack valStack = new Stack();
    //表达式集
    public static String[] exprs = {
            "Stmts",
            "Stmt'",
            "Stmt",
            "ForstList",
            "Forst'",
            "Forst",
            "ForetList",
            "Foret'",
            "Foret",
            "Block",
            "If",
            "Else'",
            "Nadef",
            "Tempdef'",
            "Funcall",
            "Temp'",
            "VdList",
            "Vdt'",
            "Vdf",
            "Vd",
            "EList",
            "Et'",
            "Ef",
            "E",
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
            "id",
            "Temp1",
            "Temp2",
            "Temp3"
    };
    //符号集
    public static String[] tokens = {"consv", "Na", "Typ", "for", "while", "do", "if", "else", "(", ")", "&&", "||", "!", "<", "<=", ">", ">=", "==", "!=", "+", "-", "*", "/", "%", ";", ",", "{", "}", "$"};
    //语法驱动表
    public static String[][] actionMap = {
            {"pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "error", "pop;push Stmt,Stmt';printf0", "error", "error", "error", "pop;push Stmt,Stmt';printf0", "error", "error", "error", "error", "error", "error", "error", "pop;push Stmt,Stmt';printf0", "error", "error", "error", "error", "error", "pop;push Stmt,Stmt';printf0", "error", "error"},
            {"pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;push Stmt,Stmt';printf0", "pop;", "pop;push Stmt,Stmt';printf0", "pop;", "pop;", "pop;", "pop;push Stmt,Stmt';printf0", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push Stmt,Stmt';printf0", "pop;", "pop;", "pop;", "pop;printf1", "pop;", "pop;push Stmt,Stmt';printf0", "pop;printf1", "pop;printf1"},
            {"pop;push E;SegTypeE", "pop;push Funcall;SegTypeCall", "pop;push Typ,Nadef;SegTypeDef", "pop;push for,(,ForstList,semi,E,semi,ForetList,),Block;SegTypeFor", "pop;push while,(,E,),Block;SegTypeWhile", "pop;push do,Block,while,(,E,),semi;SegTypeDo", "pop;push If,Else';SegTypeElse", "error", "pop;push E;SegTypeE", "error", "error", "error", "pop;push E;SegTypeE", "error", "error", "error", "error", "error", "error", "error", "pop;push E;SegTypeE", "error", "error", "error", "error", "error", "pop;push Block;SegTypeBlock", "error", "error"},
            {"pop;", "pop;push Forst,Forst'", "pop;push Forst,Forst'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push comma,Forst,Forst'", "pop;", "pop;", "pop;"},
            {"error", "pop;push Na,=,E", "pop;if2eqpush Na,=,Typ,Na,=,E;if2eqpush Na,semi,Typ,Na;if2eqpush Na,comma,Typ,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;push Foret,Foret'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push comma,Foret,Foret'", "pop;", "pop;", "pop;"},
            {"error", "pop;ifeqpush =,Na,=,E;ifeqpush (,Na,(,EList,)", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push {,Stmts,}", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "pop;push if,(,E,),Stmt", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push else,Stmt", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"error", "pop;ifeqpush =,Na,=,E,semi;ifnepush =,Na,Tempdef',semi", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;pop;push (,VdList,),Stmt;SegTypeFunDef", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"error", "pop;ifeqpush =,Na,=,E,semi;ifnepush =,Na,Temp',semi", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "error", "error", "pop;pop;push (,EList,),semi;SegTypeFunCall", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;push Vdf,Vdt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push comma,Vdf", "pop;", "pop;", "pop;"},
            {"error", "error", "pop;push Vd,Vdt'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "pop;push Typ,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Ef,Et'", "pop;push Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push Ef,Et'", "pop;", "pop;", "pop;", "pop;push Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push comma,Ef", "pop;", "pop;", "pop;"},
            {"pop;push E,Et'", "pop;push E,Et'", "error", "error", "error", "error", "error", "error", "pop;push E,Et'", "error", "error", "error", "pop;push E,Et'", "error", "error", "error", "error", "error", "error", "error", "pop;push E,Et'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Le", "pop;push Le", "error", "error", "error", "error", "error", "error", "pop;push Le", "error", "error", "error", "pop;push Le", "error", "error", "error", "error", "error", "error", "error", "pop;push Le", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Lf,Lt'", "pop;push Lf,Lt'", "error", "error", "error", "error", "error", "error", "pop;push Lf,Lt'", "error", "error", "error", "pop;push !,Le", "error", "error", "error", "error", "error", "error", "error", "pop;push Lf,Lt'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push &&,Lf,Lt'", "pop;push ||,Lf,Lt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push Ce", "pop;push Ce", "error", "error", "error", "error", "error", "error", "pop;push (,Le,),Temp1", "error", "error", "error", "pop;push !,Lf", "error", "error", "error", "error", "error", "error", "error", "pop;push Ce", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Cf,Ct'", "pop;push Cf,Ct'", "error", "error", "error", "error", "error", "error", "pop;push Cf,Ct'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push Cf,Ct'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push <,Cf,Ct'", "pop;push <=,Cf,Ct'", "pop;push >,Cf,Ct'", "pop;push >=,Cf,Ct'", "pop;push ==,Cf,Ct'", "pop;push !=,Cf,Ct'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push Fe", "pop;push Fe", "error", "error", "error", "error", "error", "error", "pop;push (,Ce,),Temp2", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push Fe", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Ft,Fe'", "pop;push Ft,Fe'", "error", "error", "error", "error", "error", "error", "pop;push Ft,Fe'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push -,Fe", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push +,Ft,Fe'", "pop;push -,Ft,Fe'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push F,Ft'", "pop;push F,Ft'", "error", "error", "error", "error", "error", "error", "pop;push (,Fe,),Temp3", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push *,F,Ft'", "pop;push /,F,Ft'", "pop;push %,F,Ft'", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push id", "pop;push id", "error", "error", "error", "error", "error", "error", "pop;push (,Fe,),Temp3", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push consv", "pop;ifeqpush (,Na,(,EList,);ifnepush (,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push &&,Lf,Lt'", "pop;push ||,Lf,Lt'", "pop;push !,Le", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "pop;push Temp2", "error"},
            {"pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push <,Cf,Ct'", "pop;push <=,Cf,Ct'", "pop;push >,Cf,Ct'", "pop;push >=,Cf,Ct'", "pop;push ==,Cf,Ct'", "pop;push !=,Cf,Ct'", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "pop;push Temp3", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push !=,Cf,Ct'", "pop;push +,Ft',Fe'", "pop;push *,F,Ft'", "pop;push /,F,Ft'", "pop;push %,F,Ft'", "pop;", "pop;", "pop;", "pop;", "pop;"},
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
            System.out.println("[stackTop,token]=[" + stackTop + "," + token + "]");
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
                || token.equals("(") || token.equals(")")
                || token.equals(",")) {
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
        } else if (token.equals("&&") || token.equals("||")) {
            level = 1;
        } else if (token.equals("!")) {
            level = 2;
        } else if (token.equals("<") || token.equals("<=")
                || token.equals(">") || token.equals(">=")
                || token.equals("==") || token.equals("!=")) {
            level = 3;
        } else if (token.equals("+") || token.equals("-")) {
            level = 4;
        } else if (token.equals("*") || token.equals("/") || token.equals("%")) {
            level = 5;
        } else if (token.equals("(")) {
            level = 6;
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
        if (",".equals(operate2) && ",".equals(operate1)) {
            return 0;
        } else if (",".equals(operate2)) {
            //+, 先进行operate1计算
            return -1;
        } else if (",".equals(operate1)) {
            //,+ 先进行operate2压栈
            return 1;
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
        if (",".equals(op)) {
            return valStack.pop();
        }
        if (1 > valStack.size()) {
            System.out.println("error val length < 1");
        }
        String val2 = valStack.pop();
        String val1 = null;
        if (!valStack.isEmpty() && !"!".equals(op)) {
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
        } else if (op.equals("&&") || op.equals("||")) {
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
        } else if (op.equals("!")) {
            boolean b2 = false;
            if ("true".equals(val2) || "false".equals(val2)) {
                b2 = "true".equals(val2);
            } else {
                System.out.println("error val1 is not boolean");
            }
            if (op.equals("!")) {
                result = String.valueOf(!b2);
            }
        }
        return result;
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

    public static List<String> operateCommandList = new ArrayList<>();

    public static void translationUnit() throws IOException {
        String symbol = null;
        String token = getToken();
        StringBuilder symbolLine = new StringBuilder("");
        boolean isError = false;
        do {
            symbol = tokenToSymbol(token);
            if ("$".equals(symbol) && stack.isEmpty()) {
                System.out.println("语法解析结束！");
                return;
                //是结束符 pop 操作符栈 进行运算
//                while (!opStack.isEmpty()) {
//                    String op = opStack.pop();
//                    //运算
//                    String result = operate(op);
//                    if (",".equals(op)) {
//                        System.out.print("输出：" + result + op);
//                    } else {
//                        //结果入栈
//                        valStack.push(result);
//                    }
//                }
//                System.out.println("解析：" + symbolLine.toString());
//                if (!valStack.isEmpty()) {
//                    String val = valStack.pop();
//                    for (int i = 0; i < operateCommandList.size(); i++) {
//                        System.out.println(operateCommandList.get(i));
//                    }
//                    System.out.println("result = " + val);
//                }
//                operateCommandList.clear();
//                symbolLine = new StringBuilder("");
//                token = getToken();
            } else if (!symbol.equals("") && stack.isEmpty()) {
                stack.push(topSym);
            } else if (compare(stack.getTop(), symbol)) {
                //System.out.print(token);
                symbolLine.append(token);
                stack.pop();
                if ("Stmt'".equals(stack.getTop())) {
                    System.out.println("语句解析结束：" + symbolLine.toString());
                    symbolLine = new StringBuilder("");
                } else if ("Lt'".equals(stack.getTop())) {
                    System.out.println("E结束：" + symbolLine.toString());
                    symbolLine = new StringBuilder("");
                }
                //ForstForst'
                //ForetForet'
                //do Block while(E);
                //while(E) Block
                //TypNadef
                //Funcall
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
                            } else if (0 == level && ",".equals(opStack.getTop())) {
                                opStack.push(token);
                                flag = false;
                            } else {
                                //运算
                                String result = operate(opStack.pop());
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
                    } else if (command.contains("if2eqpush")) {
                        //获取下一个token，
                        String nextToken1 = getToken();
                        String nextToken2 = getToken();
                        if (null != nextToken1 && null != nextToken2) {
                            String symbol1 = tokenToSymbol(nextToken1);
                            String symbol2 = tokenToSymbol(nextToken2);
                            tokenStack.push(nextToken2);
                            String[] param2 = param.split(",");
                            param2[1] = param2[1].replaceAll("comma", ",");
                            param2[1] = param2[1].replaceAll("semi", ";");
                            if (symbol1.equals(param2[0]) && symbol2.equals(param2[1])) {
                                for (int j = param2.length - 1; j > 1; j--) {
                                    stack.push(param2[j]);
                                }
                            }
                        }
                        if (null != nextToken1) {
                            tokenStack.push(nextToken1);
                        }
                    } else if (command.contains("ifeqpush")) {
                        //获取下一个token，
                        String nextToken = getToken();
                        if (null != nextToken) {
                            tokenStack.push(nextToken);
                            String[] param2 = param.split(",");
                            if (nextToken.equals(param2[0])) {
                                for (int j = param2.length - 1; j > 0; j--) {
                                    stack.push(param2[j]);
                                }
                            }
                        }
                    } else if (command.contains("ifnepush")) {
                        //获取下一个token，
                        String nextToken = getToken();
                        if (null != nextToken) {
                            tokenStack.push(nextToken);
                        }
                        String[] param2 = param.split(",");
                        if (null == nextToken || !nextToken.equals(param2[0])) {
                            for (int j = param2.length - 1; j > 0; j--) {
                                stack.push(param2[j]);
                            }
                        }
                    } else if (command.contains("push")) {
                        stack.push(param);
                    } else if (command.contains("printf0")) {
                        System.out.println("语句解析开始");
                    } else if (command.contains("printf1")) {
//                        System.out.println("语句解析结束2：" + symbolLine.toString());
//                        symbolLine = new StringBuilder("");
                    } else if (command.contains("SegType")) {
                        switch (command) {
                            case "SegTypeE":
                                System.out.println(command);
                                break;
                            case "SegTypeBlock":
                                System.out.println(command);
                                break;
                            case "SegTypeIf":
                                System.out.println(command);
                                break;
                            case "SegTypeDo":
                                System.out.println(command);
                                break;
                            case "SegTypeWhile":
                                System.out.println(command);
                                break;
                            case "SegTypeFor":
                                System.out.println(command);
                                break;
                            case "SegTypeDef"://变量声明、变量声明赋值
                                System.out.println(command);
                                break;
                            case "SegTypeFunDef"://函数定义
                                System.out.println(command);
                                break;
                            case "SegTypeCall"://赋值、表达式
                                System.out.println(command);
                                break;
                            case "SegTypeFunCall"://函数调用
                                System.out.println(command);
                                break;
                            default:
                                System.out.println(command);
                        }
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
