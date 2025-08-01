package com.example.compile.table3;

import com.example.compile.table3.segment.Segment;
import com.example.compile.table3.segment.impl.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static InfoStack stack = new InfoStack();
    public static Stack tokenStack = new Stack();
    // 操作符栈
    public static Stack opStack = new Stack();
    // 值栈
    public static StackE valStack = new StackE();
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
            {"pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "error", "pop;push 0,Stmt,Stmt'", "error", "error", "error", "pop;push 0,Stmt,Stmt'", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Stmt,Stmt'", "error", "error", "error", "error", "error", "pop;push 0,Stmt,Stmt'", "pop;", "error"},
            {"pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;push 0,Stmt,Stmt'", "pop;", "pop;push 0,Stmt,Stmt'", "pop;", "pop;", "pop;", "pop;push 0,Stmt,Stmt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,Stmt,Stmt'", "pop;", "pop;", "pop;", "pop", "pop;", "pop;push 0,Stmt,Stmt'", "pop", "pop"},
            {"pop;push 0,E;SegTypeE", "pop;push 0,Funcall;SegTypeCall", "pop;push 0,Typ,Nadef;SegTypeDef", "pop;push 0,for,(,ForstList,semi,E,semi,ForetList,),Block;SegTypeFor", "pop;push 0,while,(,E,),Block;SegTypeWhile", "pop;push 0,do,Block,while,(,E,),semi;SegTypeDo", "pop;push 0,If,Else';SegTypeElse", "error", "pop;push 0,E;SegTypeE", "error", "error", "error", "pop;push 0,E;SegTypeE", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,E;SegTypeE", "error", "error", "error", "error", "error", "pop;push 0,Block;SegTypeBlock", "pop;", "error"},
            {"pop;", "pop;push 0,Forst,Forst'", "pop;push 0,Forst,Forst'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,comma,Forst,Forst'", "pop;", "pop;", "pop;"},
            {"error", "pop;push 0,Na,=,E", "pop;push 2,eq,Na,=,Typ,Na,=,E;push 2,eq,Na,semi,Typ,Na;push 2,eq,Na,comma,Typ,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;push 0,Foret,Foret'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,comma,Foret,Foret'", "pop;", "pop;", "pop;"},
            {"error", "pop;push 1,eq,=,Na,=,E;push 1,eq,(,Na,(,EList,)", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,{,Stmts,}", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "pop;push 0,if,(,E,),Stmt", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,else,Stmt", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"error", "pop;push 1,eq,=,Na,=,E,semi;push 1,ne,=,Na,Tempdef',semi", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;pop;push 0,(,VdList,),Stmt;SegTypeFunDef", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"error", "pop;push 1,eq,=,Na,=,E,semi;push 1,ne,=,Na,Temp',semi", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "error", "error", "error", "error", "error", "error", "pop;pop;push 0,(,EList,),semi;SegTypeFunCall", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;push 0,Vdf,Vdt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,comma,Vdf", "pop;", "pop;", "pop;"},
            {"error", "error", "pop;push 0,Vd,Vdt'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"error", "error", "pop;push 0,Typ,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Ef,Et'", "pop;push 0,Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,Ef,Et'", "pop;", "pop;", "pop;", "pop;push 0,Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,Ef,Et'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,comma,Ef", "pop;", "pop;", "pop;"},
            {"pop;push 0,E,Et'", "pop;push 0,E,Et'", "error", "error", "error", "error", "error", "error", "pop;push 0,E,Et'", "error", "error", "error", "pop;push 0,E,Et'", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,E,Et'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Le", "pop;push 0,Le", "error", "error", "error", "error", "error", "error", "pop;push 0,Le", "error", "error", "error", "pop;push 0,Le", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Le", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Lf,Lt'", "pop;push 0,Lf,Lt'", "error", "error", "error", "error", "error", "error", "pop;push 0,Lf,Lt'", "error", "error", "error", "pop;push 0,!,Le", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Lf,Lt'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,&&,Lf,Lt'", "pop;push 0,||,Lf,Lt'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push 0,Ce", "pop;push 0,Ce", "error", "error", "error", "error", "error", "error", "pop;push 0,(,Le,),Temp1", "error", "error", "error", "pop;push 0,!,Lf", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Ce", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Cf,Ct'", "pop;push 0,Cf,Ct'", "error", "error", "error", "error", "error", "error", "pop;push 0,Cf,Ct'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Cf,Ct'", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,<,Cf,Ct'", "pop;push 0,<=,Cf,Ct'", "pop;push 0,>,Cf,Ct'", "pop;push 0,>=,Cf,Ct'", "pop;push 0,==,Cf,Ct'", "pop;push 0,!=,Cf,Ct'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push 0,Fe", "pop;push 0,Fe", "error", "error", "error", "error", "error", "error", "pop;push 0,(,Ce,),Temp2", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,Fe", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Ft,Fe'", "pop;push 0,Ft,Fe'", "error", "error", "error", "error", "error", "error", "pop;push 0,Ft,Fe'", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "pop;push 0,-,Fe", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,+,Ft,Fe'", "pop;push 0,-,Ft,Fe'", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push 0,F,Ft'", "pop;push 0,F,Ft'", "error", "error", "error", "error", "error", "error", "pop;push 0,(,Fe,),Temp3", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,*,F,Ft'", "pop;push 0,/,F,Ft'", "pop;push 0,%,F,Ft'", "pop;", "pop;", "pop;", "pop;", "pop;"},
            {"pop;push 0,id", "pop;push 0,id", "error", "error", "error", "error", "error", "error", "pop;push 0,(,Fe,),Temp3", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,consv", "pop;push 1,eq,(,Na,(,EList,);push 1,ne,(,Na", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error", "error"},
            {"pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,&&,Lf,Lt'", "pop;push 0,||,Lf,Lt'", "pop;push 0,!,Le", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "pop;push 0,Temp2", "error"},
            {"pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,<,Cf,Ct'", "pop;push 0,<=,Cf,Ct'", "pop;push 0,>,Cf,Ct'", "pop;push 0,>=,Cf,Ct'", "pop;push 0,==,Cf,Ct'", "pop;push 0,!=,Cf,Ct'", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "pop;push 0,Temp3", "error"},
            {"pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;", "pop;push 0,!=,Cf,Ct'", "pop;push 0,+,Ft',Fe'", "pop;push 0,*,F,Ft'", "pop;push 0,/,F,Ft'", "pop;push 0,%,F,Ft'", "pop;", "pop;", "pop;", "pop;", "pop;"},
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
    public static SegmentExprOp operate(String op) {
        if ("(".equals(op)) {
            return null;
        } else if (")".equals(op)) {
            if ("(".equals(opStack.getTop())) {
                opStack.pop();
            } else {
                System.out.println("error");
            }
            return null;
        }
        if (",".equals(op)) {
            return valStack.pop();
        }
        if (1 > valStack.size()) {
            System.out.println("error val length < 1");
        }
        SegmentExprOp e2 = valStack.pop();
        SegmentExprOp e1 = null;
        if (!valStack.isEmpty() && !"!".equals(op)) {
            e1 = valStack.pop();
        }
        if ("-".equals(op) && null == e1) {
            e1 = new SegmentExprOp("int", "0", "0");
        }
        SegmentExprOp result = operate(op, e1, e2);
        return result;
    }

    public static SegmentExprOp operate(String op, SegmentExprOp val1, SegmentExprOp val2) {
        SegmentExprOp result = null;
        if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%")
                || op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">=") || op.equals("==") || op.equals("!=")) {

            Integer int1 = val1.value == null ? null : Integer.valueOf(val1.value);
            Integer int2 = val2.value == null ? null : Integer.valueOf(val2.value);

            if (null != int1 && null != int2) {
                if (op.equals("+")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 + int2));
                } else if (op.equals("-")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 - int2));
                } else if (op.equals("*")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 * int2));
                } else if (op.equals("/")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 / int2));
                } else if (op.equals("%")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 % int2));
                } else if (op.equals("<")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 < int2));
                } else if (op.equals("<=")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 <= int2));
                } else if (op.equals(">")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 > int2));
                } else if (op.equals(">=")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 >= int2));
                } else if (op.equals("==")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 == int2));
                } else if (op.equals("!=")) {
                    result = new SegmentExprOp("int", "", String.valueOf(int1 != int2));
                }
            } else {
                if (val1.op != null) {
                    segmentExprOpList.add(val1);
                }
                if (val2.op != null) {
                    segmentExprOpList.add(val2);
                }
                result = new SegmentExprOp("int", op, val1, val2);
            }
        } else if (op.equals("&&") || op.equals("||")) {
            Boolean b1 = null;
            Boolean b2 = null;
            String s1 = val1.value == null ? "" : val1.value;
            String s2 = val2.value == null ? "" : val2.value;

            if ("true".equals(s1) || "false".equals(s1)) {
                b1 = "true".equals(s1);
            }
            if ("true".equals(s2) || "false".equals(s2)) {
                b2 = "true".equals(s2);
            }
            if (null != b1 && null != b2) {
                if (op.equals("&&")) {
                    result = new SegmentExprOp("Boolean", "", String.valueOf(b1 && b2));
                } else if (op.equals("||")) {
                    result = new SegmentExprOp("Boolean", "", String.valueOf(b1 || b2));
                }
            } else {
                if (val1.op != null) {
                    segmentExprOpList.add(val1);
                }
                if (val2.op != null) {
                    segmentExprOpList.add(val2);
                }
                result = new SegmentExprOp("Boolean", op, val1, val2);
            }
        } else if (op.equals("!")) {
            Boolean b2 = null;
            String s2 = val2.value == null ? "" : val2.value;
            if ("true".equals(s2) || "false".equals(s2)) {
                b2 = "true".equals(s2);
            }
            if (null != b2) {
                result = new SegmentExprOp("Boolean", "", String.valueOf(!b2));
            } else {
                if (val2.op != null) {
                    segmentExprOpList.add(val2);
                }
                result = new SegmentExprOp("Boolean", op, null, val2);
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
    public static List<SegmentExprOp> segmentExprOpList = new ArrayList<>();

    public static Segment segment;

    public static List<NameTable> nameTableList = new ArrayList<>();
    public static Long offset = 0L;

    public static void translationUnit() throws IOException {
        String symbol = null;
        String token = getToken();
        StringBuilder symbolLine = new StringBuilder("");
        boolean isError = false;
        do {
            symbol = tokenToSymbol(token);
            if ("$".equals(symbol) && stack.isEmpty()) {
                System.out.println("语法解析结束！");
                //是结束符 pop 操作符栈 进行运算
                while (!opStack.isEmpty()) {
                    String op = opStack.pop();
                    //运算
                    SegmentExprOp result = operate(op);
                    if (",".equals(op)) {
                        //System.out.print("输出：" + result + op);
                    } else {
                        //结果入栈
                        valStack.push(result);
                    }
                }
                if (!valStack.isEmpty()) {
                    SegmentExprOp val = valStack.pop();
                    segmentExprOpList.add(val);
                }
                if (segmentExprOpList.size() == 1) {
                    SegmentExprOp e = segmentExprOpList.get(0);
                    String var1 = e.value == null ? e.name : e.value;
                    System.out.println(" " + var1);
                }
                for (SegmentExprOp item : segmentExprOpList) {
                    Utils.printSegmentE(item);
                }
                return;
            } else if (!symbol.equals("") && stack.isEmpty()) {
                stack.push(topSym);
            } else if (compare(stack.getTop().name, symbol)) {
                stack.print();
                symbolLine.append(token);
                System.out.println(token);
                stack.pop();
                Info top = stack.getTop();
                doAction(top.name, symbol, token);
                //是数值 压入值栈
                if (null != token && token.matches("\\d*")) {
                    valStack.push(new SegmentExprOp("int", "", token));
                } else if (null != token && token.matches("void|char|short|int|long|float")) {

                } else if (null != token && token.matches("do|for|if|while|else")) {

                } else if (null != token && token.matches("[A-Za-z]+[A-Za-z0-9]*")) {
                    valStack.push(new SegmentExprOp("int", token, null));
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
                String top = stack.getTop().name;
                stack.print();
                top = doAction(top, symbol, token);
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
            for (int i = 0; i < operateCommandList.size(); i++) {
                System.out.println(operateCommandList.get(i));
            }
            System.out.println("=======================error=======================");
        }
    }

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
        if ("{ae}".equals(top)) {
            System.out.println("E end!");
            while (!opStack.isEmpty()) {
                String op = opStack.pop();
                //运算
                SegmentExprOp result = operate(op);
                if (",".equals(op)) {
                } else {
                    valStack.push(result);
                }
            }
            if (!valStack.isEmpty()) {
                SegmentExprOp val = valStack.pop();
                segmentExprOpList.add(val);
            }
            if (segmentExprOpList.size() == 1) {
                SegmentExprOp e = segmentExprOpList.get(0);
                String var1 = e.value == null ? e.name : e.value;
                System.out.println(" " + var1);
            }
            for (SegmentExprOp item : segmentExprOpList) {
                Utils.printSegmentE(item);
            }
            stack.pop();
            return stack.getTop().name;
        } else if ("{atype}".equals(top)) {
            stack.pop();
            int topIndex = stack.stack.size() - 1;
            stack.stack.get(topIndex - 1).type = token;
            stack.stack.get(topIndex - 1).typeWidth = TypeEnum.getWidthByType(token);
            return stack.getTop().name;
        } else if ("{ana0}".equals(top)) {
            stack.pop();
            int topIndex = stack.stack.size() - 1;
            stack.stack.get(topIndex - 4).varName = token;
            return stack.getTop().name;
        } else if ("{ana1}".equals(top)) {
            stack.pop();
            int topIndex = stack.stack.size() - 1;
            stack.stack.get(topIndex - 2).varName = token;
            return stack.getTop().name;
        } else if ("{ana2}".equals(top)) {
            stack.pop();
        } else if ("{ainfo}".equals(top)) {
            Info pop = stack.pop();

        } else if ("{afd}".equals(top)) {
            stack.pop();
        } else {
            return top;
        }
        return top;
    }

    public static NameTable finddNameTable(String name) {
        for (NameTable nameTable : nameTableList) {
            if (nameTable.name.equals(name)) {
                return nameTable;
            }
        }
        return null;
    }

    public static NameTable generaNameTable(String name, String type) {
        NameTable nameTable = new NameTable();
        nameTable.name = name;
        nameTable.type = type;
        nameTable.typeWidth = TypeEnum.getWidthByType(type);
        nameTable.address = offset;
        offset += nameTable.typeWidth;
        nameTableList.add(nameTable);
        return nameTable;
    }
}
