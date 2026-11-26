package com.example.simulate.cpu;

import com.example.simulate.cpu.chip.DFlipFlop;
import com.example.simulate.cpu.chip.Sum;
import com.example.simulate.cpu.component.Counter;

public class Main {
    public static void main(String[] args) {
        boolean clk0 = false;
        boolean clk = true;
        boolean isEnd = false;

        int size = 8;

        DFlipFlop d = new DFlipFlop(size);
        boolean[] input = new boolean[size];


        Counter counter8A = new Counter(size);
        Counter counter8B = new Counter(size).init(Utils.byte2Booleans((byte) 0x02));

        Sum sum = new Sum(size);
        while (!isEnd) {
            clk0 = !clk0;
            clk = !clk;

            counter8A.calc(clk0, clk);
            counter8B.calc(clk0, clk);
            sum.calc(counter8A.Q, counter8B.Q, false);
            if (clk0 && !clk) {
                counter8A.d.printf();
                System.out.printf(" + ");
                counter8B.d.printf();
                System.out.printf(" = ");
                sum.printf();
                System.out.printf("\n");
            }
        }
    }
}
