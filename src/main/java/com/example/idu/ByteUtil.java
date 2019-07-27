package com.example.idu;

/**
 * @Description: bytes 0 --> n ,地位在前
 * @Auther: xiankun.jiang
 * @Date: 2019/7/27 13:46
 */
public class ByteUtil {
    /**
     * 字节数据倒序
     * @param data
     * @return
     */
    public static byte[] inverted(byte[] data) {
        int length = data.length;
        byte bytes[] = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = data[length - 1 - i];
        }
        return bytes;
    }

    public static byte[] short2Bytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] int2Bytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] float2Bytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return int2Bytes(intBits);
    }

    public static byte[] double2Bytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return long2Bytes(intBits);
    }

    public static byte[] long2Bytes(long data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }


    public static short bytes2Short(byte[] bytes) {
        return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
    }

    public static int bytes2Int(byte[] bytes) {
        return (0xff & bytes[0])
                | (0xff00 & (bytes[1] << 8))
                | (0xff0000 & (bytes[2] << 16))
                | (0xff000000 & (bytes[3] << 24));
    }

    public static float bytes2Float(byte[] bytes) {
        return Float.intBitsToFloat(bytes2Int(bytes));
    }

    public static double bytes2Double(byte[] bytes) {
        return Double.longBitsToDouble(bytes2Long(bytes));
    }

    public static long bytes2Long(byte[] bytes) {
        return (0xffL & (long) bytes[0])
                | (0xff00L & ((long) bytes[1] << 8))
                | (0xff0000L & ((long) bytes[2] << 16))
                | (0xff000000L & ((long) bytes[3] << 24))
                | (0xff00000000L & ((long) bytes[4] << 32))
                | (0xff0000000000L & ((long) bytes[5] << 40))
                | (0xff000000000000L & ((long) bytes[6] << 48))
                | (0xff00000000000000L & ((long) bytes[7] << 56));
    }
}
