package com.example.slippy_map;


import java.awt.image.BufferedImage;
import java.io.File;

import static com.example.idu.ImageUtils.convertImageToArray;
import static com.example.idu.ImageUtils.readImage;
import static com.example.idu.ImageUtils.writeImageFromArray;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/7 13:49
 */
public class Slippy {
    public static void main(String[] args) {
        String rootPath = "E:/14";
        String desPath = "E:/14_chage";
        int deltax = 0;
        int deltay = 0;
        File file = new File(rootPath);
        if (file.isDirectory()) {
            String paths[] = file.list();

            for (String s : paths) {
                int x = Integer.valueOf(s);
                File file1 = new File(rootPath + "/" + s);
                String names[] = file1.list();
                for (String n : names) {
                    int y = Integer.valueOf(n.substring(0,n.lastIndexOf(".")));
                    BufferedImage bufferedImage = readImage(rootPath + "/" + s + "/" + n);
                    int[][] image = convertImageToArray(bufferedImage);
                    //todo 左上角加偏移后
                    int[][] newImage = new int[512][512];
                    //16777215
                    for (int i = 0; i < 512; i++) {
                        for (int j = 0; j < 512; j++) {
                            newImage[i][j] = 16777215;
                        }
                    }
                    for (int i = 0; i < 256; i++) {
                        for (int j = 0; j < 256; j++) {
                            newImage[i][j] = 256;
                            newImage[i][j] = image[i][j];
                        }
                    }

                    File f = new File(desPath + "/" + s);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    writeImageFromArray(desPath + "/" + s + "/" + n, "png", newImage);
                }
            }
        }
    }
}
