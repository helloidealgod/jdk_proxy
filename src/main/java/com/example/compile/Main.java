package com.example.compile;

import java.io.*;

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

    public static Stack stack = new Stack();

    public static void main(String[] args) {
        try {

            File f = new File("");
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/main.c"));
            pr = new PushbackReader(reader);
            translationUnit();
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getToken() throws IOException {
        String token1 = stack.getToken();
        if (null != token1) {
            return token1;
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

    /**
     * <translationUnit>:={<externalDeclaration>}<TK_EOF>
     *
     * @throws IOException
     */
    public static void translationUnit() throws IOException {
        String token;
        while ((token = getToken()) != null) {
//            System.out.println(token);
            externalDeclaration(token);
        }
    }

    public static void externalDeclaration(String token) throws IOException {
        int i = 0;
        if (includeHandle(token)) {

        } else if (blsmdy(token)) {

        } else if (0 < (i = functionDeclaration(token))) {
            if (2 == i) {
                //函数体
                functionDefinition(token);
            }
        } else {
            stack.empty();
        }
        //functionProcess(token);

    }

    /**
     * <functionDefinition>:=<typeSpecifier><declarator><functionBody>
     * 函数定义：=类型 声明 函数体
     */
    public static void functionDefinition(String token) throws IOException {
        //变量声明或初始化
        //变量赋值
        //if else
        //for
        //do while
        token = getToken();
        if ("{".equals(token)) {
            stack.push(token);
            token = getToken();
            do {
                if ("}".equals(token)) {
                    stack.push(token);
                    stack.print();
                    break;
                }
                blsmdy(token);
                token = getToken();
            }while (true);
        }
    }

    /**
     * <functionDefinition>:=<typeSpecifier><declarator>;
     * 函数声明：=类型 声明
     */
    public static int functionDeclaration(String token) throws IOException {
        int i = 1;
        token = getToken();
        if (token.matches("int|float|char|long|")) {
            stack.push(token);
            token = getToken();
            while (true) {
                //变量名、函数名
                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                    stack.push(token);
                    token = getToken();
                    if (token.matches("\\(")) {
                        //函数参数列表
                        stack.push(token);
                        token = getToken();
                        if (")".equals(token)) {
                            //没有入参
                            stack.push(token);
                            token = getToken();
                            if (";".equals(token)) {
                                //函数声明
                                stack.print();
                                break;
                            } else if ("{".equals(token)) {
                                //函数体开始
                                pr.unread('{');
                                return 2;
                            }
                        } else if (token.matches("int|float|char|long|")) {
                            //入参开始
                            do {
                                stack.push(token);
                                token = getToken();
                                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                                    //入参名
                                    stack.push(token);
                                    token = getToken();
                                    if (",".equals(token)) {
                                        stack.push(token);
                                        token = getToken();
                                        if (!token.matches("int|float|char|long|")) {
                                            // 逗号后不是类型
                                            //todo ... 可变入参
                                            System.out.println("error");
                                            i = -1;
                                            break;
                                        }
                                    } else if (")".equals(token)) {
                                        //入参结束
                                        stack.push(token);
                                        token = getToken();
                                        if (";".equals(token)) {
                                            //函数声明
                                            stack.print();
                                            break;
                                        } else if ("{".equals(token)) {
                                            //函数体
                                            pr.unread('{');
                                            return 2;
                                        }
                                    }
                                } else if ("*".equals(token)) {
                                }
                            } while (true);
                        }
                        break;
                    } else if (token.matches("=")) {
                        stack.failed();
                        i = -1;
                        break;
                    } else if (token.matches("\\[")) {
                        stack.failed();
                        i = -1;
                        break;
                    } else if (token.matches(",")) {
                        stack.failed();
                        i = -1;
                        break;
                    } else if (token.matches(";")) {
                        stack.failed();
                        i = -1;
                        break;
                    }
                } else if ("*".equals(token)) {
                    stack.push(token);
                    token = getToken();
                } else {
                    break;
                }
            }
        }
        return i;
    }

    private static void functionProcess(String token) throws IOException {
        if (token.matches("int|float|char|long|")) {
            stack.push(token);
            token = getToken();
            while (true) {
                //变量名、函数名
                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                    stack.push(token);
                    token = getToken();
                    if (token.matches("\\(")) {
                        //函数参数列表
                        stack.push(token);
                        token = getToken();
                        if (")".equals(token)) {
                            stack.push(token);
                            token = getToken();
                            if (";".equals(token)) {
                                stack.print();
                                break;
                            } else if ("{".equals(token)) {
                                stack.push(token);
                                token = getToken();
                                if (token.matches("int|float|char|long|")) {

                                }
                            }
                        } else if (token.matches("int|float|char|long|")) {
                            do {
                                stack.push(token);
                                token = getToken();
                                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                                    stack.push(token);
                                    token = getToken();
                                    if (",".equals(token)) {
                                        stack.push(token);
                                        token = getToken();
                                        if (!token.matches("int|float|char|long|")) {
                                            System.out.println("error");
                                            break;
                                        }
                                    } else if (")".equals(token)) {
                                        stack.push(token);
                                        token = getToken();
                                        if (";".equals(token)) {
                                            stack.print();
                                            break;
                                        } else if ("{".equals(token)) {

                                        }
                                    }
                                } else if ("*".equals(token)) {
                                }
                            } while (true);
                        }
                        //函数 functionBody()
                        break;
                    } else if (token.matches("=")) {
                        stack.failed();
                        ;
                        break;
                    } else if (token.matches("\\[")) {
                        stack.failed();
                        ;
                        break;
                    } else if (token.matches(",")) {
                        stack.failed();
                        ;
                        break;
                    } else if (token.matches(";")) {
                        stack.failed();
                        ;
                        break;
                    }
                } else if ("*".equals(token)) {
                    stack.push(token);
                    token = getToken();
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 变量声明【定义】
     *
     * @param token
     * @return
     * @throws IOException
     */
    private static boolean blsmdy(String token) throws IOException {
        boolean success = false;
        if (token.matches("int|float|char|long|")) {
            //记录类型
            stack.push(token);
            token = getToken();
            while (true) {
                //变量名、函数名
                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                    stack.push(token);
                    token = getToken();
                    if (token.matches("\\(")) {
                        stack.push(token);
                        stack.failed();
                        break;
                    } else if (token.matches("=")) {
                        //变量 =
                        stack.push(token);
                        //取数
                        token = getToken();
                        stack.push(token);
                        //取标点符号
                        token = getToken();
                        if (token.matches(";")) {
                            stack.print();
                            success = true;
                            break;
                        } else if (token.matches(",")) {
                            stack.push(token);
                            token = getToken();
                        }
                    } else if (token.matches("\\[")) {
                        do {
                            stack.push(token);
                            token = getToken();
                            stack.push(token);
                            token = getToken();
                            if ("]".equals(token)) {
                                stack.push(token);
                                token = getToken();
                                if (";".equals(token)) {
                                    stack.print();
                                    success = true;
                                    break;
                                }
                            } else if (";".equals(token)) {
                                stack.print();
                                success = true;
                                break;
                            }
                        } while (true);
                        break;
                    } else if (token.matches(",")) {
                        stack.push(token);
                        token = getToken();
                    } else if (token.matches(";")) {
                        stack.print();
                        success = true;
                        break;
                    }
                } else if ("*".equals(token)) {
                    stack.push(token);
                    token = getToken();
                } else {
                    break;
                }
            }
        }
        return success;
    }

    /**
     * #include<>  #include ""
     *
     * @param token
     * @return
     * @throws IOException
     */
    private static boolean includeHandle(String token) throws IOException {
        boolean success = false;
        if (token.matches("#include")) {
            stack.push(token);
            token = getToken();
            if (token.matches("<")) {
                token = getToken();
                stack.push(token);
                token = getToken();
                if (token.matches(">")) {
                    success = true;
                } else {
                    System.out.print("error:");
                    stack.print();
                }
            } else if (token.startsWith("\"")) {
                stack.push(token);
                success = true;
            } else {
                System.out.print("error:");
                stack.print();
            }
            if (success) {
                stack.print();
            }
        }
        return success;
    }

    /**
     * 解析外部声明
     * <externalDeclaration>:={<functionDefinition>}|{<declaration>}
     * 函数定义或变量声明
     *
     * @throws IOException
     */
    public static void externalDeclaration0(String token) throws IOException {
        StringBuilder sb = new StringBuilder("");
        if (token.matches("#include")) {
            /**
             * #include <stdio.h>
             * #include "hello.h"
             * begin
             */
            sb.append(token);
            sb.append(" ");
            token = getToken();
            if (token.matches("<")) {
                token = getToken();
                sb.append(token);
                token = getToken();
                if (token.matches(">")) {
                    System.out.println(sb.toString());
                } else {
                    System.out.println("error");
                }
            } else if (token.startsWith("\"")) {
                sb.append(token);
                System.out.println(sb.toString());
            } else {
                System.out.println("error");
            }
            /**
             * #include <stdio.h>
             * #include "hello.h"
             * end
             */
        } else if (token.matches("int|float|char|long|")) {
            //记录类型
            sb.append(token);
            sb.append(" ");
            token = getToken();
            while (true) {
                //变量名、函数名
                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                    sb.append(token);
                    token = getToken();
                    if (token.matches("\\(")) {
                        //函数参数列表
                        sb.append(token);
                        token = getToken();
                        if (")".equals(token)) {
                            sb.append(token);
                            token = getToken();
                            if (";".equals(token)) {
                                System.out.println(sb.toString());
                                break;
                            } else if ("{".equals(token)) {
                                /**
                                 * 函数体
                                 * 1 变量声明、定义
                                 * 2 变量引用
                                 * 3 函数调用
                                 * 4 for、if、while、Switch等语句（顺序、循环、分支、跳转）
                                 */
                                sb.append(token);
                                token = getToken();
                                if (token.matches("int|float|char|long|")) {

                                }
                            }
                        } else if (token.matches("int|float|char|long|")) {
                            do {
                                sb.append(token);
                                sb.append(" ");
                                token = getToken();
                                if (token.matches("[_A-Za-z][_A-Za-z0-9]*")) {
                                    sb.append(token);
                                    token = getToken();
                                    if (",".equals(token)) {
                                        sb.append(token);
                                        token = getToken();
                                        if (!token.matches("int|float|char|long|")) {
                                            System.out.println("error");
                                            break;
                                        }
                                    } else if (")".equals(token)) {
                                        sb.append(token);
                                        token = getToken();
                                        if (";".equals(token)) {
                                            System.out.println(sb.toString());
                                            break;
                                        } else if ("{".equals(token)) {

                                        }
                                    }
                                } else if ("*".equals(token)) {
//                                    sb.append(token);
//                                    token = getToken();
                                }
                            } while (true);
                        }
                        //函数 functionBody()
                        break;
                    } else if (token.matches("=")) {
                        //变量 =
                        sb.append(token);
                        //取数
                        token = getToken();
                        sb.append(token);
                        //取标点符号
                        token = getToken();
                        if (token.matches(";")) {
                            System.out.println(sb.toString());
                            break;
                        } else if (token.matches(",")) {
                            sb.append(token);
                            token = getToken();
                        }
                    } else if (token.matches("\\[")) {
                        do {
                            sb.append(token);
                            token = getToken();
                            sb.append(token);
                            token = getToken();
                            if ("]".equals(token)) {
                                sb.append(token);
                                token = getToken();
                                if (";".equals(token)) {
                                    System.out.println(sb.toString());
                                    break;
                                }
                            } else if (";".equals(token)) {
                                System.out.println(sb.toString());
                                break;
                            }
                        } while (true);
                        break;
                    } else if (token.matches(",")) {
                        sb.append(token);
                        token = getToken();
                    } else if (token.matches(";")) {
                        System.out.println(sb.toString());
                        break;
                    }
                } else if ("*".equals(token)) {
                    /**
                     * 类型之后接*表示指针类型
                     */
                    sb.append(token);
                    token = getToken();
                } else {
                    break;
                }
            }
        }
    }


    /**
     * <declaration>:=<typeSpecifier><TK_SEMICOLON> | <typeSpecifier><initDeclaratorList><TK_SEMICOLON>
     * 类型 ；| 类型 变量名列表 ；
     */
    public static void declaration() {

    }

    /**
     * <initDeclaratorList>:=<initDeclarator>{<TK_COMMA><initDeclarator>}
     * :=变量名{=初始值}{,变量名{=初始值}}
     */
    public static void initDeclaratorList() {

    }

    /**
     * <initDeclarator>:=<declarator>{<TK_ASSIGN><initializer>}
     * :=变量名{=初始值}
     */
    public static void initDeclarator() {

    }
}
