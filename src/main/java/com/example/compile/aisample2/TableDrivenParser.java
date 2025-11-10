package com.example.compile.aisample2;

import com.example.compile.aisample2.impl.*;

import java.util.*;

// 表驱动解析器
class TableDrivenParser {
    private final List<Token> tokens;
    private int currentTokenIndex;
    private Stack<ASTNode> nodeStack;
    private Stack<ParseState> stateStack;
    private Stack<Object> valueStack;

    // 预测分析表（简化版）
    private Map<ParseState, Map<String, Runnable>> parseTable;

    public TableDrivenParser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.nodeStack = new Stack<>();
        this.stateStack = new Stack<>();
        this.valueStack = new Stack<>();
        initializeParseTable();
    }

    private void initializeParseTable() {
        parseTable = new HashMap<>();

        // START状态
        Map<String, Runnable> startActions = new HashMap<>();
        startActions.put("for", this::startForStatement);
        parseTable.put(ParseState.START, startActions);

        // IN_FOR状态
        Map<String, Runnable> inForActions = new HashMap<>();
        inForActions.put("(", this::enterForParentheses);
        parseTable.put(ParseState.IN_FOR, inForActions);

        // IN_FOR_INIT状态
        Map<String, Runnable> inForInitActions = new HashMap<>();
        inForInitActions.put("int", this::processVariableDeclaration);
        inForInitActions.put(";", this::endForInit);
        inForInitActions.put("IDENTIFIER", this::processExpression);
        parseTable.put(ParseState.IN_FOR_INIT, inForInitActions);

        // IN_FOR_CONDITION状态
        Map<String, Runnable> inForConditionActions = new HashMap<>();
        inForConditionActions.put("IDENTIFIER", this::processCondition);
        inForConditionActions.put("LITERAL", this::processCondition);
        inForConditionActions.put(";", this::endForCondition);
        parseTable.put(ParseState.IN_FOR_CONDITION, inForConditionActions);

        // IN_FOR_UPDATE状态
        Map<String, Runnable> inForUpdateActions = new HashMap<>();
        inForUpdateActions.put("IDENTIFIER", this::processUpdate);
        inForUpdateActions.put(")", this::endForUpdate);
        parseTable.put(ParseState.IN_FOR_UPDATE, inForUpdateActions);

        // EXPECT_STATEMENT状态
        Map<String, Runnable> expectStatementActions = new HashMap<>();
        expectStatementActions.put("{", this::startBlock);
        expectStatementActions.put("IDENTIFIER", this::processSimpleStatement);
        parseTable.put(ParseState.EXPECT_STATEMENT, expectStatementActions);
    }

    public ASTNode parse() {
        stateStack.push(ParseState.START);
        System.out.println("开始解析...");

        while (!stateStack.isEmpty() && currentTokenIndex < tokens.size()) {
            ParseState currentState = stateStack.peek();
            Token currentToken = tokens.get(currentTokenIndex);

            System.out.printf("状态: %-15s 当前Token: %s\n", currentState, currentToken);

            Map<String, Runnable> actions = parseTable.get(currentState);
            if (actions != null) {
                String tokenValue = getTokenKey(currentToken);
                Runnable action = actions.get(tokenValue);

                if (action != null) {
                    action.run();
                } else {
                    // 错误恢复或默认动作
                    unexpectedToken(currentToken);
                }
            } else {
                System.out.println("错误: 未定义的状态: " + currentState);
                break;
            }
        }

        if (!nodeStack.isEmpty()) {
            return nodeStack.pop();
        }
        return null;
    }

    private String getTokenKey(Token token) {
        switch (token.type) {
            case KEYWORD: return token.value;
            case IDENTIFIER: return "IDENTIFIER";
            case LITERAL: return "LITERAL";
            case OPERATOR: return token.value;
            case SEPARATOR: return token.value;
            default: return token.value;
        }
    }

    // 表驱动动作方法
    private void startForStatement() {
        System.out.println("动作: 开始解析for语句");
        stateStack.pop(); // 弹出START
        stateStack.push(ParseState.IN_FOR);
        currentTokenIndex++; // 消耗'for'
    }

    private void enterForParentheses() {
        System.out.println("动作: 进入for括号");
        stateStack.pop(); // 弹出IN_FOR
        stateStack.push(ParseState.IN_FOR_UPDATE); // 注意：这里应该是反向压栈
        stateStack.push(ParseState.IN_FOR_CONDITION);
        stateStack.push(ParseState.IN_FOR_INIT);
        currentTokenIndex++; // 消耗'('

        // 创建for语句节点框架
        ForStatement forStmt = new ForStatement(null, null);
        nodeStack.push(forStmt);
        valueStack.push(forStmt);
    }

    private void processVariableDeclaration() {
        System.out.println("动作: 处理变量声明");
        // 简化处理: int i = 0
        currentTokenIndex++; // 消耗'int'
        Token idToken = tokens.get(currentTokenIndex++);
        Token opToken = tokens.get(currentTokenIndex++);
        Token literalToken = tokens.get(currentTokenIndex++);

        VariableDeclaration init = new VariableDeclaration(
                "int",
                idToken.value,
                new Literal(Integer.parseInt(literalToken.value), "int")
        );
        valueStack.push(init);
        System.out.println("创建初始化: " + init);
    }

    private void processExpression() {
        System.out.println("动作: 处理表达式");
        // 简化处理单个标识符
        Token token = tokens.get(currentTokenIndex++);
        Variable expr = new Variable(token.value);
        valueStack.push(expr);
        System.out.println("创建表达式: " + expr);
    }

    private void processCondition() {
        System.out.println("动作: 处理条件");
        // 简化处理: i < 10
        Token left = tokens.get(currentTokenIndex++);
        Token operator = tokens.get(currentTokenIndex++);
        Token right = tokens.get(currentTokenIndex++);

        BinaryExpression condition = new BinaryExpression(
                new Variable(left.value),
                operator.value,
                new Literal(Integer.parseInt(right.value), "int")
        );
        valueStack.push(condition);
        System.out.println("创建条件: " + condition);
    }

    private void processUpdate() {
        System.out.println("动作: 处理更新");
        Token idToken = tokens.get(currentTokenIndex++);
        Token opToken = tokens.get(currentTokenIndex++);

        PostfixExpression update = new PostfixExpression(
                new Variable(idToken.value),
                opToken.value
        );
        valueStack.push(update);
        System.out.println("创建更新: " + update);
    }

    private void endForInit() {
        System.out.println("动作: 结束初始化部分");
        stateStack.pop(); // 弹出IN_FOR_INIT
        currentTokenIndex++; // 消耗';'

        // 从值栈获取init并设置到for语句
        Object init = valueStack.pop();
        ForStatement forStmt = (ForStatement) valueStack.peek();
        if (forStmt.forControl == null) {
            forStmt.forControl = new TraditionalForControl((ASTNode)init, null, null);
        }
    }

    private void endForCondition() {
        System.out.println("动作: 结束条件部分");
        stateStack.pop(); // 弹出IN_FOR_CONDITION
        currentTokenIndex++; // 消耗';'

        // 设置条件到for控制
        Object condition = valueStack.pop();
        ForStatement forStmt = (ForStatement) valueStack.peek();
        TraditionalForControl control = (TraditionalForControl) forStmt.forControl;
        control.condition = (Expression) condition;
    }

    private void endForUpdate() {
        System.out.println("动作: 结束更新部分");
        stateStack.pop(); // 弹出IN_FOR_UPDATE
        currentTokenIndex++; // 消耗')'

        // 设置更新到for控制
        Object update = valueStack.pop();
        ForStatement forStmt = (ForStatement) valueStack.peek();
        TraditionalForControl control = (TraditionalForControl) forStmt.forControl;
        control.update = (ASTNode) update;

        // 进入语句期望状态
        stateStack.push(ParseState.EXPECT_STATEMENT);
    }

    private void startBlock() {
        System.out.println("动作: 开始代码块");
        stateStack.pop(); // 弹出EXPECT_STATEMENT
        currentTokenIndex++; // 消耗'{'

        // 处理块内语句（简化）
        List<ASTNode> statements = new ArrayList<>();
        while (currentTokenIndex < tokens.size() &&
                !tokens.get(currentTokenIndex).value.equals("}")) {
            Token token = tokens.get(currentTokenIndex);
            if (token.type == TokenType.IDENTIFIER) {
                // 简化：假设所有标识符都是方法调用
                MethodCall stmt = processMethodCall();
                statements.add(stmt);
            } else {
                currentTokenIndex++;
            }
        }

        if (currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).value.equals("}")) {
            currentTokenIndex++; // 消耗'}'
        }

        BlockStatement block = new BlockStatement(statements);
        valueStack.push(block);

        // 设置body到for语句
        ForStatement forStmt = (ForStatement) valueStack.get(0); // 获取栈底的for语句
        forStmt.body = block;

        stateStack.push(ParseState.END);
    }

    private void processSimpleStatement() {
        System.out.println("动作: 处理简单语句");
        // 简化处理
        MethodCall stmt = processMethodCall();
        valueStack.push(stmt);

        ForStatement forStmt = (ForStatement) valueStack.get(0);
        forStmt.body = stmt;

        stateStack.pop(); // 弹出EXPECT_STATEMENT
        stateStack.push(ParseState.END);
    }

    private MethodCall processMethodCall() {
        // 简化处理 System.out.println(i)
        Token systemToken = tokens.get(currentTokenIndex++);
        Token dot1Token = tokens.get(currentTokenIndex++);
        Token outToken = tokens.get(currentTokenIndex++);
        Token dot2Token = tokens.get(currentTokenIndex++);
        Token printlnToken = tokens.get(currentTokenIndex++);
        Token lparenToken = tokens.get(currentTokenIndex++);
        Token argToken = tokens.get(currentTokenIndex++);
        Token rparenToken = tokens.get(currentTokenIndex++);
        Token semiToken = tokens.get(currentTokenIndex++);

        FieldAccess systemOut = new FieldAccess(new Variable(systemToken.value), outToken.value);
        MethodCall printlnCall = new MethodCall(
                systemOut,
                printlnToken.value,
                Arrays.asList(new Variable(argToken.value))
        );

        System.out.println("创建方法调用: " + printlnCall);
        return printlnCall;
    }

    private void unexpectedToken(Token token) {
        System.out.println("错误: 意外的token: " + token + " 在状态: " + stateStack.peek());
        currentTokenIndex++; // 跳过错误token
    }
}
