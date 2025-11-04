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

    // 初始化包含for语句的文法
    private void initializeGrammar() {
        grammar = new HashMap<>();

        // Program -> Statements
        String[] Program = {"Statements"};
        List<String[]> ProgramList = new ArrayList<>();
        ProgramList.add(Program);
        grammar.put("Program", ProgramList);

        // Statements -> Statement Statements | ε
        grammar.put("Statements", Arrays.asList(
                new String[]{"Statement", "Statements"},
                new String[]{"ε"}
        ));

        // Statement -> ForStmt | AssignStmt | ExprStmt | Block
        grammar.put("Statement", Arrays.asList(
                new String[]{"ForStmt"},
                new String[]{"AssignStmt"},
                new String[]{"ExprStmt"},
                new String[]{"Block"}
        ));

        // ForStmt -> for ( ForInit ; Condition ; ForUpdate ) Statement
        String[] ForStmt = {"for", "(", "ForInit", ";", "Condition", ";", "ForUpdate", ")", "Statement"};
        List<String[]> ForStmtList = new ArrayList<>();
        ForStmtList.add(ForStmt);
        grammar.put("ForStmt", ForStmtList);

        // ForInit -> Type id = Expr | Expr | ε
        grammar.put("ForInit", Arrays.asList(
                new String[]{"AssignStmt"},
                new String[]{"ExprStmt"},
                new String[]{"ε"}
        ));

        // ForUpdate -> AssignStmt | ExprStmt | ε
        grammar.put("ForUpdate", Arrays.asList(
                new String[]{"AssignStmt"},
                new String[]{"ExprStmt"},
                new String[]{"ε"}
        ));

        // Condition -> Expr | ε
        grammar.put("Condition", Arrays.asList(
                new String[]{"Expr"},
                new String[]{"ε"}
        ));

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

        // 表达式文法
        String[] Expr = {"E"};
        List<String[]> ExprList = new ArrayList<>();
        ExprList.add(Expr);
        grammar.put("Expr", ExprList);

        String[] E = {"T", "E'"};
        List<String[]> EList = new ArrayList<>();
        EList.add(E);
        grammar.put("E", EList);

        grammar.put("E'", Arrays.asList(
                new String[]{"+", "T", "E'"},
                new String[]{"-", "T", "E'"},
                new String[]{"ε"}
        ));

        String[] T = {"F", "T'"};
        List<String[]> TList = new ArrayList<>();
        TList.add(T);
        grammar.put("T", TList);

        grammar.put("T'", Arrays.asList(
                new String[]{"*", "F", "T'"},
                new String[]{"/", "F", "T'"},
                new String[]{"ε"}
        ));

        grammar.put("F", Arrays.asList(
                new String[]{"(", "E", ")"},
                new String[]{"id"},
                new String[]{"num"}
        ));
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

            if (isOperator(c) || c == '(' || c == ')' || c == '{' || c == '}' ||
                    c == ';' || c == '=' || c == ',') {
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
                if (i + 1 < input.length() && isDoubleCharOperator(c, input.charAt(i + 1))) {
                    tokens.add(String.valueOf(c) + input.charAt(i + 1));
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
        } else if (token.equals("<") || token.equals(">") || token.equals("<=") ||
                token.equals(">=") || token.equals("==") || token.equals("!=")) {
            return "relop";
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

    // LL语法分析并进行翻译
    public ASTNode parseAndTranslate(String input) {
        this.stack = new Stack<>();
        this.tokens = tokenize(input);
        this.tokenIndex = 0;

        System.out.println("Tokens: " + tokens);

        // 初始化栈
        stack.push("$");
        stack.push("Program");

        // 创建根节点
        ASTNode root = new ASTNode("Program");
        Stack<ASTNode> nodeStack = new Stack<>();
        nodeStack.push(root);

        while (!stack.isEmpty() && tokenIndex <= tokens.size()) {
            Object top = stack.pop();
            String currentToken = tokenIndex < tokens.size() ? tokens.get(tokenIndex) : "$";

            System.out.println("栈顶: " + top + ", 当前token: " + currentToken);

            if (top.equals("$")) {
                if (tokenIndex >= tokens.size()) {
                    break;
                } else {
                    System.out.println("错误: 输入未完全消耗");
                    break;
                }
            }

            if (top instanceof ASTNode) {
                ASTNode node = (ASTNode) top;
                if (!nodeStack.isEmpty() && nodeStack.peek() != null) {
                    nodeStack.peek().addChild(node);
                }
                continue;
            }

            String symbol = (String) top;

            if (isTerminal(symbol)) {
                if (matchTerminal(symbol, currentToken)) {
                    System.out.println("匹配终结符: " + symbol + " -> " + currentToken);

                    // 创建叶子节点（除了ε）
                    if (!symbol.equals("ε")) {
                        ASTNode leaf = new ASTNode(symbol, currentToken);
                        if (!nodeStack.isEmpty() && nodeStack.peek() != null) {
                            nodeStack.peek().addChild(leaf);
                        }
                    }
                    if (!symbol.equals("ε")) {
                        tokenIndex++;
                    }
                } else {
                    System.out.println("语法错误: 期望 " + symbol + " 但找到 " + currentToken);
                    return null;
                }
            } else {
                String[] production = getProduction(symbol, currentToken);
                if (production != null) {
                    System.out.println("应用产生式: " + symbol + " -> " + Arrays.toString(production));

                    // 创建非终结符节点
                    ASTNode node = new ASTNode(symbol);
                    nodeStack.push(node);

                    // 逆序压栈
                    for (int i = production.length - 1; i >= 0; i--) {
                        if (!production[i].equals("ε")) {
                            stack.push(production[i]);
                        }
                    }

                    // 压入节点标记
                    stack.push(node);
                } else {
                    System.out.println("语法错误: 没有合适的产生式 for " + symbol + " with lookahead " + currentToken);
                    return null;
                }
            }
        }

        if (stack.isEmpty() && tokenIndex >= tokens.size()) {
            System.out.println("分析成功!");
        } else {
            System.out.println("分析失败! 剩余栈: " + stack + ", 剩余tokens: " +
                    (tokenIndex < tokens.size() ? tokens.subList(tokenIndex, tokens.size()) : "无"));
            return null;
        }

        return root;
    }

    private boolean isTerminal(String symbol) {
        return !grammar.containsKey(symbol) ||
                symbol.equals("id") || symbol.equals("num") ||
                symbol.equals("+") || symbol.equals("-") || symbol.equals("*") || symbol.equals("/") ||
                symbol.equals("<") || symbol.equals(">") || symbol.equals("<=") || symbol.equals(">=") ||
                symbol.equals("==") || symbol.equals("!=") || symbol.equals("=") ||
                symbol.equals("(") || symbol.equals(")") || symbol.equals("{") || symbol.equals("}") ||
                symbol.equals(";") || symbol.equals("for") || symbol.equals("ε");
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

    private String[] getProduction(String nonTerminal, String lookahead) {
        String tokenType = getTokenType(lookahead);
        List<String[]> productions = grammar.get(nonTerminal);

        if (productions == null) {
            System.out.println("警告: 没有找到非终结符 " + nonTerminal + " 的产生式");
            return null;
        }

        switch (nonTerminal) {
            case "Program":
                return productions.get(0);

            case "Statements":
                if (tokenType.equals("}") || lookahead.equals("$")) {
                    return productions.get(1); // ε
                } else {
                    return productions.get(0);
                }

            case "Statement":
                if (lookahead.equals("for")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() &&
                        tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(1);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(2);
                } else if (lookahead.equals("{")) {
                    return productions.get(3);
                }
                break;

            case "ForStmt":
                return productions.get(0);

            case "ForInit":
                if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() &&
                        tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(1);
                } else if (lookahead.equals(";")) {
                    return productions.get(2);
                }
                break;

            case "ForUpdate":
                if (tokenType.equals("id") && tokenIndex + 1 < tokens.size() &&
                        tokens.get(tokenIndex + 1).equals("=")) {
                    return productions.get(0);
                } else if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(1);
                } else if (lookahead.equals(")")) {
                    return productions.get(2);
                }
                break;

            case "Condition":
                if (tokenType.equals("id") || tokenType.equals("num") || lookahead.equals("(")) {
                    return productions.get(0);
                } else if (lookahead.equals(";")) {
                    return productions.get(1);
                }
                break;

            case "AssignStmt":
            case "ExprStmt":
            case "Block":
            case "Expr":
            case "E":
            case "T":
                return productions.get(0);

            case "E'":
                if (lookahead.equals("+")) {
                    return productions.get(0);
                } else if (lookahead.equals("-")) {
                    return productions.get(1);
                } else {
                    return productions.get(2);
                }

            case "T'":
                if (lookahead.equals("*")) {
                    return productions.get(0);
                } else if (lookahead.equals("/")) {
                    return productions.get(1);
                } else {
                    return productions.get(2);
                }

            case "F":
                if (lookahead.equals("(")) {
                    return productions.get(0);
                } else if (tokenType.equals("id")) {
                    return productions.get(1);
                } else if (tokenType.equals("num")) {
                    return productions.get(2);
                }
                break;
        }

        System.out.println("警告: 没有找到 " + nonTerminal + " 的合适产生式，lookahead=" + lookahead);
        return null;
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
            case "Statements":
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
                        case "Condition":
                            condCode = generateCodeRecursive(child, 0).trim();
                            break;
                        case "ForUpdate":
                            updateCode = generateCodeRecursive(child, 0).trim();
                            break;
                        case "Statement":
                            bodyNode = child;
                            break;
                    }
                }

                code.append(initCode).append("; ").append(condCode).append("; ").append(updateCode).append(") ");

                if (bodyNode != null) {
                    String bodyCode = generateCodeRecursive(bodyNode, indent + 1);
                    // 如果循环体是单个语句且不是块，需要加花括号
                    if (bodyNode.children.isEmpty() ||
                            (bodyNode.children.size() == 1 && !bodyNode.children.get(0).type.equals("Block"))) {
                        code.append("{\n").append(bodyCode).append(indentStr).append("}\n");
                    } else {
                        code.append(bodyCode);
                    }
                }
                break;

            case "ForInit":
            case "ForUpdate":
            case "Condition":
                if (!node.children.isEmpty()) {
                    code.append(generateCodeRecursive(node.children.get(0), 0));
                }
                break;

            case "AssignStmt":
                code.append(indentStr);
                if (node.children.size() >= 3) {
                    String id = node.children.get(0).value != null ? node.children.get(0).value : "";
                    String expr = generateCodeRecursive(node.children.get(2), 0);
                    code.append(id).append(" = ").append(expr).append(";\n");
                }
                break;

            case "ExprStmt":
                code.append(indentStr)
                        .append(generateCodeRecursive(node.children.get(0), 0))
                        .append(";\n");
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

            case "E":
            case "T":
                if (node.children.size() >= 1) {
                    code.append(generateCodeRecursive(node.children.get(0), 0));
                    if (node.children.size() > 1) {
                        code.append(generateCodeRecursive(node.children.get(1), 0));
                    }
                }
                break;

            case "E'":
            case "T'":
                if (!node.children.isEmpty() && node.children.get(0).value != null &&
                        !node.children.get(0).value.equals("ε")) {
                    code.append(" ").append(node.children.get(0).value).append(" ")
                            .append(generateCodeRecursive(node.children.get(1), 0))
                            .append(generateCodeRecursive(node.children.get(2), 0));
                }
                break;

            case "F":
                if (node.children.size() == 1) {
                    code.append(node.children.get(0).value != null ? node.children.get(0).value : "");
                } else if (node.children.size() == 3) {
                    code.append("(").append(generateCodeRecursive(node.children.get(1), 0)).append(")");
                }
                break;

            default:
                if (node.value != null && !node.value.isEmpty()) {
                    code.append(node.value);
                } else if (!node.children.isEmpty()) {
                    code.append(generateCodeRecursive(node.children.get(0), 0));
                }
                break;
        }

        return code.toString();
    }

    public static void main(String[] args) {
        LLTranslator translator = new LLTranslator();

        // 测试用例 - 简化版本确保能运行
        String[] testInputs = {
//                "i = 0 ;",
                "for ( i1 = 0 ; i2 < 10 ; i3 = i4 + 1 ) { x5 = x6 + i7 ; }",
//                "x = 1 ; y = 2 ; z = x + y ;"
        };

        for (String input : testInputs) {
            System.out.println("\n" + "==========================");
            System.out.println("分析输入: " + input);
            System.out.println("==========================");

            try {
                ASTNode ast = translator.parseAndTranslate(input);
                if (ast != null) {
                    System.out.println("\n语法树:");
                    System.out.println(ast);

                    String code = translator.generateCode(ast);
                    System.out.println("生成的代码:");
                    System.out.println(code);
                } else {
                    System.out.println("解析失败!");
                }
            } catch (Exception e) {
                System.out.println("解析过程中发生错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}