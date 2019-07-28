package com.example.idu;

import java.io.*;
import java.lang.reflect.Array;

import static com.example.idu.ByteUtil.*;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/27 09:55
 */
public class IDUFileUtils {
    //    public static void main(String[] argv) {
//        IDUFileUtils iduFileUtils = new IDUFileUtils();
////        Object images = iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\t10k-images.idx3-ubyte");
//        byte[] labels = (byte[]) iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\t10k-labels.idx1-ubyte");
////        int[] dimensions = {10000, 28, 28};
////        iduFileUtils.write(images, dimensions, (byte) 0x08, "C:\\Users\\Administrator\\Desktop\\octave\\resource\\test.idx3-ubyte");
////        Object test = iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\test.idx3-ubyte");
////        iduFileUtils.write(labels, "C:\\Users\\Administrator\\Desktop\\octave\\resource\\t10k-labels-test.idx1-ubyte");
////        Integer data[] = new Integer[10000];
//        int[] dimensions = {10000};
//        iduFileUtils.write(labels, dimensions, (byte) 0x08, "C:\\Users\\Administrator\\Desktop\\octave\\resource\\test1.idx1-ubyte");
//        byte[] test = (byte[]) iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\test1.idx1-ubyte");
//        int count = 0;
//        for (int i = 0; i < 10000; i++) {
//            if (labels[i] != test[i]) {
//                System.out.println("不匹配：" + i);
//            }
//        }
//        System.out.println("不匹配个数：" + count + " 匹配个数：" + (10000 - count));
//    }
    public static void main(String[] argv) {
        IDUFileUtils iduFileUtils = new IDUFileUtils();
        //byte
        byte[][][] dbyte = {
                {
                        {0x01, 0x02},
                        {0x03, 0x04}
                },
                {
                        {0x05, 0x06},
                        {0x07, 0x08}
                },
                {
                        {0x09, 0x0a},
                        {0x0b, 0x0c}
                }
        };
        //short
        short[][][] dshort = {
                {
                        {0x01, 0x02},
                        {0x03, 0x04}
                },
                {
                        {0x05, 0x06},
                        {0x07, 0x08}
                },
                {
                        {0x09, 0x0a},
                        {0x0b, 0x0c}
                }
        };
        //int
        int[][][] dint = {
                {
                        {0x01, 0x02},
                        {0x03, 0x04}
                },
                {
                        {0x05, 0x06},
                        {0x07, 0x08}
                },
                {
                        {0x09, 0x0a},
                        {0x0b, 0x0c}
                }
        };
        //float
        float[][][] dfloat = {
                {
                        {0x01, 0x02},
                        {0x03, 0x04}
                },
                {
                        {0x05, 0x06},
                        {0x07, 0x08}
                },
                {
                        {0x09, 0x0a},
                        {0x0b, 0x0c}
                }
        };
        //double
        double[][][] ddouble = {
                {
                        {0x01, 0x02},
                        {0x03, 0x04}
                },
                {
                        {0x05, 0x06},
                        {0x07, 0x08}
                },
                {
                        {0x09, 0x0a},
                        {0x0b, 0x0c}
                }
        };
        int[] db = {3,2,2};
        iduFileUtils.write(dbyte,db,(byte)0x08,"F:/db.idx3-ubyte");
        Object d1 = iduFileUtils.read("F:/db.idx3-ubyte");
        //short
        iduFileUtils.write(dshort,db,(byte)0x0B,"F:/ds.idx3-ubyte");
        Object d2 = iduFileUtils.read("F:/ds.idx3-ubyte");
        //int
        iduFileUtils.write(dint,db,(byte)0x0C,"F:/di.idx3-ubyte");
        Object d3 = iduFileUtils.read("F:/di.idx3-ubyte");
        //float
        iduFileUtils.write(dfloat,db,(byte)0x0D,"F:/df.idx3-ubyte");
        Object d4 = iduFileUtils.read("F:/df.idx3-ubyte");
        //double
        iduFileUtils.write(ddouble,db,(byte)0x0E,"F:/dd.idx3-ubyte");
        Object d5 = iduFileUtils.read("F:/dd.idx3-ubyte");
        System.out.println("");
    }


    /**
     * 文件中数据保存字节顺序 如：整型 10,000 (0x00 00 27 10) --> 文件中保存为 00 00 27 10
     *
     * @param filePath
     * @return
     */
    public Object read(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("file is not exists");
            return null;
        }
        FileInputStream fis = null;
        byte bytes[] = new byte[4];
        try {
            fis = new FileInputStream(file);
            //读取 magic number
            fis.read(bytes, 0, 2);
            System.out.printf("magic number = %02x %02x\n", bytes[0], bytes[1]);
            //读取数据类型
            fis.read(bytes, 0, 1);
            byte data_type = bytes[0];
            System.out.printf("data type = %02x\n", bytes[0]);
            //读取数据维度
            fis.read(bytes, 0, 1);
            System.out.printf("dimension = %02x ", bytes[0]);
            System.out.println(bytes2Int(bytes));
            int dimension = bytes[0];
            int[] dimensions = new int[dimension];
            int array_length = 1;
            for (int i = 0; i < dimension; i++) {
                fis.read(bytes);
                System.out.printf("dimension %d = %02x %02x %02x %02x ", i + 1, bytes[0], bytes[1], bytes[2], bytes[3]);
                dimensions[i] = bytes2Int(inverted(bytes));
                System.out.println(dimensions[i]);
                array_length *= dimensions[i];
            }

            Object return_array = null;
            ArrayUtil arrayUtil = new ArrayUtil(dimensions);
            arrayUtil.rest();
            Object array = null;
            switch (data_type) {
                case 0x08://0x08: unsigned byte
                    array = new byte[array_length];
                    fis.read((byte[]) array, 0, array_length);
//                    array = bytes2Unsigned((byte[]) array);
                    return_array = Array.newInstance(Byte.TYPE, dimensions);
                    arrayUtil.reshape((byte[]) array, return_array);
                    break;
                case 0x09://0x09: signed byte
                    array = new byte[array_length];
                    fis.read((byte[]) array, 0, array_length);
                    return_array = Array.newInstance(Byte.TYPE, dimensions);
                    arrayUtil.reshape((byte[]) array, return_array);
                    break;
                case 0x0B://0x0B: short (2 bytes)
                    bytes = new byte[2];
                    array = new short[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((short[]) array)[i] = bytes2Short(inverted(bytes));
                    }
                    return_array = Array.newInstance(Short.TYPE, dimensions);
                    arrayUtil.reshape((short[]) array, return_array);
                    break;
                case 0x0C://0x0C: int (4 bytes)
                    array = new int[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((int[]) array)[i] = bytes2Int(inverted(bytes));
                    }
                    return_array = Array.newInstance(Integer.TYPE, dimensions);
                    arrayUtil.reshape((int[]) array, return_array);
                    break;
                case 0x0D://0x0D: float (4 bytes)
                    array = new float[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((float[]) array)[i] = bytes2Float(inverted(bytes));
                    }
                    return_array = Array.newInstance(Float.TYPE, dimensions);
                    arrayUtil.reshape((float[]) array, return_array);
                    break;
                case 0x0E://0x0E: double (8 bytes)
                    bytes = new byte[8];
                    array = new double[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((double[]) array)[i] = bytes2Double(inverted(bytes));
                    }
                    return_array = Array.newInstance(Double.TYPE, dimensions);
                    arrayUtil.reshape((double[]) array, return_array);
                    break;
                default:
                    break;
            }
            fis.close();
            arrayUtil.rest();
            return return_array;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param object   多维数组（数据）
     * @param filePath 保存路径
     *                 //     * @param dimensions 一维数组保存多维数组每一维的长度
     *                 //     * @param data_type  数据保存类型 byte,short,int,double,float..
     * @return
     */
    public Boolean write(Object object, int[] dimensions, byte data_type, String filePath) {
        //object是否是数组
        if (!object.getClass().isArray()) {
            System.out.println("this object is not a array");
            return false;
        }
        //获取object数组的维度
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        if (dimensions.length != dimension) {
            System.out.println("dimension do not match");
            return false;
        }
        byte dimens[] = inverted(int2Bytes(dimension));
        //magic number 2 bytes 数据类型 1 byte 数据维度 1 byte 具体每一个维度 4bytes/个
        int headLength = 2 + 1 + 1 + 4 * dimension;
        byte[] headByte = new byte[headLength];
        headByte[2] = data_type;
        headByte[3] = dimens[3];
        int data_length = 1;
        for (int i = 0; i < dimension; i++) {
            data_length *= dimensions[i];
            byte[] d = inverted(int2Bytes(dimensions[i]));
            for (int t = 0; t < 4; t++) {
                headByte[4 + i * 4 + t] = d[t];
            }
        }
        //文件是否存在，不存在则创建文件
        File file = new File(filePath);
        FileOutputStream fos = null;
        ArrayUtil arrayUtil = new ArrayUtil(dimensions);
        arrayUtil.rest();
        try {
            if (!file.exists()) file.createNewFile();
            fos = new FileOutputStream(file);
            //写文件头
            fos.write(headByte);
            //写数据
            byte[] temp = null;
            switch (data_type) {
                case 0x08://0x08: unsigned byte
                    byte[] data_ubyte = new byte[data_length];
                    arrayUtil.reshape(object, data_ubyte);
                    fos.write(data_ubyte);
                    break;
                case 0x09://0x09: signed byte
                    byte[] data_byte = new byte[data_length];
                    arrayUtil.reshape(object, data_byte);
                    fos.write(data_byte);
                    break;
                case 0x0B://0x0B: short (2 bytes)
                    short[] data_short = new short[data_length];
                    arrayUtil.reshape(object, data_short);
                    for (int i = 0; i < data_length; i++) {
                        temp = inverted(short2Bytes(data_short[i]));
                        fos.write(temp);
                    }
                    break;
                case 0x0C://0x0C: int (4 bytes)
                    int[] data_int = new int[data_length];
                    arrayUtil.reshape(object, data_int);
                    for (int i = 0; i < data_length; i++) {
                        temp = inverted(int2Bytes(data_int[i]));
                        fos.write(temp);
                    }
                    break;
                case 0x0D://0x0D: float (4 bytes)
                    float[] data_float = new float[data_length];
                    arrayUtil.reshape(object, data_float);
                    for (int i = 0; i < data_length; i++) {
                        temp = inverted(float2Bytes(data_float[i]));
                        fos.write(temp);
                    }
                    break;
                case 0x0E://0x0E: double (8 bytes)
                    double[] data_double = new double[data_length];
                    arrayUtil.reshape(object, data_double);
                    for (int i = 0; i < data_length; i++) {
                        temp = inverted(double2Bytes(data_double[i]));
                        fos.write(temp);
                    }
                    break;
                default:
                    break;
            }
            arrayUtil.rest();
            fos.flush();
            fos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
}
