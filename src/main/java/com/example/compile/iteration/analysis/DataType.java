package com.example.compile.iteration.analysis;

public enum DataType {
    INT(1),
    CHAR(2),
    SHORT(3),
    VOID(4),
    PTR(5),
    STRUCT(6),
    FLOAT(7),
    LONG(8),
    ARRAY(9),
    ;
    private int value;

    DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
