package com.example.compile.iteration.analysis;

public enum StatementType {
    VAR_DECLARE(1),
    VAR_ASSIGN(2),
    FUNCTION_DECLARE(3),
    FUNCTION_DEFINE(4),
    FUNCTION_CALL(5),
    IF(6),
    FOR(7),
    WHILE(8),
    ;
    private int value;

    StatementType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
