package com.example.compile.table3.tree;

import com.example.compile.table3.tree.impl.SyntaxNa;
import com.example.compile.table3.tree.impl.SyntaxTyp;

public abstract class SyntaxFactory {
    public static Syntax generate(String token) {
        switch (token) {
            case "Typ":
                return new SyntaxTyp();
            case "Na":
                return new SyntaxNa();
            default:
                return null;
        }
    }
}
