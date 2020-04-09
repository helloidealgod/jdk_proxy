package com.example.slippy_map;


import java.awt.image.BufferedImage;
import java.io.File;

import static com.example.idu.ImageUtils.*;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/7 13:49
 */
public class Slippy18Test {
    public static void main(String[] args) {
        int zoom = 18;
        int deltax = 858;
        int deltay = 756;
        String rootPath = "E:/root/service/file_service/" + zoom;
        String desPath = "E:/root/service/file_service/" + zoom + "_change_thread";
        long startTime = System.currentTimeMillis();
        File file = new File(rootPath);
        if (file.isDirectory()) {
            String paths[] = {"211610", "211611", "211612", "211613", "211614", "211615", "211616", "211617"};

            for (String s : paths) {
                int x = Integer.valueOf(s);
                File file1 = new File(rootPath + "/" + s);
                String names[] = file1.list();
                for (String n : names) {
                    int y = Integer.valueOf(n.substring(0, n.lastIndexOf(".")));
                    BufferedImage bufferedImage = readImage(rootPath + "/" + s + "/" + n);
                    int[][] image = convertImageToArray(bufferedImage);
                    int bx = deltax / 256;
                    int by = deltay / 256;
                    int[][][] newImage = new int[4][256][256];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 256; j++) {
                            for (int l = 0; l < 256; l++) {
                                newImage[i][j][l] = 16777215;
                            }
                        }
                    }
                    bufferedImage = readImage(desPath + "/" + (x + bx) + "/" + (y + by) + ".png");
                    if (null != bufferedImage) {
                        newImage[0] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (x + bx + 1) + "/" + (y + by) + ".png");
                    if (null != bufferedImage) {
                        newImage[1] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (x + bx) + "/" + (y + by + 1) + ".png");
                    if (null != bufferedImage) {
                        newImage[2] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (x + bx + 1) + "/" + (y + by + 1) + ".png");
                    if (null != bufferedImage) {
                        newImage[3] = convertImageToArray(bufferedImage);
                    }
                    for (int i = 0; i < 256; i++) {
                        for (int j = 0; j < 256; j++) {
                            if ((i + deltay % 256) / 256 <= 0 && (j + deltax % 256) / 256 <= 0) {
                                newImage[0][(i + deltay) % 256][(j + deltax) % 256] = image[i][j];
                            } else if ((i + deltay % 256) / 256 > 0 && (j + deltax % 256) / 256 <= 0) {
                                newImage[2][(i + deltay) % 256][(j + deltax) % 256] = image[i][j];
                            } else if ((i + deltay % 256) / 256 <= 0 && (j + deltax % 256) / 256 > 0) {
                                newImage[1][(i + deltay) % 256][(j + deltax) % 256] = image[i][j];
                            } else if ((i + deltay % 256) / 256 > 0 && (j + deltax % 256) / 256 > 0) {
                                newImage[3][(i + deltay) % 256][(j + deltax) % 256] = image[i][j];
                            }
                        }
                    }

                    File f = new File(desPath + "/" + (x + bx));
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    f = new File(desPath + "/" + (x + bx + 1));
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    writeImageFromArray(desPath + "/" + (x + bx) + "/" + (y + by) + ".png", "png", newImage[0]);
                    writeImageFromArray(desPath + "/" + (x + bx + 1) + "/" + (y + by) + ".png", "png", newImage[1]);
                    writeImageFromArray(desPath + "/" + (x + bx) + "/" + (y + by + 1) + ".png", "png", newImage[2]);
                    writeImageFromArray(desPath + "/" + (x + bx + 1) + "/" + (y + by + 1) + ".png", "png", newImage[3]);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 60000 + " m");
    }
}
