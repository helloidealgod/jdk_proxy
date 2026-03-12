package com.example.fskrealtime;

/**
 * 主入口类
 * 展示如何使用 FSK 接收器
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("FSK 音频解码器 - Goertzel算法");
        System.out.println("目标频率: 1200Hz (1), 2200Hz (0)");
        System.out.println("=================================\n");

        // 创建并启动接收器
        FSKReceiver receiver = new FSKReceiver();
        receiver.setDebug(true);  // 显示详细输出
        receiver.start();

        // 等待用户输入退出
        System.out.println("\n按回车键退出程序...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

        receiver.stop();
        System.out.println("程序结束");
    }
}
