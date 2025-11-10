package com.example.compile.aisample2;



import com.example.compile.aisample2.impl.ForStatement;
import com.example.compile.aisample2.impl.TraditionalForControl;

import java.util.List;

public class TableDrivenParserDemo {
    public static void main(String[] args) {
        // 测试代码
        String sourceCode = "for (int i = 0; i < 10; i++) { System.out.println(i); }";

        System.out.println("源代码: " + sourceCode);
        System.out.println("============================");

        // 词法分析
        Lexer lexer = new Lexer(sourceCode);
        List<Token> tokens = lexer.tokenize();

        System.out.println("词法分析结果:");
        for (Token token : tokens) {
            System.out.println("  " + token);
        }

        System.out.println("\n" + "============================");
        System.out.println("语法分析过程:");

        // 语法分析
        TableDrivenParser parser = new TableDrivenParser(tokens);
        ASTNode ast = parser.parse();

        System.out.println("\n" + "============================");
        System.out.println("生成的AST:");
        System.out.println(ast);

        // 可视化AST
        visualizeAST(ast);
    }

    private static void visualizeAST(ASTNode node) {
        System.out.println("\nAST树形结构:");
        if (node instanceof ForStatement) {
            ForStatement forStmt = (ForStatement) node;
            System.out.println("ForStatement");
            if (forStmt.forControl instanceof TraditionalForControl) {
                TraditionalForControl control = (TraditionalForControl) forStmt.forControl;
                System.out.println("├── TraditionalForControl");
                System.out.println("│   ├── Init: " + control.init);
                System.out.println("│   ├── Condition: " + control.condition);
                System.out.println("│   └── Update: " + control.update);
            }
            System.out.println("└── Body: " + forStmt.body);
        }
    }
}
