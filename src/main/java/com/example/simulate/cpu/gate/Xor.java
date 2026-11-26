package com.example.simulate.cpu.gate;

public class Xor {
    public boolean[] Q;

    public Xor(int size) {
        this.Q = new boolean[size];
    }

    public Xor calc(boolean[] A, boolean[] B) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = A[i] ^ B[i];
        }
        return this;
    }
}
