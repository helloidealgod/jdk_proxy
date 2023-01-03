package com.example.compile.iteration.analysis;

public enum DataType {
    VOID(1),
    CHAR(2),
    INT(3),
    FLOAT(4),
    LONG(5),
    STRUCT(6),
    ;
    private int value;

    DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
