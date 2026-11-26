package com.example.simulate.cpu;

public class Utils {
    public static boolean[] byte2Booleans(byte b) {
        boolean[] result = new boolean[8];
//        for (int i = 7; i >= 0; i--) {
        for (int i = 0; i < 8; i++) {
            if ((b & 0b00000001) > 0) {
                result[i] = true;
            } else {
                result[i] = false;
            }
            b = (byte) (b >> 1);
        }
        return result;
    }

    public static byte booleans2Byte(boolean[] b) {
        byte result = 0;
        for (int i = 7; i >= 0; i--) {
            if ((b[i])) {
                result += 1;
            }
            if (i > 0) {
                result = (byte) (result << 1);
            }
        }
        return result;
    }
}
