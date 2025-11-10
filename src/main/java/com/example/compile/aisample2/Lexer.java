package com.example.compile.aisample2;

import java.util.*;

// 简单的词法分析器
class Lexer {
    private final String input;
    private int position;
    private int line;
    private int column;

    // 关键字集合
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "for", "int", "if", "while", "do", "return", "class", "public", "private"
    ));

    // 操作符集合
    private static final Set<Character> OPERATORS = new HashSet<>(Arrays.asList('+', '-', '*', '/', '=', '<', '>', '!'));
    private static final Set<Character> SEPARATORS = new HashSet<>(Arrays.asList('(', ')', '{', '}', ';', ',', ':'));

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.line = 1;
        this.column = 1;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token token;

        while ((token = nextToken()) != null) {
            tokens.add(token);
            if (token.type == TokenType.EOF) break;
        }

        return tokens;
    }

    private Token nextToken() {
        skipWhitespace();

        if (position >= input.length()) {
            return new Token(TokenType.EOF, "", line, column);
        }

        char current = input.charAt(position);

        // 标识符或关键字
        if (Character.isLetter(current) || current == '_') {
            return readIdentifierOrKeyword();
        }

        // 数字字面量
        if (Character.isDigit(current)) {
            return readNumber();
        }

        // 操作符
        if (OPERATORS.contains(current)) {
            return readOperator();
        }

        // 分隔符
        if (SEPARATORS.contains(current)) {
            return readSeparator();
        }

        // 字符串字面量
        if (current == '"') {
            return readString();
        }

        position++;
        column++;
        return null;
    }

    private Token readIdentifierOrKeyword() {
        int start = position;
        while (position < input.length() &&
                (Character.isLetterOrDigit(input.charAt(position)) ||
                        input.charAt(position) == '_')) {
            position++;
            column++;
        }

        String value = input.substring(start, position);
        TokenType type = KEYWORDS.contains(value) ? TokenType.KEYWORD : TokenType.IDENTIFIER;
        return new Token(type, value, line, column - value.length());
    }

    private Token readNumber() {
        int start = position;
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            position++;
            column++;
        }
        String value = input.substring(start, position);
        return new Token(TokenType.LITERAL, value, line, column - value.length());
    }

    private Token readOperator() {
        char current = input.charAt(position);
        position++;
        column++;

        // 处理双字符操作符
        if (position < input.length()) {
            char next = input.charAt(position);
            String doubleOp = String.valueOf(current) + next;
            if (doubleOp.equals("++") || doubleOp.equals("--") ||
                    doubleOp.equals("<=") || doubleOp.equals(">=") ||
                    doubleOp.equals("==") || doubleOp.equals("!=")) {
                position++;
                column++;
                return new Token(TokenType.OPERATOR, doubleOp, line, column - 2);
            }
        }

        return new Token(TokenType.OPERATOR, String.valueOf(current), line, column - 1);
    }

    private Token readSeparator() {
        char current = input.charAt(position);
        position++;
        column++;
        return new Token(TokenType.SEPARATOR, String.valueOf(current), line, column - 1);
    }

    private Token readString() {
        int start = position;
        position++; // 跳过开始的 "
        column++;

        while (position < input.length() && input.charAt(position) != '"') {
            if (input.charAt(position) == '\n') {
                line++;
                column = 1;
            }
            position++;
            column++;
        }

        if (position < input.length()) {
            position++; // 跳过结束的 "
            column++;
        }

        String value = input.substring(start + 1, position - 1);
        return new Token(TokenType.LITERAL, value, line, column - value.length() - 2);
    }

    private void skipWhitespace() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            if (input.charAt(position) == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
            position++;
        }
    }
}
