package com.example.simulate.cpu.chip;

import com.example.simulate.cpu.Utils;

public class DFlipFlop {
    public boolean[] Q;
    public boolean[] Q0;
    public boolean[] QN;
    public boolean[] Q0N;

    public DFlipFlop(int size) {
        Q = new boolean[size];
        Q0 = new boolean[size];
        QN = new boolean[size];
        Q0N = new boolean[size];
        for (int i = 0; i < Q.length; i++) {
            this.QN[i] = true;
            this.Q0N[i] = true;
        }
    }

    public DFlipFlop calc(boolean[] A, boolean clk0, boolean clk) {
        for (int i = 0; i < Q.length; i++) {
            if (!clk0 && clk) {
                this.Q0[i] = A[i];
                this.Q0N[i] = !A[i];
            } else {
                this.Q[i] = this.Q0[i];
                this.QN[i] = this.Q0N[i];
            }
        }
        return this;
    }

    public void printf() {
        for (int i = Q.length - 1; i >= 0; i--) {
            System.out.printf("%d ", Q[i] ? 1 : 0);
        }
    }

    public void printfn() {
        System.out.printf("%d ", Utils.booleans2Byte(Q));
    }
}
