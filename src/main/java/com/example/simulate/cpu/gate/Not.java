package com.example.simulate.cpu.gate;

public class Not {
    public boolean[] Q;

    public Not(int size) {
        this.Q = new boolean[size];
    }

    public Not calc(boolean[] A) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = !A[i];
        }
        return this;
    }
}
