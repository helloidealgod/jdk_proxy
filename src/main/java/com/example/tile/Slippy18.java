package com.example.tile;


import java.awt.image.BufferedImage;
import java.io.File;

import static com.example.idu.ImageUtils.*;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/7 13:49
 */
public class Slippy18 {
    public static void main(String[] args) {
        int zoom = 18;
        String rootPath = "E:/root/service/file_service/" + zoom;
        String desPath = "E:/root/service/file_service/" + zoom + "_change_math";
        TileUtils tileUtils = new TileUtils();
        long startTime = System.currentTimeMillis();
        File file = new File(rootPath);
        if (file.isDirectory()) {
//            String paths[] = file.list();
//            String paths[] = {"211614","211615","211616","211617","211618"};
            String paths[] = {"211616"};

            for (String s : paths) {
                int x = Integer.valueOf(s);
                File file1 = new File(rootPath + "/" + s);
//                String names[] = file1.list();
                String names[] = {"109756.png"};
                for (String n : names) {
                    int y = Integer.valueOf(n.substring(0, n.lastIndexOf(".")));
                    BufferedImage bufferedImage = readImage(rootPath + "/" + s + "/" + n);
                    //获取瓦片图片信息
                    int[][] image = convertImageToArray(bufferedImage);
//                    for (int i = 0; i < 256; i++) {
//                        for (int j = 0; j < 256; j++) {
                    //像素点转经纬度
                    Coordinate coordinate = tileUtils.pixelToLnglat(0, 0, x, y, zoom);
                    //经纬度坐标转换
                    Gps gps = GpsUtils.gps84ToGcj02(coordinate.getLatitude(), coordinate.getLongitude());
                    //经纬度转像素、瓦片
                    Pixel pixel = tileUtils.lnglatToPixel(gps.getWgLon(), gps.getWgLat(), zoom);
                    Tile tile = tileUtils.lnglatToTile(gps.getWgLon(), gps.getWgLat(), zoom);
//                        }
//                    }

                    int[][][] newImage = new int[4][256][256];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 256; j++) {
                            for (int l = 0; l < 256; l++) {
                                newImage[i][j][l] = 16777215;
                            }
                        }
                    }
                    bufferedImage = readImage(desPath + "/" + tile.tileX + "/" + tile.tileY + ".png");
                    if (null != bufferedImage) {
                        newImage[0] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (tile.tileX + 1) + "/" + (tile.tileY) + ".png");
                    if (null != bufferedImage) {
                        newImage[1] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (tile.tileX) + "/" + (tile.tileY + 1) + ".png");
                    if (null != bufferedImage) {
                        newImage[2] = convertImageToArray(bufferedImage);
                    }
                    bufferedImage = readImage(desPath + "/" + (tile.tileX + 1) + "/" + (tile.tileY + 1) + ".png");
                    if (null != bufferedImage) {
                        newImage[3] = convertImageToArray(bufferedImage);
                    }

                    for (int i = 0; i < 256; i++) {
                        for (int j = 0; j < 256; j++) {
                            if (i + pixel.pixelY < 256 && j + pixel.pixelX < 256) {
                                newImage[0][(i + pixel.pixelY) % 256][(j + pixel.pixelX) % 256] = image[i][j];
                            } else if (i + pixel.pixelY >= 256 && j + pixel.pixelX < 256) {
                                newImage[2][(i + pixel.pixelY) % 256][(j + pixel.pixelX) % 256] = image[i][j];
                            } else if (i + pixel.pixelY < 256 && j + pixel.pixelX >= 256) {
                                newImage[1][(i + pixel.pixelY) % 256][(j + pixel.pixelX) % 256] = image[i][j];
                            } else if (i + pixel.pixelY >= 256 && j + pixel.pixelX >= 256) {
                                newImage[3][(i + pixel.pixelY) % 256][(j + pixel.pixelX) % 256] = image[i][j];
                            }
                        }
                    }

                    File f = new File(desPath + "/" + tile.tileX);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    f = new File(desPath + "/" + (tile.tileX + 1));
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    writeImageFromArray(desPath + "/" + (tile.tileX) + "/" + (tile.tileY) + ".png", "png", newImage[0]);
                    writeImageFromArray(desPath + "/" + (tile.tileX + 1) + "/" + (tile.tileY) + ".png", "png", newImage[1]);
                    writeImageFromArray(desPath + "/" + (tile.tileX) + "/" + (tile.tileY + 1) + ".png", "png", newImage[2]);
                    writeImageFromArray(desPath + "/" + (tile.tileX + 1) + "/" + (tile.tileY + 1) + ".png", "png", newImage[3]);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 60000 + " m");
    }

}
