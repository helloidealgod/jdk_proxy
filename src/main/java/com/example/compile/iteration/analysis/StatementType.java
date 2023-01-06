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
    BREAK(9),
    CONTINUE(10),
    RETURN(11),
    SMF(12),
    CONSTANT(13),
    ADDR(14),
    PTR(15),
    MINUS(16),
    MUL(17),
    DIV(18),
    MOD(19),
    ;
    private int value;

    StatementType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
