package com.example.simulate.cpu.gate;

public class Or {
    public boolean[] Q;

    public Or(int size) {
        this.Q = new boolean[size];
    }

    public Or calc(boolean[] A, boolean[] B) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = A[i] | B[i];
        }
        return this;
    }
}
