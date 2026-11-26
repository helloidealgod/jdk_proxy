package com.example.simulate.cpu.chip;

import com.example.simulate.cpu.Utils;

public class Sum {
    public boolean[] Q;
    public boolean carry;

    public Sum(int size) {
        this.Q = new boolean[size];
    }

    public Sum calc(boolean[] A, boolean[] B, boolean carry0) {
        for (int i = 0; i < Q.length; i++) {
            this.Q[i] = A[i] ^ B[i] ^ carry0;
            this.carry = A[i] & B[i] | A[i] & carry0 | carry0 & B[i];
            carry0 = carry;
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
