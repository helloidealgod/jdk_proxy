package com.example.compile.tabledrive2;

import com.example.compile.tabledrive2.impl.Declarator;
import com.example.compile.tabledrive2.impl.FunctionDeclarator;
import com.example.compile.tabledrive2.impl.FunctionDefinition;
import com.example.compile.tabledrive2.impl.InitDeclarator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Compile {
    public static PushbackReader pr;
    /**
     * 关键字
     * 分隔符
     * 算术运算符
     * 逻辑运算符
     * 表达式
     */

    public static String[] keyword = {"int", "void", "float", "char"};
    public static char[] delimiter = {' ', ',', ';', '=', '{', '}', '(', ')', '\n'};
    public static Stack stack = new Stack();

    public static List<Keyword> keywordList = new ArrayList<>();
    /**
     * <initDeclarator>:=<declarator>{<TK_ASSIGN><initializer>}
     * :=变量名{=初始值}
     */
    /**
     * <initDeclaratorList>:=<initDeclarator>{<TK_COMMA><initDeclarator>}
     * :=变量名{=初始值}{,变量名{=初始值}}
     */
    /**
     * <declaration>:=<typeSpecifier><TK_SEMICOLON> | <typeSpecifier><initDeclaratorList><TK_SEMICOLON>
     * 类型 ；| 类型 变量名列表 ；
     */
    /**
     * 解析外部声明
     * <externalDeclaration>:={<functionDefinition>}|{<declaration>}
     * 函数定义或变量声明
     *
     * @throws IOException
     */
    /**
     * <functionDefinition>:=<typeSpecifier><declarator>;
     * 函数声明：=类型 声明
     */
    /**
     * <functionDefinition>:=<typeSpecifier><declarator><functionBody>
     * 函数定义：=类型 声明 函数体
     */
    /**
     * <translationUnit>:={<externalDeclaration>}<TK_EOF>
     *
     * @throws IOException
     */

    public static void main(String[] args) {
        try {

            File f = new File("");
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/c.c"));
            pr = new PushbackReader(reader);
            StringBuilder token = new StringBuilder("");
            int read;
            char c;
            while ((read = pr.read()) != -1) {
                c = (char) read;
                if (isDelimiter(c)) {
                    if (0 < token.length()) {
                        process(token.toString());
                    }
                    token.setLength(0);
                    if (' ' != c && '\n' != c) {
                        process(c + "");
                    }
                } else {
                    token.append(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDelimiter(char c) {
        for (char item : delimiter) {
            if (item == c) {
                return true;
            }
        }
        return false;
    }

    public static void process(String token) {
        if (token.matches("int|float|char|long|void")) {
            stack.push(token, "@TYPE");
        } else if (token.matches(";|\\)|}|]|\\{|\\(")) {
            stack.push(token, "@" + token);
            matchSymbol(stack);
        } else if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
            stack.push(token, "@KEYWORD");
        } else if (token.matches("[0-9]*")) {
            stack.push(token, "@CONSTANT");
        } else {
            stack.push(token, "@" + token);
            if (",".equals(token)) {
                matchSymbol(stack);
            }
        }
    }

    public static Analysis[] analysisList = {
            new Declarator(),

            new InitDeclarator(),
            new InitDeclarator(),
            new FunctionDeclarator(),
            new FunctionDeclarator(),
            new FunctionDeclarator(),
            new FunctionDefinition(),
            new FunctionDefinition(),
            new Declarator()
    };
    public static String[] symList = {
            "@TYPE@KEYWORD@;"
            , "@TYPE@KEYWORD@=@CONSTANT@;"
            , "@TYPE@KEYWORD@=@CONSTANT@,"

            , "@TYPE@KEYWORD@(@TYPE@KEYWORD@,"
            , "@TYPE@KEYWORD@(@TYPE@KEYWORD@)"
            , "@TYPE@KEYWORD@(@)"
            , "@FUNCTION@;"
            , "@FUNCTION@{@}"
            , "@TYPE@KEYWORD@,"
    };

    public static void matchSymbol(Stack stack) {
        String symbol = stack.toString();
        boolean isMatch = false;
        for (int i = 0; i < symList.length; i++) {
            if (symbol.endsWith(symList[i])) {
                analysisList[i].analysis(stack);
            }
        }
        if (!isMatch) {

        }
    }

    public static int countOccurrences(String str, String subStr) {
        int count = 0;
        int index = str.indexOf(subStr);
        while (index != -1) {
            count++;
            index = str.indexOf(subStr, index + 1);
        }
        return count;
    }
}
