package com.example.compile.table3.operate;

import com.example.compile.table3.stack.Stack;

import java.util.ArrayList;
import java.util.List;

public class OprateUtils {
    // 操作符栈
    public static Stack opStack = new Stack();
    // 值栈
    public static StackE valStack = new StackE();
    public static List<SegmentExprOp> segmentExprOpList = new ArrayList<>();

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
}
