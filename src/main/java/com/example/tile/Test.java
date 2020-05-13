package com.example.tile;

import java.awt.image.BufferedImage;

import static com.example.idu.ImageUtils.convertImageToArray;
import static com.example.idu.ImageUtils.readImage;
import static com.example.idu.ImageUtils.writeImageFromArray;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/13 12:45
 */
public class Test {
    public static void main(String[] args) {
        String rootPath = "E:/root/service/file_service/" + "109940.png";
        String descPath = "E:/root/service/file_service/" + "109940_test.png";
        BufferedImage bufferedImage = readImage(rootPath);
        //获取瓦片图片信息
        int[][] image = convertImageToArray(bufferedImage);
        int[][] imageTest = convertImageToArray(bufferedImage);
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                imageTest[i][j] = image[i][j];
                if (16777215 == image[i][j]) {
                    System.out.println("a: " + i);
                }
                if (0 == image[i][j]) {
                    System.out.println("0: " + i);
                }
                if (-1 == image[i][j]) {
                    System.out.println("-1: " + i);
                    imageTest[i][j] = 0;
                }
            }
        }
        writeImageFromArray(descPath, "png", imageTest);
    }
}
