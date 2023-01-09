package com.example.compile.iteration;


import com.example.compile.iteration.analysis.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static com.example.compile.iteration.analysis.StatementType.FZBDS;
import static com.example.compile.iteration.analysis.StatementType.ZKH;
import static com.example.compile.iteration.analysis.Utils.compareOperate;
import static com.example.compile.iteration.analysis.Utils.resultListToString;

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

    public static TokenStack stack = new TokenStack();

    public static List<Result> resultList = new ArrayList<>();

    public static HashMap<String, Result> symbol = new HashMap<>();

    public static Stack<Result> smfStack = new Stack<>();
    public static Stack<String> operateStack = new Stack<>();

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
        System.out.println("\n================================================================================\n");
        resultListToString(resultList, 0);
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
        Result lxqff = lxqff(token);
        if (!lxqff.success) {
            System.out.println("error");
        }
        System.out.print("type:" + token);
        while (true) {
            token = getToken();
            if (";".equals(token)) {
                break;
            }
            // 标识符
            Result smf = smf(token);
            if (!smf.success) {
                System.out.println("error");
            }
            if (DataType.PTR.getValue() == smf.type) {
                lxqff.statementType = StatementType.VAR_DECLARE.getValue();
                lxqff.statementTypeStr = StatementType.VAR_DECLARE.name();
                setPointerType(smf, lxqff);
            } else {
                smf.type = lxqff.type;
                smf.typeStr = lxqff.typeStr;
                smf.width = lxqff.width;
            }
            smf.statementType = StatementType.VAR_DECLARE.getValue();
            smf.statementTypeStr = StatementType.VAR_DECLARE.name();
            if (DataType.STRUCT.getValue() == lxqff.type) {
                if (0 < lxqff.subList.size()) {
                    smf.subList = lxqff.subList;
                } else {
                    String name = lxqff.getName();
                    Result result = symbol.get(name);
                    if (null != result) {
                        smf.subList = result.subList;
                    }
                }
            }
            System.out.print(" name:" + token);
            token = getToken();
            Result zjsmfhz = zjsmfhz(token);
            if (zjsmfhz.success) {
                token = getToken();
                if (DataType.ARRAY.getValue() == zjsmfhz.type) {
                    Result arr = smf.clone();
                    setArrAyType2(zjsmfhz, arr);
                    smf = zjsmfhz;
                } else {
                    smf.statementType = StatementType.FUNCTION_DECLARE.getValue();
                    smf.statementTypeStr = StatementType.FUNCTION_DECLARE.name();
                    smf.formalParameterList = zjsmfhz.formalParameterList;
                }
                if ("{".equals(token)) {
                    smf.statementType = StatementType.FUNCTION_DEFINE.getValue();
                    smf.statementTypeStr = StatementType.FUNCTION_DEFINE.name();
                    System.out.print(token);
                    token = getToken();
                    Result fhyj = fhyj(token);
                    if (fhyj.success) {
                        smf.subList = fhyj.subList;
                        resultList.add(smf);
                        break;
                    }
                }
            } else {
                if ("=".equals(token)) {
                    token = getToken();
                    System.out.print(" value:" + token);
                    smf.initValue = token;
                    token = getToken();
                    if (",".equals(token)) {
                        resultList.add(smf);
                    }
                } else if (",".equals(token)) {
                    resultList.add(smf);
                }
            }
            if (";".equals(token)) {
                resultList.add(smf);
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
    private static Result lxqff(String token) throws IOException {
        Result result = null;
        if (token.matches("void|int|float|char|long|")) {
            result = new Result();
            if ("void".equals(token)) {
                result.type = DataType.VOID.getValue();
                result.typeStr = DataType.VOID.name();
                result.width = 4;
            } else if ("int".equals(token)) {
                result.type = DataType.INT.getValue();
                result.typeStr = DataType.INT.name();
                result.width = 4;
            } else if ("float".equals(token)) {
                result.type = DataType.FLOAT.getValue();
                result.typeStr = DataType.FLOAT.name();
                result.width = 4;
            } else if ("char".equals(token)) {
                result.type = DataType.CHAR.getValue();
                result.typeStr = DataType.CHAR.name();
                result.width = 1;
            } else if ("long".equals(token)) {
                result.type = DataType.LONG.getValue();
                result.typeStr = DataType.LONG.name();
                result.width = 4;
            }
            result.success = true;
            return result;
        } else if ((result = jgqff(token)).success) {
            result.success = true;
            return result;
        } else {
            result = new Result();
            result.success = false;
            return result;
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
    private static Result jgqff(String token) throws IOException {
        Result result = new Result();
        if (token.matches("struct")) {
            result.type = DataType.STRUCT.getValue();
            result.typeStr = DataType.STRUCT.name();
            token = getToken();
            Result smf = smf(token);
            if (smf.success) {
                System.out.print(token + " ");
                result.name = smf.name;
                token = getToken();
                if ("{".equals(token)) {
                    System.out.print(token + " ");

                    while (true) {
                        token = getToken();
                        if ("}".equals(token)) {
                            System.out.print(token + " ");
                            result.success = true;
                            symbol.put(result.name, result);
                            return result;
                        }
                        Result lxqff = lxqff(token);
                        if (!lxqff.success) {
                            System.out.println("error");
                        }
                        System.out.print(token + " ");
                        while (true) {
                            token = getToken();
                            Result smf1 = smf(token);
                            if (!smf1.success) {
                                System.out.println("error");
                            }

                            smf1.type = lxqff.type;
                            smf1.typeStr = lxqff.typeStr;
                            smf1.width = lxqff.width;
                            result.subList.add(smf1);

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
                    result.success = true;
                    return result;
                }
            }
        }
        result.success = false;
        return result;
    }

    /**
     * <声明符>::={<指针>}[<调用约定>][<结构成员对齐>]<直接声明符>
     *
     * @param token
     */
    private static Result smf(String token) throws IOException {
        Result result = new Result();
        Result zz = zz(token);
        if (zz.success) {
            token = getToken();
        }
        boolean success = !token.matches("void|int|float|char|long|") && token.matches("[_A-Za-z][_A-Za-z0-9]*");
        result.success = success;
        result.statementType = StatementType.SMF.getValue();
        result.statementTypeStr = StatementType.SMF.name();
        if (success) {
            result.name = token;
            if (zz.success) {
                result.type = DataType.PTR.getValue();
                result.typeStr = DataType.PTR.name();
                result.rel = zz.rel;
            }
        }
        return result;
    }

    /**
     * <指针>::=<星号>
     *
     * @param token
     */
    private static Result zz(String token) throws IOException {
        Result result = new Result();
        if ("*".equals(token)) {
            System.out.print(token);
            result.type = DataType.PTR.getValue();
            result.typeStr = DataType.PTR.name();
            token = getToken();
            if (!"*".equals(token)) {
                stack.push(token);
                stack.failed();
                result.success = true;
                return result;
            } else {
                Result zz = zz(token);
                result.rel = zz;
            }
        } else {
            result.success = false;
            return result;
        }
        result.success = true;
        return result;
    }

    private static void setPointerType(Result result, Result type) {
        if (null == result.rel) {
            Result rel = type.clone();
            result.rel = rel;
        } else {
            setPointerType(result.rel, type);
        }
    }

    private static void setArrAyType(Result result, Result result2e) {
        if (null == result.typeStr) {
            result.type = result2e.type;
            result.typeStr = result2e.typeStr;
            result.statementType = result2e.statementType;
            result.statementTypeStr = result2e.statementTypeStr;
            result.arrayLength = result2e.arrayLength;
        } else {
            result.rel = new Result();
            setArrAyType(result.rel, result2e);
        }
    }

    private static void setArrAyType2(Result result, Result result2e) {
        if (null == result.rel) {
            result.rel = result2e;
        } else {
            setArrAyType2(result.rel, result2e);
        }
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
    private static Result zjsmfhz(String token) throws IOException {
        Result result = new Result();
        if ("[".equals(token)) {
            while (true) {
                Result array = new Result();
                array.type = DataType.ARRAY.getValue();
                array.typeStr = DataType.ARRAY.name();
                array.statementType = StatementType.VAR_DECLARE.getValue();
                array.statementTypeStr = StatementType.VAR_DECLARE.name();
                System.out.print(token);
                token = getToken();
                if (token.matches("\\d+")) {
                    System.out.print(token);
                    array.arrayLength = token;
                    token = getToken();
                }
                if ("]".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    setArrAyType(result, array);
                    if ("[".equals(token)) {
//                        setArrAyType(result, array);
                    } else {
                        stack.push(token);
                        stack.failed();
                        result.success = true;
                        return result;
                    }
                } else {
                    System.out.print("error");
                }
            }
        } else if ("(".equals(token)) {
            System.out.print(token + " ");
            while (true) {
                token = getToken();
                // 类型区分符
                Result lxqff = lxqff(token);
                if (lxqff.success) {
                    System.out.print(token + " ");
                    token = getToken();
                    // 标识符
                    Result smf = smf(token);
                    if (!smf.success) {
                        System.out.println("error");
                    }
                    System.out.print(token + " ");
                    smf.type = lxqff.type;
                    smf.typeStr = lxqff.typeStr;
                    smf.width = lxqff.width;
                    token = getToken();
                    //形参
                    result.formalParameterList.add(smf);
                }
                if (")".equals(token)) {
                    System.out.print(token);
                    result.success = true;
                    return result;
                }
            }
        }
        result.success = false;
        return result;
    }

    /**
     * <复合语句>::=<左大括号>{<声明><语句>}<右大括号>
     *
     * @param token
     */
    private static Result fhyj(String token) throws IOException {
        Result result = new Result();
        // <声明>
        Result lxqff = null;
        while ((lxqff = lxqff(token)).success) {
            Statement tempStatement = new Statement();
            System.out.print("type:" + token);
            tempStatement.setType(token);
            while (true) {
                token = getToken();
                // 标识符
                Result smf = smf(token);
                if (!smf.success) {
                    System.out.println("error");
                }
                if (smf.type == DataType.PTR.getValue()) {
                    smf.rel = lxqff;
                } else if (0 != lxqff.type) {
                    smf.type = lxqff.type;
                    smf.typeStr = lxqff.typeStr;
                    smf.width = lxqff.width;
                }
                smf.statementType = StatementType.VAR_DECLARE.getValue();
                smf.statementTypeStr = StatementType.VAR_DECLARE.name();

                System.out.print(" name:" + token);
                token = getToken();
                Result zjsmfhz = zjsmfhz(token);
                if (zjsmfhz.success) {
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.println("error");
                    }
                } else {
                    if ("=".equals(token)) {
                        token = getToken();
//                        System.out.print(" value:" + token);
                        Result fzbds = fzbds(token);
//                        smf.initValue = token;
                        if (fzbds.success) {
                            smf.operateList = fzbds.operateList;
                        }
                        token = getToken();
                        if (",".equals(token)) {
                            result.subList.add(smf);
                        }
                    } else if (",".equals(token)) {
                        result.subList.add(smf);
                    }
                }
                if (";".equals(token)) {
                    result.subList.add(smf);
                    token = getToken();
                    break;
                }
            }
        }
        // 语句
        while (!"}".equals(token)) {
            Result yj = yj(token);
            if (yj.success) {
                if (yj.statementType == 0) {
                    result.subList.addAll(yj.subList);
                } else {
                    result.subList.add(yj);
                }
                token = getToken();
            } else {
                System.out.println("error");
            }
        }
        if ("}".equals(token)) {
            System.out.println(token);
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <语句>::={<复合语句>|<if语句>|<for语句>|<break语句>|<continue语句>|<return语句>|<表达式语句>}
     *
     * @param token
     */
    private static Result yj(String token) throws IOException {
        Result result = new Result();
        if ("break".equals(token)) {
            System.out.println(token);
            result.name = token;
            result.statementType = StatementType.BREAK.getValue();
            result.statementTypeStr = StatementType.BREAK.name();
            token = getToken();
            result.success = true;
            return result;
        } else if ("continue".equals(token)) {
            System.out.println(token);
            result.name = token;
            result.statementType = StatementType.CONTINUE.getValue();
            result.statementTypeStr = StatementType.CONTINUE.name();
            token = getToken();
            result.success = true;
            return result;
        } else if ("return".equals(token)) {
            System.out.print(token);
            result.name = token;
            result.statementType = StatementType.RETURN.getValue();
            result.statementTypeStr = StatementType.RETURN.name();
            token = getToken();
            if (!";".equals(token)) {
                System.out.print(" ");
                Result bds = bds(token);
                if (bds.success) {

                }
            }
            System.out.println("");
            result.success = true;
            return result;
        } else if ("if".equals(token)) {
            //if 语句
            System.out.print(token);
            result.name = token;
            result.statementType = StatementType.IF.getValue();
            result.statementTypeStr = StatementType.IF.name();
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                Result bds = bds(token);
                if (bds.success) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        Result fhyj = fhyj(token);
                        if (fhyj.success) {
                            result.success = true;
                            result.subList = fhyj.subList;
                            return result;
                        }
                    }
                }
            }
        } else if ("for".equals(token)) {
            System.out.print(token);
            result.name = token;
            result.statementType = StatementType.FOR.getValue();
            result.statementTypeStr = StatementType.FOR.name();
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                Result bds = bds(token);
                if (bds.success) {

                }
                token = getToken();
                Result bds1 = bds(token);
                if (bds1.success) {

                }
                token = getToken();
                Result bds2 = bds(token);
                if (bds2.success) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        Result fhyj = fhyj(token);
                        if (fhyj.success) {
                            result.success = true;
                            result.subList = fhyj.subList;
                            return result;
                        }
                    }
                }
            }
        } else if ("while".equals(token)) {
            System.out.print(token);
            result.name = token;
            result.statementType = StatementType.WHILE.getValue();
            result.statementTypeStr = StatementType.WHILE.name();
            token = getToken();
            if ("(".equals(token)) {
                System.out.print(token);
                token = getToken();
                Result bds = bds(token);
                if (bds.success) {

                }
                token = getToken();
                if (")".equals(token)) {
                    System.out.print(token);
                    token = getToken();
                    if ("{".equals(token)) {
                        System.out.print(token);
                        token = getToken();
                        Result fhyj = fhyj(token);
                        if (fhyj.success) {
                            //statement.subStatementList.add(tempStatement);
                            result.subList = fhyj.subList;
                            result.success = true;
                            return result;
                        }
                    }
                }
            }
        } else if ((result = bds(token)).success) {
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <表达式>::=<赋值表达式>{<逗号><赋值表达式>}
     *
     * @param token
     */
    private static Result bds(String token) throws IOException {
        Result result = new Result();
        while (true) {
            Result fzbds = fzbds(token);
            if (!fzbds.success) {
                System.out.println("error");
            }
            result.operateList = fzbds.clone().operateList;
            token = getToken();
            if (!",".equals(token)) {
                break;
            }
            token = getToken();
        }
        if (";".equals(token)) {
            result.success = true;
            return result;
        } else {
            stack.push(token);
            stack.failed();
            result.success = true;
            return result;
        }
    }

    /**
     * <赋值表达式>::=<相等类表达式>|<一元表达式><赋值等号><赋值表达式>
     *
     * @param token
     */
    private static Result fzbds(String token) throws IOException {
        Result result = null;
        if ((result = xdlbds(token)).success) {
            result.success = true;
            result.statementType = StatementType.FZBDS.getValue();
            result.statementTypeStr = StatementType.FZBDS.name();
            return result;
        } else if ((result = yybds(token)).success) {
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <相等类表达式>::=<关系表达式>{<等于号><关系表达式>|<不等于号><关系表达式>}
     *
     * @param token
     * @return
     */
    private static Result xdlbds(String token) throws IOException {
        Result result = null;
        if ((result = gxbds(token)).success) {
            token = getToken();
            if ("=".equals(token) || "!=".equals(token)) {
                if ("=".equals(token)) {
                    if (StatementType.SMF.getValue() == result.statementType) {
                        result.statementType = StatementType.VAR_ASSIGN.getValue();
                        result.statementTypeStr = StatementType.VAR_ASSIGN.name();
                    }
                } else {
                    System.out.print("");
                }
                System.out.print(token);
                token = getToken();
                Result gxbds = gxbds(token);
                if (!gxbds.success) {
                    System.out.println("error");
                }
                result.rel = gxbds;
            } else {
                stack.push(token);
                stack.failed();
            }
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <关系表达式>::=<加减类表达式>{<小于号><加减类表达式>|<大于号><加减类表达式>|<小于等于号><加减类表达式>|<大于等于号><加减类表达式>}
     *
     * @param token
     * @return
     */
    private static Result gxbds(String token) throws IOException {
        Result result = null;
        if ((result = jjlbds(token)).success) {
            token = getToken();
            if ("<".equals(token) || ">".equals(token) || "<=".equals(token) || ">=".equals(token) || "==".equals(token) || "!=".equals(token)) {
                result.operation1 = result.clone();
                if ("<".equals(token)) {
                    result.operationType = Operation.LT.getValue();
                    result.operationTypeStr = Operation.LT.name();
                } else if (">".equals(token)) {
                    result.operationType = Operation.BT.getValue();
                    result.operationTypeStr = Operation.BT.name();
                } else if ("<=".equals(token)) {
                    result.operationType = Operation.LTEQ.getValue();
                    result.operationTypeStr = Operation.LTEQ.name();
                } else if (">=".equals(token)) {
                    result.operationType = Operation.BTEQ.getValue();
                    result.operationTypeStr = Operation.BTEQ.name();
                } else if ("==".equals(token)) {
                    result.operationType = Operation.EQ.getValue();
                    result.operationTypeStr = Operation.EQ.name();
                } else {
                    result.operationType = Operation.NEQ.getValue();
                    result.operationTypeStr = Operation.NEQ.name();
                }
                System.out.print(token);
                token = getToken();
                Result jjlbds = jjlbds(token);
                if (!jjlbds.success) {
                    System.out.println("error");
                }

                result.operation2 = jjlbds;
            } else {
                stack.push(token);
                stack.failed();
            }
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <加减类表达式>::=<乘除类表达式>{<加号><乘除类表达式>|<减号><乘除类表达式>}
     *
     * @param token
     * @return
     */
    private static Result jjlbds(String token) throws IOException {
        Result result = new Result();
        Result cclbds = null;
        if ((cclbds = cclbds(token)).success) {
            boolean isSet = false;
            while (true) {
                token = getToken();
                if ("+".equals(token) || "-".equals(token)) {
                    if (!isSet) {
                        smfStack.push(cclbds.clone());
                        isSet = true;
                    }
                    Result op = new Result();
                    if ("+".equals(token)) {
                        if (compareOperate(operateStack, Operation.PLUS.name())) {
                            //出栈运算，入栈
                            Result smf2 = smfStack.pop();
                            Result smf1 = smfStack.pop();
                            String operate = operateStack.pop();
                            op.operation1 = smf1;
                            op.operation2 = smf2;
                            if (Operation.PLUS.name().equals(operate)) {
                                op.operationType = Operation.PLUS.getValue();
                                op.operationTypeStr = Operation.PLUS.name();
                            } else if (Operation.SUB.name().equals(operate)) {
                                op.operationType = Operation.SUB.getValue();
                                op.operationTypeStr = Operation.SUB.name();
                            }
                            smfStack.push(op);
                            operateStack.push(Operation.PLUS.name());
                            result.operateList.add(op);
                        } else {
                            operateStack.push(Operation.PLUS.name());
                        }
                    } else {
                        if (compareOperate(operateStack, Operation.SUB.name())) {
                            //出栈运算，入栈
                            Result smf2 = smfStack.pop();
                            Result smf1 = smfStack.pop();
                            String operate = operateStack.pop();
                            op.operation1 = smf1;
                            op.operation2 = smf2;
                            if (Operation.PLUS.name().equals(operate)) {
                                op.operationType = Operation.PLUS.getValue();
                                op.operationTypeStr = Operation.PLUS.name();
                            } else if (Operation.SUB.name().equals(operate)) {
                                op.operationType = Operation.SUB.getValue();
                                op.operationTypeStr = Operation.SUB.name();
                            }
                            smfStack.push(op);
                            operateStack.push(Operation.SUB.name());
                            result.operateList.add(op);
                        } else {
                            operateStack.push(Operation.SUB.name());
                        }
                    }
                    System.out.print(token);
                    token = getToken();
                    Result cclbds1 = cclbds(token);
                    if (cclbds1.success) {
                        smfStack.push(cclbds1.clone());
                        result.operateList.addAll(cclbds1.operateList);
                    }
                } else {
                    stack.push(token);
                    stack.failed();
                    break;
                }
            }
            if (operateStack.empty()) {
                smfStack.clear();
                result = cclbds.clone();
            } else if (0 < operateStack.size()) {
                String peek = operateStack.peek();
                if (!ZKH.name().equals(peek)) {
                    //出栈运算，入栈
                    Result op = new Result();
                    Result smf2 = smfStack.pop();
                    Result smf1 = smfStack.pop();
                    String operate = operateStack.pop();
                    op.operation1 = smf1;
                    op.operation2 = smf2;
                    if (Operation.PLUS.name().equals(operate)) {
                        op.operationType = Operation.PLUS.getValue();
                        op.operationTypeStr = Operation.PLUS.name();
                    } else if (Operation.SUB.name().equals(operate)) {
                        op.operationType = Operation.SUB.getValue();
                        op.operationTypeStr = Operation.SUB.name();
                    }
                    result.operateList.add(op);
                } else {
//                    System.out.println("error");
                    result = cclbds.clone();
                }
            }
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
    }

    /**
     * <乘除类表达式>::=<一元表达式>{<星号><一元表达式>|<除号><一元表达式>|<取余运算符><一元表达式>}
     *
     * @param token
     * @return
     */
    private static Result cclbds(String token) throws IOException {
        Result yybds = yybds(token);
        if (yybds.success) {
            boolean isSet = false;
            while (true) {
                token = getToken();
                if ("*".equals(token) || "/".equals(token) || "%".equals(token)) {
                    if (!isSet) {
                        if (StatementType.FZBDS.getValue() == yybds.statementType && 0 < yybds.operateList.size()) {
                            smfStack.push(yybds.operateList.get(0).clone());
                            if (1 < yybds.operateList.size()) {
                                System.out.println("wrong");
                            }
                        } else {
                            smfStack.push(yybds.clone());
                        }
                        isSet = true;
                    }
                    Result op = new Result();
                    if ("*".equals(token)) {
                        if (compareOperate(operateStack, Operation.MUL.name())) {
                            //出栈运算，入栈
                            Result smf2 = smfStack.pop();
                            Result smf1 = smfStack.pop();
                            String operate = operateStack.pop();
                            op.operation1 = smf1;
                            op.operation2 = smf2;
                            if (Operation.MUL.name().equals(operate)) {
                                op.operationType = Operation.MUL.getValue();
                                op.operationTypeStr = Operation.MUL.name();
                            } else if (Operation.DIV.name().equals(operate)) {
                                op.operationType = Operation.DIV.getValue();
                                op.operationTypeStr = Operation.DIV.name();
                            } else if (Operation.MOD.name().equals(operate)) {
                                op.operationType = Operation.MOD.getValue();
                                op.operationTypeStr = Operation.MOD.name();
                            }
                            smfStack.push(op);
                            operateStack.push(Operation.MUL.name());
                            yybds.operateList.add(op);
                        } else {
                            operateStack.push(Operation.MUL.name());
                        }
                    } else if ("/".equals(token)) {
                        if (compareOperate(operateStack, Operation.DIV.name())) {
                            //出栈运算，入栈
                            Result smf2 = smfStack.pop();
                            Result smf1 = smfStack.pop();
                            String operate = operateStack.pop();
                            op.operation1 = smf1;
                            op.operation2 = smf2;
                            if (Operation.MUL.name().equals(operate)) {
                                op.operationType = Operation.MUL.getValue();
                                op.operationTypeStr = Operation.MUL.name();
                            } else if (Operation.DIV.name().equals(operate)) {
                                op.operationType = Operation.DIV.getValue();
                                op.operationTypeStr = Operation.DIV.name();
                            } else if (Operation.MOD.name().equals(operate)) {
                                op.operationType = Operation.MOD.getValue();
                                op.operationTypeStr = Operation.MOD.name();
                            }
                            smfStack.push(op);
                            operateStack.push(Operation.DIV.name());
                            yybds.operateList.add(op);
                        } else {
                            operateStack.push(Operation.DIV.name());
                        }
                    } else {
                        if (compareOperate(operateStack, Operation.MOD.name())) {
                            //出栈运算，入栈
                            Result smf2 = smfStack.pop();
                            Result smf1 = smfStack.pop();
                            String operate = operateStack.pop();
                            op.operation1 = smf1;
                            op.operation2 = smf2;
                            if (Operation.MUL.name().equals(operate)) {
                                op.operationType = Operation.MUL.getValue();
                                op.operationTypeStr = Operation.MUL.name();
                            } else if (Operation.DIV.name().equals(operate)) {
                                op.operationType = Operation.DIV.getValue();
                                op.operationTypeStr = Operation.DIV.name();
                            } else if (Operation.MOD.name().equals(operate)) {
                                op.operationType = Operation.MOD.getValue();
                                op.operationTypeStr = Operation.MOD.name();
                            }
                            smfStack.push(op);
                            operateStack.push(Operation.MOD.name());
                            yybds.operateList.add(op);
                        } else {
                            operateStack.push(Operation.MOD.name());
                        }
                    }
                    System.out.print(token);
                    token = getToken();
                    Result yybds1 = yybds(token);
                    if (yybds1.success) {
                        if (FZBDS.getValue() == yybds1.statementType && 0 != yybds1.operateList.size()) {
//                            smfStack.push(yybds1.operateList.get(0));
                            yybds.operateList.add(yybds1.operateList.get(0));
                            if (1 < yybds1.operateList.size()) {
                                System.out.println("wrong");
                            }
                        } else {
                            smfStack.push(yybds1.clone());
                        }
                    }
                } else {
                    stack.push(token);
                    stack.failed();
                    break;
                }
            }
            if (0 < operateStack.size()) {
                String peek = operateStack.peek();
                if (!ZKH.name().equals(peek)) {
                    if (Operation.MUL.name().equals(peek) || Operation.DIV.name().equals(peek) || Operation.MOD.name().equals(peek)) {
                        //出栈运算，入栈
                        Result op = new Result();
                        Result smf2 = smfStack.pop();
                        Result smf1 = smfStack.pop();
                        String operate = operateStack.pop();
                        op.operation1 = smf1;
                        op.operation2 = smf2;
                        if (Operation.MUL.name().equals(operate)) {
                            op.operationType = Operation.MUL.getValue();
                            op.operationTypeStr = Operation.MUL.name();
                        } else if (Operation.DIV.name().equals(operate)) {
                            op.operationType = Operation.DIV.getValue();
                            op.operationTypeStr = Operation.DIV.name();
                        } else if (Operation.MOD.name().equals(operate)) {
                            op.operationType = Operation.MOD.getValue();
                            op.operationTypeStr = Operation.MOD.name();
                        }
                        smfStack.push(op);
                        yybds.operateList.add(op.clone());
                    }
                }
            }
            if (0 < yybds.operateList.size()) {
                yybds.statementType = FZBDS.getValue();
                yybds.statementTypeStr = FZBDS.name();
                //todo
                yybds.name = null;
            }
            yybds.success = true;
            return yybds;
        }
        yybds.success = false;
        return yybds;
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
    private static Result yybds(String token) throws IOException {
        Result result = new Result();
        if ("&".equals(token) || "*".equals(token) || "+".equals(token) || "-".equals(token)) {
            if ("&".equals(token)) {
                result.statementType = StatementType.ADDR.getValue();
                result.statementTypeStr = StatementType.ADDR.name();
            } else if ("*".equals(token)) {
                result.statementType = StatementType.PTR.getValue();
                result.statementTypeStr = StatementType.PTR.name();
            } else if ("-".equals(token)) {
                result.statementType = StatementType.MINUS.getValue();
                result.statementTypeStr = StatementType.MINUS.name();
            }
            System.out.print(token);
            token = getToken();
            Result yybds = yybds(token);
            if (yybds.success) {
                result.success = true;
                result.name = yybds.name;
                return result;
            }
        }
        if ((result = hzbds(token)).success) {
            result.success = true;
            return result;
        }
        result.success = false;
        return result;
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
    private static Result hzbds(String token) throws IOException {
        Result cdbds = cdbds(token);
        if (cdbds.success) {
            token = getToken();
            if ("(".equals(token) || "[".equals(token)) {
                if ("(".equals(token)) {
                    cdbds.statementType = StatementType.FUNCTION_CALL.getValue();
                    cdbds.statementTypeStr = StatementType.FUNCTION_CALL.name();
                } else {

                }
                while (true) {
                    Result cdbd1 = null;
                    System.out.print(token);
                    token = getToken();
                    if (")".equals(token) || "]".equals(token)) {
                        System.out.print(token);
                        break;
                    } else if ((cdbd1 = cdbds(token)).success) {
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
            cdbds.success = true;
            return cdbds;
        }
        cdbds.success = false;
        return cdbds;
    }


    /**
     * <初等表达式>::=<标识符>|<整数常量>|<字符串常量>|<字符常量>|(<表达式>)
     *
     * @param token
     */
    private static Result cdbds(String token) throws IOException {
        Result smf = smf(token);
        if (smf.success) {
            System.out.print(token);
            smf.success = true;
            smf.statementType = StatementType.SMF.getValue();
            smf.statementTypeStr = StatementType.SMF.name();
            return smf;
        } else if (token.matches("\\d+")) {
            System.out.print(token);
            smf.success = true;
            smf.statementType = StatementType.CONSTANT.getValue();
            smf.statementTypeStr = StatementType.CONSTANT.name();
            smf.initValue = token;
            return smf;
        } else if (token.startsWith("\"") && token.endsWith("\"")) {
            System.out.print(token);
            smf.success = true;
            smf.statementType = StatementType.CONSTANT.getValue();
            smf.statementTypeStr = StatementType.CONSTANT.name();
            smf.initValue = token;
            return smf;
        } else if (token.startsWith("\'") && token.endsWith("\'")) {
            System.out.print(token);
            smf.success = true;
            smf.statementType = StatementType.CONSTANT.getValue();
            smf.statementTypeStr = StatementType.CONSTANT.name();
            smf.initValue = token;
            return smf;
        } else if ("(".equals(token)) {
            System.out.print(token);
            operateStack.push(ZKH.name());
            token = getToken();
            Result bds = bds(token);
            if (bds.success) {
                token = getToken();
                System.out.print(token);
                if (!")".equals(token)) {
                    System.out.println("error");
                }
                String peek = operateStack.peek();
                if (ZKH.name().equals(peek)) {
                    operateStack.pop();
                }

                smf.operateList = bds.clone().operateList;
                smf.statementType = StatementType.FZBDS.getValue();
                smf.statementTypeStr = StatementType.FZBDS.name();
            }
            smf.success = true;
            return smf;
        }
        smf.success = false;
        return smf;
    }

    /**
     * <结构区分符>::=<struct 关键字><标识符><左大括号><结构声明表><右大括号>| <struct关键字><标识符>
     * <调用约定>::=<__cdecl 关键字>|<__stdcall关键字>
     * <结构成员对齐>::=<__align关键字><左小括号><整数常量><右小括号>
     * <参数声明>::=<类型区分符><声明符>
     * <初值符>::=<赋值表达式>
     * <sizeof表达式>::=<sizeof 关键字>{<类型区分符>}
     */
}