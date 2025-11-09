package com.example.compile.aisample;

import java.util.*;

// LL语法分析器
public class LLTranslator {
    private Stack<Object> stack;
    private Map<String, List<String[]>> grammar;
    private List<String> tokens;
    private int tokenIndex;

    public LLTranslator() {
        initializeGrammar();
    }

    // 初始化文法 - 简化版本专注于for语句
    private void initializeGrammar() {
        grammar = new HashMap<>();

        // Program -> Statement*
        String[] Program = {"Statement", "Program'"};
        List<String[]> ProgramList = new ArrayList<>();
        ProgramList.add(Program);
        grammar.put("Program", ProgramList);

        grammar.put("Program'", Arrays.asList(new String[]{"Statement", "Program'"}, new String[]{"ε"}));

        // Statement -> ForStmt | AssignStmt | ExprStmt
        grammar.put("Statement", Arrays.asList(new String[]{"ForStmt"}, new String[]{"AssignStmt"}, new String[]{"ExprStmt"}));

        // ForStmt -> for ( ForInit ; ForCond ; ForUpdate ) Block
        String[] ForStmt = {"for", "(", "ForInit", ";", "ForCond", ";", "ForUpdate", ")", "Block"};
        List<String[]> ForStmtList = new ArrayList<>();
        ForStmtList.add(ForStmt);
        grammar.put("ForStmt", ForStmtList);

        // ForInit -> AssignStmt | Expr | ε
        grammar.put("ForInit", Arrays.asList(new String[]{"AssignStmt"}, new String[]{"Expr"}, new String[]{"ε"}));

        // ForCond -> Expr | ε
        grammar.put("ForCond", Arrays.asList(new String[]{"Expr"}, new String[]{"ε"}));

        // ForUpdate -> AssignStmt | Expr | ε
        grammar.put("ForUpdate", Arrays.asList(new String[]{"AssignStmt"}, new String[]{"Expr"}, new String[]{"ε"}));

        // AssignStmt -> id = Expr ;
        String[] AssignStmt = {"id", "=", "Expr", ";"};
        List<String[]> AssignStmtList = new ArrayList<>();
        AssignStmtList.add(AssignStmt);
        grammar.put("AssignStmt", AssignStmtList);

        // ExprStmt -> Expr ;
        String[] ExprStmt = {"Expr", ";"};
        List<String[]> ExprStmtList = new ArrayList<>();
        ExprStmtList.add(ExprStmt);
        grammar.put("ExprStmt", ExprStmtList);

        // Block -> { Statements }
        String[] Block = {"{", "Statements", "}"};
        List<String[]> BlockList = new ArrayList<>();
        BlockList.add(Block);
        grammar.put("Block", BlockList);

        // Statements -> Statement Statements'
        String[] Statements = {"Statement", "Statements'"};
        List<String[]> StatementsList = new ArrayList<>();
        StatementsList.add(Statements);
        grammar.put("Statements", StatementsList);

        grammar.put("Statements'", Arrays.asList(new String[]{"Statement", "Statements'"}, new String[]{"ε"}));

        // 表达式文法
        String[] Expr = {"LogicExpr"};
        List<String[]> ExprList = new ArrayList<>();
        ExprList.add(Expr);
        grammar.put("Expr", ExprList);

        String[] LogicExpr = {"CompareExpr", "LogicExpr'"};
        List<String[]> LogicExprList = new ArrayList<>();
        LogicExprList.add(LogicExpr);
        grammar.put("LogicExpr", LogicExprList);

        grammar.put("LogicExpr'", Arrays.asList(new String[]{"&&", "CompareExpr", "LogicExpr'"}, new String[]{"||", "CompareExpr", "LogicExpr'"}, new String[]{"ε"}));

        String[] CompareExpr = {"ArithExpr", "CompareExpr'"};
        List<String[]> CompareExprList = new ArrayList<>();
        CompareExprList.add(CompareExpr);
        grammar.put("CompareExpr", CompareExprList);

        grammar.put("CompareExpr'", Arrays.asList(new String[]{"<", "ArithExpr"}, new String[]{">", "ArithExpr"}, new String[]{"<=", "ArithExpr"}, new String[]{">=", "ArithExpr"}, new String[]{"==", "ArithExpr"}, new String[]{"!=", "ArithExpr"}, new String[]{"ε"}));

        String[] ArithExpr = {"Term", "ArithExpr'"};
        List<String[]> ArithExprList = new ArrayList<>();
        ArithExprList.add(ArithExpr);
        grammar.put("ArithExpr", ArithExprList);

        grammar.put("ArithExpr'", Arrays.asList(new String[]{"+", "Term", "ArithExpr'"}, new String[]{"-", "Term", "ArithExpr'"}, new String[]{"ε"}));

        String[] Term = {"Factor", "Term'"};
        List<String[]> TermList = new ArrayList<>();
        TermList.add(Term);
        grammar.put("Term", TermList);

        grammar.put("Term'", Arrays.asList(new String[]{"*", "Factor", "Term'"}, new String[]{"/", "Factor", "Term'"}, new String[]{"ε"}));

        grammar.put("Factor", Arrays.asList(new String[]{"(", "Expr", ")"}, new String[]{"id"}, new String[]{"num"}));
    }

    // 词法分析器
    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isWhitespace(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                continue;
            }

            // 处理运算符和分隔符
            if (isOperator(c) || c == '(' || c == ')' || c == '{' || c == '}' || c == ';' || c == '=' || c == ',') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (isRelationalOperatorStart(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                // 处理双字符运算符
                if (i + 1 < input.length() && isDoubleCharOperator(c, input.charAt(i + 1))) {
                    tokens.add(String.valueOf(c) + input.charAt(i + 1));
                    i++;
                } else {
                    tokens.add(String.valueOf(c));
                }
            } else if (c == '&' || c == '|') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                if (i + 1 < input.length() && input.charAt(i + 1) == c) {
                    tokens.add(String.valueOf(c) + c);
                    i++;
                } else {
                    tokens.add(String.valueOf(c));
                }
            } else {
                currentToken.append(c);
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isRelationalOperatorStart(char c) {
        return c == '<' || c == '>' || c == '=' || c == '!';
    }

    private boolean isDoubleCharOperator(char c1, char c2) {
        String op = String.valueOf(c1) + c2;
        return op.equals("<=") || op.equals(">=") || op.equals("==") || op.equals("!=");
    }

    // 获取当前token的类型
    private String getTokenType(String token) {
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            return "operator";
        } else if (token.equals("<") || token.equals(">") || token.equals("<=") || token.equals(">=") || token.equals("==") || token.equals("!=")) {
            return "relop";
        } else if (token.equals("&&") || token.equals("||")) {
            return "logop";
        } else if (token.equals("=")) {
            return "assign";
        } else if (token.equals("(")) {
            return "(";
        } else if (token.equals(")")) {
            return ")";
        } else if (token.equals("{")) {
            return "{";
        } else if (token.equals("}")) {
            return "}";
        } else if (token.equals(";")) {
            return ";";
        } else if (token.equals("for")) {
            return "for";
        } else if (isNumeric(token)) {
            return "num";
        } else {
            return "id";
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 主要的解析方法
    public ASTNode parse(String input) {
        this.stack = new Stack<>();
        this.tokens = tokenize(input);
        this.tokenIndex = 0;
        System.out.println("Tokens: " + tokens);
        // 初始化栈
        stack.push("$");
        stack.push("Program");
        List<String> p = Arrays.asList(new String[]{"Program", "Program'", "Statement", "Statements", "ForStmt", "for", "ForInit", "ForCond", "ForUpdate", "Block", "AssignStmt", "Expr", "ExprStmt"});
        ASTNode root = new ASTNode("Program");
        Stack<ASTNode> nodeStack = new Stack<>();
        nodeStack.push(root);
        while (!stack.isEmpty()) {
            Object top = stack.pop();
            String currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : "$";
            System.out.println("栈: " + stack + ", 栈顶: " + top + ", 当前token: " + currentToken);
            if (top.equals("$")) {
                if (tokenIndex >= tokens.size()) {
                    break;
                } else {
                    error("输入未完全消耗");
                    return null;
                }
            }
            // 处理AST节点
            if (top instanceof ASTNode) {
                ASTNode node = (ASTNode) top;
                if (!nodeStack.isEmpty()) {
                    ASTNode pop = nodeStack.pop();
                    nodeStack.peek().addChild(node);
                    nodeStack.push(pop);
                }
                continue;
            }
            String symbol = (String) top;
            if (isTerminal(symbol)) {
                if (matchTerminal(symbol, currentToken)) {
                    System.out.println("  匹配: " + symbol + " == " + currentToken);
                    if (!symbol.equals("ε")) {
                        if (";".equals(symbol)) {
                            ASTNode peek = nodeStack.peek();
                            if ("Expr".equals(peek.type)) {
                                nodeStack.pop();
                                peek = nodeStack.peek();
                                if ("AssignStmt".equals(peek.type) || "ForCond".equals(peek.type)) {
                                    nodeStack.pop();
                                }
                                peek = nodeStack.peek();
                                if ("Statement".equals(peek.type)) {
                                    nodeStack.pop();
                                }
                                tokenIndex++;
                            } else if ("ForInit".equals(peek.type)) {
                                nodeStack.pop();
                                ASTNode leaf = new ASTNode(symbol, currentToken);
                                if (!nodeStack.isEmpty()) {
                                    nodeStack.peek().addChild(leaf);
                                }
                                tokenIndex++;
                            }
                        } else if (")".equals(symbol)) {
                            ASTNode peek = nodeStack.peek();
                            if ("ForUpdate".equals(peek.type)) {
                                nodeStack.pop();
                                ASTNode leaf = new ASTNode(symbol, currentToken);
                                if (!nodeStack.isEmpty()) {
                                    nodeStack.peek().addChild(leaf);
                                }
                                tokenIndex++;
                            }
                        } else if ("}".equals(symbol)) {
                            ASTNode peek = nodeStack.peek();
                            if ("Statements".equals(peek.type)) {
                                nodeStack.pop();
                                ASTNode leaf = new ASTNode(symbol, currentToken);
                                if (!nodeStack.isEmpty()) {
                                    nodeStack.peek().addChild(leaf);
                                }
                                tokenIndex++;
                            }
                        } else {
                            // 创建叶子节点
                            ASTNode leaf = new ASTNode(symbol, currentToken);
                            if (!nodeStack.isEmpty()) {
                                nodeStack.peek().addChild(leaf);
                            }
                            tokenIndex++;
                        }
                    }
                } else {
                    error("期望 " + symbol + " 但找到 " + currentToken);
                    return null;
                }
            } else {
                // 非终结符 - 选择产生式
                String[] production = selectProduction(symbol, currentToken);
                if (production != null) {
                    System.out.println("  应用产生式: " + symbol + " -> " + Arrays.toString(production));
                    // 创建非终结符节点
                    ASTNode node = new ASTNode(symbol);
                    if (p.contains(node.type)) {
                        nodeStack.push(node);
                    }
                    // 逆序压栈
                    for (int i = production.length - 1; i >= 0; i--) {
                        String symbolToPush = production[i];
                        if (!symbolToPush.equals("ε")) {
                            stack.push(symbolToPush);
                        }
                    }
                    // 压入节点本身，用于构建AST
                    if (p.contains(node.type)) {
                        stack.push(node);
                    }
                } else {
                    error("没有合适的产生式: " + symbol + " with lookahead " + currentToken);
                    return null;
                }
            }
        }
        if (tokenIndex == tokens.size()) {
            System.out.println("✓ 分析成功!");
            return root;
        } else {
            error("分析失败 - 剩余tokens: " + tokens.subList(tokenIndex, tokens.size()));
            return null;
        }
    }

    private boolean isTerminal(String symbol) {
        return !grammar.containsKey(symbol) || symbol.equals("id") || symbol.equals("num") || symbol.equals("+") || symbol.equals("-") || symbol.equals("*") || symbol.equals("/") || symbol.equals("<") || symbol.equals(">") || symbol.equals("<=") || symbol.equals(">=") || symbol.equals("==") || symbol.equals("!=") || symbol.equals("&&") || symbol.equals("||") || symbol.equals("=") || symbol.equals("(") || symbol.equals(")") || symbol.equals("{") || symbol.equals("}") || symbol.equals(";") || symbol.equals("for") || symbol.equals("ε");
    }

    private boolean matchTerminal(String expected, String actual) {
        if (expected.equals("id") && getTokenType(actual).equals("id")) {
            return true;
        } else if (expected.equals("num") && getTokenType(actual).equals("num")) {
            return true;
        } else if (expected.equals("+") && actual.equals("+")) {
            return true;
        } else if (expected.equals("-") && actual.equals("-")) {
            return true;
        } else if (expected.equals("*") && actual.equals("*")) {
            return true;
        } else if (expected.equals("/") && actual.equals("/")) {
            return true;
        } else if (expected.equals("<") && actual.equals("<")) {
            return true;
        } else if (expected.equals(">") && actual.equals(">")) {
            return true;
        } else if (expected.equals("<=") && actual.equals("<=")) {
            return true;
        } else if (expected.equals(">=") && actual.equals(">=")) {
            return true;
        } else if (expected.equals("==") && actual.equals("==")) {
            return true;
        } else if (expected.equals("!=") && actual.equals("!=")) {
            return true;
        } else if (expected.equals("&&") && actual.equals("&&")) {
            return true;
        } else if (expected.equals("||") && actual.equals("||")) {
            return true;
        } else if (expected.equals("=") && actual.equals("=")) {
            return true;
        } else if (expected.equals("(") && actual.equals("(")) {
            return true;
        } else if (expected.equals(")") && actual.equals(")")) {
            return true;
        } else if (expected.equals("{") && actual.equals("{")) {
            return true;
        } else if (expected.equals("}") && actual.equals("}")) {
            return true;
        } else if (expected.equals(";") && actual.equals(";")) {
            return true;
        } else if (expected.equals("for") && actual.equals("for")) {
            return true;
        } else if (expected.equals("ε")) {
            return true;
        }
        return false;
    }

    private String[] selectProduction(String nonTerminal, String lookahead) {
        String tokenType = getTokenType(lookahead);
        List<String[]> productions = grammar.get(nonTerminal);

        if (productions == null) {
            return null;
        }

        switch (nonTerminal) {
            case "Program":
                if (tokenType.equals("for") || tokenType.equals("id") || lookahead.equals("{")) {
                    return productions.get(0);
                }
                break;

            case "Program'":
                if (tokenType.equals("for") || tokenType.equals("id") || lookahead.equals("{")) {
                    return productions.get(0);
                } else {
                    return productions.get(1);
                }

            case "Statement":
                if (lookahead.equals("for")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() && tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(1);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(2);
                }
                break;

            case "ForStmt":
                return productions.get(0);

            case "ForInit":
                if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() && tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(1);
                } else if (lookahead.equals(";")) {
                    return productions.get(2);
                }
                break;

            case "ForCond":
                if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(0);
                } else if (lookahead.equals(";")) {
                    return productions.get(1);
                }
                break;

            case "ForUpdate":
                if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() && tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(1);
                } else if (lookahead.equals(")")) {
                    return productions.get(2);
                }
                break;

            case "AssignStmt":
            case "ExprStmt":
            case "Block":
            case "Expr":
            case "LogicExpr":
            case "CompareExpr":
            case "ArithExpr":
            case "Term":
                return productions.get(0);

            case "Statements":
                if (tokenType.equals("for") || tokenType.equals("id") || lookahead.equals("{")) {
                    return productions.get(0);
                } else {
                    return null;
                }

            case "Statements'":
                if (tokenType.equals("for") || tokenType.equals("id") || lookahead.equals("{")) {
                    return productions.get(0);
                } else {
                    return productions.get(1);
                }

            case "LogicExpr'":
                if (lookahead.equals("&&")) {
                    return productions.get(0);
                } else if (lookahead.equals("||")) {
                    return productions.get(1);
                } else {
                    return productions.get(2);
                }

            case "CompareExpr'":
                if (lookahead.equals("<")) {
                    return productions.get(0);
                } else if (lookahead.equals(">")) {
                    return productions.get(1);
                } else if (lookahead.equals("<=")) {
                    return productions.get(2);
                } else if (lookahead.equals(">=")) {
                    return productions.get(3);
                } else if (lookahead.equals("==")) {
                    return productions.get(4);
                } else if (lookahead.equals("!=")) {
                    return productions.get(5);
                } else {
                    return productions.get(6);
                }

            case "ArithExpr'":
                if (lookahead.equals("+")) {
                    return productions.get(0);
                } else if (lookahead.equals("-")) {
                    return productions.get(1);
                } else {
                    return productions.get(2);
                }

            case "Term'":
                if (lookahead.equals("*")) {
                    return productions.get(0);
                } else if (lookahead.equals("/")) {
                    return productions.get(1);
                } else {
                    return productions.get(2);
                }

            case "Factor":
                if (lookahead.equals("(")) {
                    return productions.get(0);
                } else if (tokenType.equals("id")) {
                    return productions.get(1);
                } else if (tokenType.equals("num")) {
                    return productions.get(2);
                }
                break;
        }

        return null;
    }

    private void error(String message) {
        System.out.println("❌ 错误: " + message);
        System.out.println("当前位置: tokenIndex=" + tokenIndex + ", tokens=" + (tokenIndex < tokens.size() ? tokens.get(tokenIndex) : "EOF"));
    }

    // 生成代码
    public String generateCode(ASTNode node) {
        if (node == null) return "";
        return generateCodeRecursive(node, 0);
    }

    private String generateCodeRecursive(ASTNode node, int indent) {
        if (node == null) return "";

        StringBuilder code = new StringBuilder();
        String indentStr = "  ";

        switch (node.type) {
            case "Program":
                for (ASTNode child : node.children) {
                    code.append(generateCodeRecursive(child, indent));
                }
                break;

            case "ForStmt":
                code.append(indentStr).append("for (");
                // 提取for循环的三个部分
                String initCode = "";
                String condCode = "";
                String updateCode = "";
                ASTNode bodyNode = null;

                for (ASTNode child : node.children) {
                    switch (child.type) {
                        case "ForInit":
                            initCode = generateCodeRecursive(child, 0).trim();
                            break;
                        case "ForCond":
                            condCode = generateCodeRecursive(child, 0).trim();
                            break;
                        case "ForUpdate":
                            updateCode = generateCodeRecursive(child, 0).trim();
                            break;
                        case "Block":
                            bodyNode = child;
                            break;
                    }
                }

                code.append(initCode).append("; ").append(condCode).append("; ").append(updateCode).append(") ");

                if (bodyNode != null) {
                    code.append(generateCodeRecursive(bodyNode, indent));
                } else {
                    code.append("{}");
                }
                break;

            case "ForInit":
            case "ForCond":
            case "ForUpdate":
                if (!node.children.isEmpty()) {
                    code.append(generateCodeRecursive(node.children.get(0), 0));
                }
                break;

            case "AssignStmt":
                code.append(indentStr);
                if (node.children.size() >= 3) {
                    String id = node.children.get(0).value;
                    String expr = generateCodeRecursive(node.children.get(2), 0);
                    code.append(id).append(" = ").append(expr).append(";\n");
                }
                break;

            case "ExprStmt":
                code.append(indentStr).append(generateCodeRecursive(node.children.get(0), 0)).append(";\n");
                break;

            case "Block":
                code.append(indentStr).append("{\n");
                for (ASTNode child : node.children) {
                    if (!child.type.equals("{") && !child.type.equals("}")) {
                        code.append(generateCodeRecursive(child, indent + 1));
                    }
                }
                code.append(indentStr).append("}\n");
                break;

            case "LogicExpr":
            case "CompareExpr":
            case "ArithExpr":
            case "Term":
                if (node.children.size() >= 1) {
                    code.append(generateCodeRecursive(node.children.get(0), 0));
                    if (node.children.size() > 1) {
                        code.append(generateCodeRecursive(node.children.get(1), 0));
                    }
                }
                break;

            case "LogicExpr'":
            case "CompareExpr'":
            case "ArithExpr'":
            case "Term'":
                if (!node.children.isEmpty() && !node.children.get(0).value.equals("ε")) {
                    code.append(" ").append(node.children.get(0).value).append(" ").append(generateCodeRecursive(node.children.get(1), 0));
                    if (node.children.size() > 2) {
                        code.append(generateCodeRecursive(node.children.get(2), 0));
                    }
                }
                break;

            case "Factor":
                if (node.children.size() == 1) {
                    code.append(node.children.get(0).value);
                } else if (node.children.size() == 3) {
                    code.append("(").append(generateCodeRecursive(node.children.get(1), 0)).append(")");
                }
                break;

            default:
                if (node.value != null) {
                    code.append(node.value);
                }
                break;
        }

        return code.toString();
    }

    public static void main(String[] args) {
        LLTranslator translator = new LLTranslator();

        // 测试用例
        String[] testInputs = {"for ( i1 = 0 + 1 + 2 * 3 - 4 ;; i2 < 10 ; i3 = i4 + 1; ) { x5 = x6 + i7 ; }",
//                "for ( ; i > 0 ; i = i - 1 ) { }",
//                "for ( j = 1 ; j <= 5 ; j = j + 1 ) { sum = sum + j ; }",
//                "i = 0 ; for ( ; i < 10 ; i = i + 1 ) { print(i) ; }"
        };

        for (String input : testInputs) {
            System.out.println("\n" + "==================");
            System.out.println("测试输入: " + input);
            System.out.println("==================");

            ASTNode ast = translator.parse(input);
            if (ast != null) {
                System.out.println("\n语法树:");
                System.out.println(ast);

                String code = translator.generateCode(ast);
                System.out.println("生成的代码:");
                System.out.println(code);
            }
        }
    }
}