package com.example.compile.table3;

public class Utils {
    public static void printSegmentE(SegmentExprOp e) {
        if (e.op != null) {
            String var1 = e.e1.value == null ? e.e1.name : e.e1.value;
            String var2 = e.e2.value == null ? e.e2.name : e.e2.value;
            System.out.println(e.op + " " + var1 + " " + var2);
            e.value = "[" + var1 + " " + e.op + " " + var2 + "]";
        }
    }
}
