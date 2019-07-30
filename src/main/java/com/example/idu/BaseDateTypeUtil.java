package com.example.idu;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/30 10:52
 */
public class BaseDateTypeUtil {
    public static void main(String[] argv) {
        byte[] data = {0x01, 0x02, 0x03, 0x04};
        short[] shorts = {1, 2, 3, 4, 5, 6};
        int[] ints = {1, 2, 3, 4, 5, 6};
        float[] floats = {1, 2, 3, 4, 5, 6};
        double[] doubles = {1, 2, 3, 4, 5, 6};
        long[] longs = {1, 2, 3, 4, 5, 6};
        Byte[] bytes = byte2Byte(data);
        Short[] Shorts = short2Short(shorts);
        Integer[] integers = int2Integer(ints);
        Float[] Floats = float2Float(floats);
        Double[] Doubles = double2Double(doubles);
        Long[] Longs = long2Long(longs);
        System.out.println("");
    }

    public static Byte[] byte2Byte(byte[] data) {
        Byte[] bytes = new Byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bytes[i] = data[i];
        }
        return bytes;
    }

    public static Short[] short2Short(short[] data) {
        Short[] shorts = new Short[data.length];
        for (int i = 0; i < data.length; i++) {
            shorts[i] = data[i];
        }
        return shorts;
    }

    public static Integer[] int2Integer(int[] data) {
        Integer[] integers = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            integers[i] = data[i];
        }
        return integers;
    }

    public static Float[] float2Float(float[] data) {
        Float[] floats = new Float[data.length];
        for (int i = 0; i < data.length; i++) {
            floats[i] = data[i];
        }
        return floats;
    }

    public static Double[] double2Double(double[] data) {
        Double[] doubles = new Double[data.length];
        for (int i = 0; i < data.length; i++) {
            doubles[i] = data[i];
        }
        return doubles;
    }

    public static Long[] long2Long(long[] data) {
        Long[] longs = new Long[data.length];
        for (int i = 0; i < data.length; i++) {
            longs[i] = data[i];
        }
        return longs;
    }
}
