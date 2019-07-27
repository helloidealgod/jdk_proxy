package com.example.idu;

import java.io.*;

import static com.example.idu.ByteUtil.bytes2Int;
import static com.example.idu.ByteUtil.inverted;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/27 09:55
 */
public class IDUFileUtils {
    public static void main(String[] argv) {
//        int array1[] = new int[10];
//        int array2[][] = new int[10][10];
//        int array3[][][] = new int[10][10][10];
//        int array4[][][][] = new int[10][10][10][10];
//        String s = "abc";
//        String s1[] = new String[2];
//        String s2[][] = new String[2][2];
        IDUFileUtils iduFileUtils = new IDUFileUtils();
//        iduFileUtils.write(array1);
//        iduFileUtils.write(array2);
//        iduFileUtils.write(array3);
//        iduFileUtils.write(array4);
//        iduFileUtils.write(s);
//        iduFileUtils.write(s1);
//        iduFileUtils.write(s2);
        iduFileUtils.read("C:\\Users\\aorise\\Desktop\\octave\\resource\\t10k-images.idx3-ubyte");
        iduFileUtils.read("C:\\Users\\aorise\\Desktop\\octave\\resource\\t10k-labels.idx1-ubyte");

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
            System.out.printf("data type = %02x\n", bytes[0]);
            //读取数据维度
            fis.read(bytes, 0, 1);
            System.out.printf("dimension = %02x ", bytes[0]);
            System.out.println(bytes2Int(bytes));
            int dimension = bytes[0];
            for (int i = 1; i <= dimension; i++) {
                fis.read(bytes);
                System.out.printf("dimension %d = %02x %02x %02x %02x ", i, bytes[0], bytes[1], bytes[2], bytes[3]);
                System.out.println(bytes2Int(inverted(bytes)));
            }
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
