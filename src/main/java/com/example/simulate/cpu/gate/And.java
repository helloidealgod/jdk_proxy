package com.example.simulate.cpu.gate;

public class And {
    public boolean[] Q;

    public And(int size) {
        this.Q = new boolean[size];
    }

    public And calc(boolean[] A, boolean[] B) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = A[i] & B[i];
        }
        return this;
    }
}
