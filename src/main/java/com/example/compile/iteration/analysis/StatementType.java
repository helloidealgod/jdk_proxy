package com.example.compile.iteration.analysis;

public enum StatementType {
    INIT(1),
    FUNCTION(2),
    IF(3),
    FOR(4),
    WHILE(5),
    ;
    private int value;

    StatementType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
