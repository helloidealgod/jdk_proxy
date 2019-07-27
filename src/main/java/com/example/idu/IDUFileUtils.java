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
    public static void main(String[] argv) {
        IDUFileUtils iduFileUtils = new IDUFileUtils();
        Object images = iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\t10k-images.idx3-ubyte");
        Object labels = iduFileUtils.read("C:\\Users\\Administrator\\Desktop\\octave\\resource\\t10k-labels.idx1-ubyte");
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

            Object return_array = Array.newInstance(Integer.TYPE, dimensions);
            ArrayUtil arrayUtil = new ArrayUtil(dimensions);
            Object array = null;
            switch (data_type) {
                case 0x08://0x08: unsigned byte
                    array = new byte[array_length];
                    fis.read((byte[]) array, 0, array_length);
                    array = bytes2Unsigned((byte[]) array);
                    arrayUtil.reshape((int[]) array, return_array);
                    break;
                case 0x09://0x09: signed byte
                    array = new byte[array_length];
                    fis.read((byte[]) array, 0, array_length);
                    arrayUtil.reshape((byte[]) array, return_array);
                    break;
                case 0x0B://0x0B: short (2 bytes)
                    bytes = new byte[2];
                    array = new short[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((short[]) array)[i] = bytes2Short(inverted(bytes));
                    }
                    arrayUtil.reshape((short[]) array, return_array);
                    break;
                case 0x0C://0x0C: int (4 bytes)
                    array = new int[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((int[]) array)[i] = bytes2Int(inverted(bytes));
                    }
                    arrayUtil.reshape((int[]) array, return_array);
                    break;
                case 0x0D://0x0D: float (4 bytes)
                    array = new float[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((float[]) array)[i] = bytes2Float(inverted(bytes));
                    }
                    arrayUtil.reshape((float[]) array, return_array);
                    break;
                case 0x0E://0x0E: double (8 bytes)
                    bytes = new byte[8];
                    array = new double[array_length];
                    for (int i = 0; i < array_length; i++) {
                        if (-1 == fis.read(bytes)) break;
                        ((double[]) array)[i] = bytes2Double(inverted(bytes));
                    }
                    arrayUtil.reshape((double[]) array, return_array);
                    break;
                default:
                    break;
            }
            return return_array;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Boolean write(Object object) {
        if (!object.getClass().isArray()) {
            System.out.println("this object is not a array");
            return false;
        }
        int dimension = object.getClass().getName().lastIndexOf("[") + 1;
        System.out.println(dimension);
        return true;
    }
}
