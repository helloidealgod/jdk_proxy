package com.example.list;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int length = 10;
        for (int i = 0; i < length; i++) {
            list.add(i);
        }
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
            System.out.println(list.get(i));
        }
    }
}
