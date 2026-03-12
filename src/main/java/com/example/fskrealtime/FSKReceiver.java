package com.example.fskrealtime;

import java.io.*;

/**
 * FSK 接收主类
 * 集成音频采集和FSK解码
 */
public class FSKReceiver implements AudioCapture.AudioListener, FSKDecoder.DecodeListener {

    // 配置参数
    private static final float SAMPLE_RATE = 9600.0f;  // 采样率
    private static final int SAMPLE_BITS = 16;         // 采样位数
    private static final int CHANNELS = 1;             // 单声道
    private static final int SAMPLES_PER_BIT = 96;     // 每个比特采样数 (9600/100)

    private AudioCapture audioCapture;
    private FSKDecoder decoder;
    private boolean debug = true;
    // 输出文件（可选）
    private PrintWriter logWriter;

    /**
     * 构造函数
     */
    public FSKReceiver() {
        // 初始化音频采集
        this.audioCapture = new AudioCapture(SAMPLE_RATE, SAMPLE_BITS, CHANNELS);
        this.audioCapture.setListener(this);
        // 初始化FSK解码器
        this.decoder = new FSKDecoder(SAMPLE_RATE, SAMPLES_PER_BIT);
        this.decoder.setListener(this);
        this.decoder.setThreshold(0.6); // 设置判决阈值
    }

    /**
     * 启动接收
     */
    public void start() {
        System.out.println("FSK接收器启动...");
        System.out.println("采样率: " + SAMPLE_RATE + "Hz");
        System.out.println("解码频率: 1200Hz (1), 2200Hz (0)");
        System.out.println("速率: " + (SAMPLE_RATE / SAMPLES_PER_BIT) + " bps");
        // 打开日志文件
        try {
            logWriter = new PrintWriter(new FileWriter("fsk_received.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 启动音频采集
        if (!audioCapture.start()) {
            System.err.println("启动音频采集失败");
            return;
        }
        System.out.println("等待信号...");
    }

    /**
     * 停止接收
     */
    public void stop() {
        audioCapture.stop();
        if (logWriter != null) {
            logWriter.close();
        }
        System.out.println("\n接收器已停止");
        System.out.println(decoder.getStatistics());
    }

    /**
     * 设置调试模式
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    // ============ AudioCapture.AudioListener ============

    @Override
    public void onAudioSample(double sample) {
        // 将音频采样送入解码器
        decoder.processAudioSample(sample);
    }

    @Override
    public void onError(String error) {
        System.err.println("音频错误: " + error);
    }

    // ============ FSKDecoder.DecodeListener ============

    @Override
    public void onBitDecoded(int bit) {
        if (debug) {
            System.out.print(bit);
        }
    }

    @Override
    public void onByteDecoded(byte data) {
        if (debug) {
            System.out.printf(" [0x%02X %c]\n", data,
                    (data >= 32 && data <= 126) ? (char) data : '.');
        }
        // 写入日志
        if (logWriter != null) {
            logWriter.print((char) data);
            logWriter.flush();
        }
    }

    @Override
    public void onMessageReceived(String message) {
        System.out.println("\n收到消息: " + message);
    }

    /**
     * 主函数
     */
    public static void main(String[] args) {
        FSKReceiver receiver = new FSKReceiver();
        // 添加关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            receiver.stop();
        }));
        // 启动接收
        receiver.start();
        // 等待用户输入退出
        System.out.println("\n按回车键停止...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        receiver.stop();
    }
}
