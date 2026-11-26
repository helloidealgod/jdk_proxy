package com.example.simulate.cpu.component;

import com.example.simulate.cpu.chip.DFlipFlop;

public class Counter {
    public boolean[] Q;
    public DFlipFlop d;
    private boolean[] input;
    private int offer = 4;
    private boolean start1 = false;
    private boolean start2 = false;

    public Counter(int size) {
        d = new DFlipFlop(size);
        input = new boolean[size];
        this.Q = d.Q;
    }

    public Counter init(boolean[] A) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = A[i];
            this.d.Q0[i] = this.Q[i];
            this.d.QN[i] = !A[i];
            this.d.Q0N[i] = this.d.QN[i];
        }
        return this;
    }

    public Counter calc(boolean clk0, boolean clk) {
        input[0] = d.QN[0] ^ start1; // start1 = true 不计数
        input[1] = d.Q[0] ^ d.Q[1];
        // 011 110 10X
        input[2] = ((d.Q[0] ^ d.Q[2]) & d.Q[1]) | (d.Q[2] & d.QN[1]);
        // 0111 1110 10XX 110X
        input[3] = ((d.Q[3] ^ d.Q[0]) & (d.Q[2] & d.Q[1]))
                | (d.Q[3] & d.QN[2])
                | (d.Q[3] & d.Q[2] & d.QN[1]);

        if (8 == Q.length) {
            start2 = !(d.Q[3] & d.Q[2] & d.Q[1] & d.Q[0]);
            input[0 + offer] = d.QN[0 + offer] ^ start2 | d.Q[0 + offer] & start2; // start2 = true 不计数
            input[1 + offer] = (d.Q[0 + offer] ^ d.Q[1 + offer]) & !start2
                    | d.Q[1 + offer] & start2;
            // 011 110 10X
            input[2 + offer] = (((d.Q[0 + offer] ^ d.Q[2 + offer]) & d.Q[1 + offer]) | (d.Q[2 + offer] & d.QN[1 + offer])) & !start2
                    | d.Q[2 + offer] & start2;
            // 0111 1110 10XX 110X
            input[3 + offer] = (((d.Q[3 + offer] ^ d.Q[0 + offer]) & (d.Q[2 + offer] & d.Q[1 + offer]))
                    | (d.Q[3 + offer] & d.QN[2 + offer])
                    | (d.Q[3 + offer] & d.Q[2 + offer] & d.QN[1 + offer])) & !start2
                    | d.Q[3 + offer] & start2;
        }

        d.calc(input, clk0, clk);
        return this;
    }
}
