package com.example.compile.iteration;

import com.example.compile.iteration.analysis.Statement;
import com.example.compile.iteration.analysis.Utils;

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

    public static Stack stack = new Stack();

    public static List<Statement> statementList = new ArrayList<>();

    public static Statement statement;

    public static void main(String[] args) {
        try {
            File f = new File("");
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath() + "/src/main/resources/cc/m.c"));
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
     * <翻译单元>::={<外部声明>}<文件结束符>
     *
     * @throws IOException
     */
    public static void translationUnit() throws IOException {
        String token;
        while ((token = getToken()) != null) {
            externalDeclaration(token);
            System.out.println("");
        }
        System.out.println("=======================================");
//        Utils.statementToString(statementList, 0);
    }

    /**
     * <外部声明>::=<函数定义>|<声明>
     * <函数定义>::=<类型区分符><声明符><函数体>
     * <声明>::=<类型区分符>[<初值声明符表>]<分号>
     * <初值声明符表>::=<初值声明符>{<逗号><初值声明符}
     *
     * @param token
     */
    private static void externalDeclaration(String token) throws IOException {
        // 类型区分符
        if (!lxqff(token)) {
            System.out.println("error");
        }
        System.out.print("type:" + token);
        String type = token;
        while (true) {
            statement = new Statement();
            statement.setType(type);

            token = getToken();
            if (";".equals(token)) {
                break;
            }
            // 标识符
            if (!smf(token)) {
                System.out.println("error");
            }
            System.out.print(" name:" + token);
            statement.setName(token);
            token = getToken();
            if (zjsmfhz(token)) {
                token = getToken();
                if ("{".equals(token)) {
                    statement.setStatementType("function");
                    System.out.print(token);
                    token = getToken();
                    if (fhyj(token)) {
                        statementList.add(statement);
                        break;
                    }
                }
            } else {
                if ("=".equals(token)) {
                    token = getToken();
                    System.out.print(" value:" + token);
                    statement.setInitValue(token);
                    token = getToken();
                    if (",".equals(token)) {
                        statementList.add(statement);
                    }
                } else if (",".equals(token)) {
                    statementList.add(statement);
                }
            }
            if (";".equals(token)) {
                statementList.add(statement);
                break;
            }
        }
    }

    /**
     * <函数定义>::=<类型区分符><声明符><函数体>
     *
     * @param token
     */
    private static void hsdy(String token) {

    }

    /**
     * <函数体>::=<复合语句>
     *
     * @param token
     */
    private static void hst(String token) {

    }

    /**
     * <声明>::=<类型区分符>[<初值声明符表>]<分号>
     *
     * @param token
     */
    private static void sm(String token) {

    }

    /**
     * <初值声明符表>::=<初值声明符>{<逗号><初值声明符}
     *
     * @param token
     */
    private static void czsmfb(String token) {

    }

    /**
     * <初值声明符>::=<声明符>|<声明符><赋值运算符><初值符>
     *
     * @param token
     */
    private static void czsmf(String token) {

    }

    /**
     * <类型区分符>::=<void 关键字>|<char 关键字>|<int 关键字> | <结构区分符>
     *
     * @param token
     */
    private static boolean lxqff(String token) throws IOException {
        if (token.matches("void|int|float|char|long|")) {
            return true;
        } else if (jgqff(token)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <结构区分符>::=<struct 关键字><标识符><左大括号><结构声明表><右大括号>| <struct关键字><标识符>
     * <p>
     * <结构声明表>::=<结构声明>{<结构声明>}
     * <结构声明>::=<类型区分符>{<结构声明符表>}<分号>
     * <结构声明符表>::=<声明符>{<逗号><声明符>}
     *
     * @param token
     */
    private static boolean jgqff(String token) throws IOException {
        if (token.matches("struct")) {
            token = getToken();
            if (smf(token)) {
                System.out.print(token + " ");
                token = getToken();
                if ("{".equals(token)) {
                    System.out.print(token + " ");
                    while (true) {
                        token = getToken();
                        if ("}".equals(token)) {
                            System.out.print(token + " ");
                            return true;
                        }
                        if (!lxqff(token)) {
                            System.out.println("error");
                        }
                        System.out.print(token + " ");
                        while (true) {
                            token = getToken();
                            if (!smf(token)) {
                                System.out.println("error");
                            }
                            System.out.print(token + " ");
                            token = getToken();
                            if (",".equals(token)) {
                                System.out.print(token + " ");
                            }
                            if (";".equals(token)) {
                                break;
                            }
                        }
                    }
                } else {
                    stack.push(token);
                    stack.failed();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <声明符>::={<指针>}[<调用约定>][<结构成员对齐>]<直接声明符>
     *
     * @param token
     */
    private static boolean smf(String token) {
        return !token.matches("void|int|float|char|long|") && token.matches("[_A-Za-z][_A-Za-z0-9]*");
    }

    /**
     * <指针>::=<星号>
     *
     * @param token
     */
    private static boolean zz(String token) {
        return false;
    }

    /**
     * <直接声明符>::=<标识符><直接声明符后缀>
     *
     * @param token
     */
    private static boolean zjsmf(String token) {
        return false;
    }

    /**
     * <直接声明符后缀>::={<左中括号><右中括号>|<左中括号><整数常量><右中括号>|<左小括号><右小括号>|<左小括号><形参表><右小括号>}
     *
     * @param token
     */
    private static boolean zjsmfhz(String token) throws IOException {
        if ("[".equals(token)) {
            System.out.print(token);
            token = getToken();
            if (token.matches("\\d+")) {
                System.out.print(token);
                token = getToken();
            }
            if ("]".equals(token)) {
                System.out.print(token);
                return true;
            } else {
                System.out.print("error");
            }
        } else if ("(".equals(token)) {
            System.out.print(token + " ");
            while (true) {
                Statement temp = new Statement();
                token = getToken();
                // 类型区分符
                if (lxqff(token)) {
                    System.out.print(token + " ");
                    temp.setType(token);
                    token = getToken();
                    // 标识符
                    if (!smf(token)) {
                        System.out.println("error");
                    }
                    temp.setName(token);
                    System.out.print(token + " ");
                    token = getToken();

                    statement.formalParameterList.add(temp);
                }
                if (")".equals(token)) {
                    System.out.print(token);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <复合语句>::=<左大括号>{<声明><语句>}<右大括号>
     *
     * @param token
     */
    private static boolean fhyj(String token) throws IOException {
        // <声明>
        while (lxqff(token)) {
            Statement tempStatement = new Statement();
            System.out.print("type:" + token);
            tempStatement.setType(token);
            while (true) {
                token = getToken();
                // 标识符
                if (!smf(token)) {
                    System.out.println("error");
                }
                System.out.print(" name:" + token);
                tempStatement.setName(token);
                token = getToken();
                if (zjsmfhz(token)) {
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.println("error");
                    }
                } else {
                    if ("=".equals(token)) {
                        token = getToken();
                        System.out.print(" value:" + token);
                        tempStatement.setInitValue(token);
                        token = getToken();
                        if (",".equals(token)) {
                            statement.subStatementList.add(tempStatement);
                        }
                    } else if (",".equals(token)) {
                        statement.subStatementList.add(tempStatement);
                    }
                }
                if (";".equals(token)) {
                    statement.subStatementList.add(tempStatement);
                    token = getToken();
                    break;
                }
            }
        }
        // 语句
        while (!"}".equals(token)) {
            if (yj(token)) {
                token = getToken();
            } else {
                System.out.println("error");
            }
        }
        if ("}".equals(token)) {
            System.out.println(token);
            return true;
        }
        return false;
    }

    /**
     * <语句>::={<复合语句>|<if语句>|<for语句>|<break语句>|<continue语句>|<return语句>|<表达式语句>}
     *
     * @param token
     */
    private static boolean yj(String token) throws IOException {
        Statement tempStatement = new Statement();
        if ("break".equals(token)) {
            System.out.println(token);
            tempStatement.setName(token);
            tempStatement.setStatementType(token);
            statement.subStatementList.add(tempStatement);
            token = getToken();
            return true;
        } else if ("continue".equals(token)) {
            System.out.println(token);
            tempStatement.setName(token);
            tempStatement.setStatementType(token);
            statement.subStatementList.add(tempStatement);
            token = getToken();
            return true;
        } else if ("return".equals(token)) {
            System.out.print(token);
            tempStatement.setName(token);
            tempStatement.setStatementType(token);
            statement.subStatementList.add(tempStatement);
            token = getToken();
            if (!";".equals(token)) {
                System.out.print(" ");
                if (bds(token)) {

                }
            }
            System.out.println("");
            return true;
        } else if ("if".equals(token)) {
            //if 语句
            System.out.print(token);
            tempStatement.setName(token);
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                if (bds(token)) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        if (fhyj(token)) {
                            statement.subStatementList.add(tempStatement);
                            return true;
                        }
                    }
                }
            }
        } else if ("for".equals(token)) {
            System.out.print(token);
            tempStatement.setName(token);
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                if (bds(token)) {

                }
                token = getToken();
                if (bds(token)) {

                }
                token = getToken();
                if (bds(token)) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        if (fhyj(token)) {
                            statement.subStatementList.add(tempStatement);
                            return true;
                        }
                    }
                }
            }
        } else if ("while".equals(token)) {
            System.out.print(token);
            tempStatement.setName(token);
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                if (bds(token)) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        if (fhyj(token)) {
                            statement.subStatementList.add(tempStatement);
                            return true;
                        }
                    }
                }
            }
        } else if (bds(token)) {
            return true;
        }
        return false;
    }

    /**
     * <表达式>::=<赋值表达式>{<逗号><赋值表达式>}
     *
     * @param token
     */
    private static boolean bds(String token) throws IOException {
        while (true) {
            if (!fzbds(token)) {
                System.out.println("error");
            }
            token = getToken();
            if (!",".equals(token)) {
                break;
            }
            token = getToken();
        }
        if (";".equals(token)) {
            return true;
        } else {
            stack.push(token);
            stack.failed();
            return true;
        }
    }

    /**
     * <赋值表达式>::=<相等类表达式>|<一元表达式><赋值等号><赋值表达式>
     *
     * @param token
     */
    private static boolean fzbds(String token) throws IOException {
        if (xdlbds(token)) {
            return true;
        } else if (yybds(token)) {
            return true;
        }
        return false;
    }

    /**
     * <相等类表达式>::=<关系表达式>{<等于号><关系表达式>|<不等于号><关系表达式>}
     *
     * @param token
     * @return
     */
    private static boolean xdlbds(String token) throws IOException {
        if (gxbds(token)) {
            token = getToken();
            if ("=".equals(token) || "!=".equals(token)) {
                System.out.print(token);
                token = getToken();
                if (!gxbds(token)) {
                    System.out.println("error");
                }
            } else {
                stack.push(token);
                stack.failed();
            }
            return true;
        }
        return false;
    }

    /**
     * <关系表达式>::=<加减类表达式>{<小于号><加减类表达式>|<大于号><加减类表达式>|<小于等于号><加减类表达式>|<大于等于号><加减类表达式>}
     *
     * @param token
     * @return
     */
    private static boolean gxbds(String token) throws IOException {
        if (jjlbds(token)) {
            token = getToken();
            if ("<".equals(token) || ">".equals(token) || "<=".equals(token) || ">=".equals(token)) {
                System.out.print(token);
                token = getToken();
                if (!jjlbds(token)) {
                    System.out.println("error");
                }
            } else {
                stack.push(token);
                stack.failed();
            }
            return true;
        }
        return false;
    }

    /**
     * <加减类表达式>::=<乘除类表达式>{<加号><乘除类表达式>|<减号><乘除类表达式>}
     *
     * @param token
     * @return
     */
    private static boolean jjlbds(String token) throws IOException {
        if (cclbds(token)) {
            while (true) {
                token = getToken();
                if ("+".equals(token) || "-".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if (cclbds(token)) {
                    }
                } else {
                    stack.push(token);
                    stack.failed();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * <乘除类表达式>::=<一元表达式>{<星号><一元表达式>|<除号><一元表达式>|<取余运算符><一元表达式>}
     *
     * @param token
     * @return
     */
    private static boolean cclbds(String token) throws IOException {
        if (yybds(token)) {
            while (true) {
                token = getToken();
                if ("*".equals(token) || "/".equals(token) || "%".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if (yybds(token)) {
                    }
                } else {
                    stack.push(token);
                    stack.failed();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * <一元表达式>::=<后缀表达式>
     * |<与号><一元表达式>
     * |<星号><一元表达式>
     * |<加号><一元表达式>
     * |<减号><一元表达式>
     * |<sizeof 表达式>
     *
     * @param token
     * @return
     */
    private static boolean yybds(String token) throws IOException {
        if (hzbds(token)) {
            return true;
        }
        return false;
    }

    /**
     * <后缀表达式>::=<初等表达式>{
     * <左中括号><expression><右中括号>
     * |<左小括号><右小括号>
     * |<左小括号><实参表达式><右小括号>
     * |<点号>IDENTIFIER
     * |<箭头>IDENTIFIER}
     *
     * @param token
     * @return
     */
    private static boolean hzbds(String token) throws IOException {
        if (cdbds(token)) {
            token = getToken();
            if ("(".equals(token) || "[".equals(token)) {
                while (true) {
                    System.out.print(token);
                    token = getToken();
                    if (")".equals(token) || "]".equals(token)) {
                        System.out.print(token);
                        break;
                    } else if (cdbds(token)) {
                        token = getToken();
                        if (",".equals(token)) {
                            System.out.print(token);
                            token = getToken();
                        } else if (")".equals(token)) {
                            System.out.print(token);
                            break;
                        } else {
                            stack.push(token);
                            stack.failed();
                        }
                    }
                }
            } else {
                stack.push(token);
                stack.failed();
            }
            return true;
        }
        return false;
    }


    /**
     * <初等表达式>::=<标识符>|<整数常量>|<字符串常量>|<字符常量>|(<表达式>)
     *
     * @param token
     */
    private static boolean cdbds(String token) {
        if (smf(token)) {
            System.out.print(token);
            return true;
        } else if (token.matches("\\d+")) {
            System.out.print(token);
            return true;
        } else if (token.startsWith("\"") && token.endsWith("\"")) {
            System.out.print(token);
            return true;
        } else if (token.startsWith("\'") && token.endsWith("\'")) {
            System.out.print(token);
            return true;
        }
        return false;
    }

    /**
     * <结构区分符>::=<struct 关键字><标识符><左大括号><结构声明表><右大括号>| <struct关键字><标识符>
     *
     *
     *
     * <调用约定>::=<__cdecl 关键字>|<__stdcall关键字>
     * <结构成员对齐>::=<__align关键字><左小括号><整数常量><右小括号>
     *
     * <参数声明>::=<类型区分符><声明符>
     *
     * <初值符>::=<赋值表达式>
     *
     * <sizeof表达式>::=<sizeof 关键字>{<类型区分符>}
     */
}