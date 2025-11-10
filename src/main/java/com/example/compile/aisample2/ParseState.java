package com.example.compile.aisample2;
// 语法状态
enum ParseState {
    START, IN_FOR, IN_FOR_INIT, IN_FOR_CONDITION, IN_FOR_UPDATE,
    IN_EXPRESSION, IN_BLOCK, EXPECT_STATEMENT, END
}
