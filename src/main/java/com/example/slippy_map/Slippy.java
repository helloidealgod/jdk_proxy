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
public class Slippy extends Thread {
    String[] paths;
    int zoom = 16;
    int deltax = 222;
    int deltay = 174;
    String rootPath = "E:/root/service/file_service/" + zoom;
    String desPath = "E:/root/service/file_service/" + zoom + "_change_thread";

    public Slippy(String[] paths, int start, int length) {
        this.paths = new String[length];
        for (int i = 0; i < length; i++) {
            this.paths[i] = paths[i + start];
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
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
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 60000 + " m");
    }

    public static void main(String[] args) {
        int zoom = 16;
        String rootPath = "E:/root/service/file_service/" + zoom;
        File file = new File(rootPath);
        if (file.isDirectory()) {
            String[] paths = file.list();
            System.out.println("length=" + paths.length);
            Slippy slippy1 = new Slippy(paths,0,69);
            Slippy slippy2 = new Slippy(paths,69,69);
            slippy1.start();
            slippy2.start();
            System.out.println("length=" + paths.length);
        }
    }
}
