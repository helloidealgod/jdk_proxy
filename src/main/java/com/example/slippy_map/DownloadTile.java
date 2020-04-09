package com.example.slippy_map;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/4/9 14:15
 */
public class DownloadTile {
    int current_zoom;
    int current_x;
    int current_y;

    public void getHttpInterface(int zoom, int x, int y) {
        String savePath = "E:/root/service/file_service/tiles/" + zoom + "/" + x;
        String path = "http://mt1.google.cn/vt/lyrs=s&hl=zh-CN&gl=cn&x=" + x + "&y=" + y + "&z=" + zoom + "&s=Galile";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.connect();
            int code = connection.getResponseCode();
            if (200 == code) {
                InputStream inputStream = connection.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(savePath + "/" + y + ".png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                byte[] bytes = new byte[1024];
                int length = 0;
                while (-1 != (length = inputStream.read(bytes))) {
                    fileOutputStream.write(bytes, 0, length);
                }
                fileOutputStream.flush();
                inputStream.close();
                fileOutputStream.close();
                current_zoom = zoom;
                current_x = x;
                current_y = y;
            } else {
                System.out.println("接口异常：" + code);
                throw new RuntimeException("接口异常：" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("下载：" + savePath + "/" + y + ".png");
    }

    public static void repert() throws InterruptedException {
        DownloadTile downloadTile = new DownloadTile();
        long startTime = System.currentTimeMillis();
        for (int zoom = 15; zoom <= 15; zoom++) {
            String rootPath = "E:/root/service/file_service/" + zoom;
            File file = new File(rootPath);
            if (file.isDirectory()) {
                String paths[] = file.list();
                for (String s : paths) {
                    int x = Integer.valueOf(s);
                    File file1 = new File(rootPath + "/" + s);
                    String names[] = file1.list();
                    for (String n : names) {
                        int y = Integer.valueOf(n.substring(0, n.lastIndexOf(".")));
                        downloadTile.getHttpInterface(zoom, x, y);
                        //延时
                        Thread.sleep(10000);
                    }
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 60000 + " m");
    }

    public static void main(String[] args) throws InterruptedException {
        repert();
    }

}
