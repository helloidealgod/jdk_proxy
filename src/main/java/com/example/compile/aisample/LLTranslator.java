package com.example.compile.aisample;

import java.util.*;

// LL语法分析器
public class LLTranslator {
    private String input;
    private int position;
    private Stack<Object> stack; // 可以存储字符串或ASTNode
    private Map<String, List<String[]>> grammar;

    public LLTranslator() {
        initializeGrammar();
    }

    // 初始化简单算术表达式文法
    private void initializeGrammar() {
        grammar = new HashMap<>();
        // E -> T E'
        String[] te = {"T", "E'"};
        List<String[]> e = new ArrayList<>();
        e.add(te);
        grammar.put("E", e);
        // E' -> + T E' | ε
        grammar.put("E'", Arrays.asList(
                new String[]{"+", "T", "E'"},
                new String[]{"ε"}
        ));
        // T -> F T'
        String[] ft = {"F", "T'"};
        List<String[]> t = new ArrayList<>();
        t.add(ft);
        grammar.put("T", t);
        // T' -> * F T' | ε
        grammar.put("T'", Arrays.asList(
                new String[]{"*", "F", "T'"},
                new String[]{"ε"}
        ));
        // F -> ( E ) | id | num
        grammar.put("F", Arrays.asList(
                new String[]{"(", "E", ")"},
                new String[]{"id"},
                new String[]{"num"}
        ));
    }

    // 词法分析器 - 将输入字符串转换为token序列
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
            if (isOperator(c) || c == '(' || c == ')') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(String.valueOf(c));
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

    // 获取当前token的类型
    private String getTokenType(String token) {
        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            return "operator";
        } else if (token.equals("(")) {
            return "(";
        } else if (token.equals(")")) {
            return ")";
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
        this.input = input;
        this.position = 0;
        this.stack = new Stack<>();

        List<String> tokens = tokenize(input);
        System.out.println("Tokens: " + tokens);
        // 初始化栈，放入开始符号和结束标记
        stack.push("$"); // 结束标记
        stack.push("E"); // 开始符号
        // 创建根节点
        ASTNode root = new ASTNode("Program");
        int tokenIndex = 0;
        while (!stack.isEmpty() && tokenIndex < tokens.size()) {
            Object top = stack.pop();
            if (top instanceof ASTNode) {
                // 如果是AST节点，直接添加到父节点
                root.addChild((ASTNode) top);
                continue;
            }
            String symbol = (String) top;
            String currentToken = tokens.get(tokenIndex);
            String tokenType = getTokenType(currentToken);
            System.out.println("栈顶: " + symbol + ", 当前token: " + currentToken + "(" + tokenType + ")");
            if (symbol.equals("$")) {
                if (tokenIndex == tokens.size() - 1) {
                    break; // 分析成功
                } else {
                    System.out.println("错误: 输入未完全消耗");
                    break;
                }
            }
            // 如果是终结符
            if (isTerminal(symbol)) {
                if (match(symbol, tokenType, currentToken)) {
                    System.out.println("匹配: " + symbol + " 和 " + currentToken);
                    tokenIndex++;
                    // 创建叶子节点
                    if (!symbol.equals("ε")) {
                        ASTNode leaf = new ASTNode(symbol, currentToken);
                        if (!stack.isEmpty() && stack.peek() instanceof ASTNode) {
                            ((ASTNode) stack.peek()).addChild(leaf);
                        } else {
                            root.addChild(leaf);
                        }
                    }
                } else {
                    System.out.println("错误: 期望 " + symbol + " 但找到 " + currentToken);
                    break;
                }
            }
            // 如果是非终结符
            else {
                List<String[]> productions = grammar.get(symbol);
                if (productions != null) {
                    String[] production = selectProduction(symbol, tokenType, currentToken, productions);
                    if (production != null) {
                        System.out.println("应用产生式: " + symbol + " -> " + Arrays.toString(production));
                        // 创建非终结符节点
                        ASTNode node = new ASTNode(symbol);
                        // 将产生式右部逆序压栈
                        for (int i = production.length - 1; i >= 0; i--) {
                            if (!production[i].equals("ε")) {
                                stack.push(production[i]);
                            }
                        }
                        // 将节点压栈，以便后续添加子节点
                        stack.push(node);
                    } else {
                        System.out.println("错误: 没有找到合适的产生式 for " + symbol + " with lookahead " + currentToken);
                        break;
                    }
                } else {
                    System.out.println("错误: 未知的非终结符 " + symbol);
                    break;
                }
            }
        }

        if (stack.isEmpty() && tokenIndex == tokens.size()) {
            System.out.println("分析成功!");
        } else {
            System.out.println("分析失败!");
        }

        return root;
    }

    private boolean isTerminal(String symbol) {
        return !grammar.containsKey(symbol) || symbol.equals("id") || symbol.equals("num") ||
                symbol.equals("+") || symbol.equals("*") || symbol.equals("(") || symbol.equals(")") || symbol.equals("ε");
    }

    private boolean match(String expected, String actualType, String actualValue) {
        if (expected.equals("id") && actualType.equals("id")) {
            return true;
        } else if (expected.equals("num") && actualType.equals("num")) {
            return true;
        } else if (expected.equals("+") && actualValue.equals("+")) {
            return true;
        } else if (expected.equals("*") && actualValue.equals("*")) {
            return true;
        } else if (expected.equals("(") && actualValue.equals("(")) {
            return true;
        } else if (expected.equals(")") && actualValue.equals(")")) {
            return true;
        } else if (expected.equals("ε")) {
            return true; // ε产生式，不消耗输入
        }
        return false;
    }

    private String[] selectProduction(String nonTerminal, String tokenType, String tokenValue, List<String[]> productions) {
        // 简单的预测分析表
        switch (nonTerminal) {
            case "E":
            case "E'":
                if (tokenValue.equals("+")) {
                    return productions.get(0); // E' -> + T E'
                } else if (tokenValue.equals(")") || tokenValue.equals("$")) {
                    return productions.get(1); // E' -> ε
                } else {
                    return productions.get(0); // 对于E，只有一种选择
                }
            case "T":
            case "T'":
                if (tokenValue.equals("*")) {
                    return productions.get(0); // T' -> * F T'
                } else if (tokenValue.equals("+") || tokenValue.equals(")") || tokenValue.equals("$")) {
                    return productions.get(1); // T' -> ε
                } else {
                    return productions.get(0); // 对于T，只有一种选择
                }
            case "F":
                if (tokenValue.equals("(")) {
                    return productions.get(0); // F -> ( E )
                } else if (tokenType.equals("id")) {
                    return productions.get(1); // F -> id
                } else if (tokenType.equals("num")) {
                    return productions.get(2); // F -> num
                }
                break;
        }
        return null;
    }

    // 生成中间代码或目标代码
    public String generateCode(ASTNode node) {
        return generateCodeRecursive(node);
    }

    private String generateCodeRecursive(ASTNode node) {
        switch (node.type) {
            case "Program":
                StringBuilder programCode = new StringBuilder();
                for (ASTNode child : node.children) {
                    programCode.append(generateCodeRecursive(child));
                }
                return programCode.toString();
            case "E":
            case "T":
            case "F":
                if (node.children.size() == 1) {
                    return generateCodeRecursive(node.children.get(0));
                } else if (node.children.size() == 3) {
                    // 处理二元运算或括号表达式
                    if (node.children.get(0).type.equals("(")) {
                        return "(" + generateCodeRecursive(node.children.get(1)) + ")";
                    } else {
                        String left = generateCodeRecursive(node.children.get(0));
                        String op = generateCodeRecursive(node.children.get(1));
                        String right = generateCodeRecursive(node.children.get(2));
                        return left + " " + op + " " + right;
                    }
                }
                break;
            case "E'":
            case "T'":
                if (node.children.size() > 0 && !node.children.get(0).value.equals("ε")) {
                    String op = generateCodeRecursive(node.children.get(0));
                    String operand = generateCodeRecursive(node.children.get(1));
                    String continuation = generateCodeRecursive(node.children.get(2));
                    return op + " " + operand + " " + continuation;
                }
                return "";
            case "+":
            case "*":
                return node.value;

            case "id":
            case "num":
                return node.value;
        }
        return "";
    }

    public static void main(String[] args) {
        LLTranslator translator = new LLTranslator();
        // 测试表达式
        String[] testExpressions = {
                "3 + 4 * 5",
                "( 10 + 20 ) * 30",
                "a * b + c"
        };
        for (String expr : testExpressions) {
            System.out.println("\n========== 分析表达式: " + expr + " ==========");
            ASTNode ast = translator.parseAndTranslate(expr);
            System.out.println("\n语法树:");
            System.out.println(ast);
            String code = translator.generateCode(ast);
            System.out.println("生成的代码: " + code);
        }
    }
}