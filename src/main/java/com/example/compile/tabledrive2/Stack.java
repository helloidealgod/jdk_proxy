package com.example.compile.tabledrive2;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    public List<String> toKenList = new ArrayList<>();
    public List<String> symbolList = new ArrayList<>();
    public int topIndex = -1;

    public void push(String token, String symbol) {
        toKenList.add(token);
        symbolList.add(symbol);
        topIndex++;
    }

    public String pop() {
        String token = null;
        if (0 <= topIndex) {
            token = toKenList.get(topIndex);
            toKenList.remove(topIndex);
            symbolList.remove(topIndex);
            topIndex--;
        }
        return token;
    }

    public String peek() {
        if (topIndex < 0) {
            return "";
        }
        return toKenList.get(topIndex);
    }

    public String peekSymbol() {
        return symbolList.get(topIndex);
    }

    public String popSymbol() {
        String symbol = null;
        if (0 <= topIndex) {
            symbol = symbolList.get(topIndex);
            toKenList.remove(topIndex);
            symbolList.remove(topIndex);
            topIndex--;
        }
        return symbol;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (String symbol : symbolList) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
