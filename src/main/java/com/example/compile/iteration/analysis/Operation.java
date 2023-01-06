package com.example.compile.iteration.analysis;

public enum Operation {
    PLUS(1),
    SUB(2),
    MUL(3),
    DIV(4),
    MOD(5),

    EQ(6),
    NEQ(7),
    BT(8),
    BTEQ(9),
    LT(10),
    LTEQ(11),
    ;
    private int value;

    Operation(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
