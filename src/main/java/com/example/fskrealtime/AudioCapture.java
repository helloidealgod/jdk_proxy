package com.example.fskrealtime;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 音频采集类
 * 从麦克风实时采集音频数据
 */
public class AudioCapture {

    // 音频格式参数
    private AudioFormat format;
    private TargetDataLine line;
    private Thread captureThread;
    private volatile boolean isRunning = false;

    // 采样回调
    private AudioListener listener;

    // 采样缓冲区
    private byte[] buffer;
    private static final int BUFFER_SIZE = 1024; // 每次读取的字节数

    /**
     * 音频数据监听器
     */
    public interface AudioListener {
        void onAudioSample(double sample);

        void onError(String error);
    }

    /**
     * 构造函数
     *
     * @param sampleRate 采样率 (Hz)
     * @param sampleBits 采样位数 (8/16)
     * @param channels   通道数 (1/2)
     */
    public AudioCapture(float sampleRate, int sampleBits, int channels) {
        // 创建音频格式
        this.format = new AudioFormat(
                sampleRate,           // 采样率
                sampleBits,           // 采样位数
                channels,             // 通道数
                true,                 // 有符号
                false                 // 小端序
        );

        this.buffer = new byte[BUFFER_SIZE];
    }

    /**
     * 设置音频数据监听器
     */
    public void setListener(AudioListener listener) {
        this.listener = listener;
    }

    /**
     * 开始采集
     */
    public boolean start() {
        try {
            // 获取音频输入设备
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("音频格式不支持: " + format);
                return false;
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format, BUFFER_SIZE);
            line.start();

            isRunning = true;

            // 启动采集线程
            captureThread = new Thread(new CaptureRunnable());
            captureThread.setName("AudioCapture");
            captureThread.start();

            System.out.println("音频采集已启动: " + format);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError("启动音频采集失败: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * 停止采集
     */
    public void stop() {
        isRunning = false;

        if (captureThread != null) {
            try {
                captureThread.join(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        if (line != null) {
            line.stop();
            line.close();
            line = null;
        }

        System.out.println("音频采集已停止");
    }

    /**
     * 采集线程
     */
    private class CaptureRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("采集线程启动");

            while (isRunning && line != null) {
                try {
                    // 读取音频数据
                    int bytesRead = line.read(buffer, 0, buffer.length);

                    if (bytesRead > 0 && listener != null) {
                        // 将字节转换为双精度采样值
                        convertAndNotify(bytesRead);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError("采集错误: " + e.getMessage());
                    }
                    break;
                }
            }

            System.out.println("采集线程结束");
        }

        /**
         * 转换字节数据为双精度采样值并通知监听器
         */
        private void convertAndNotify(int bytesRead) {
            AudioFormat.Encoding encoding = format.getEncoding();
            int sampleSizeInBits = format.getSampleSizeInBits();
            int channels = format.getChannels();

            // 每帧字节数
            int bytesPerFrame = (sampleSizeInBits / 8) * channels;

            for (int i = 0; i < bytesRead - bytesPerFrame + 1; i += bytesPerFrame) {
                double sample = 0;

                // 只处理第一个通道（左声道）
                if (sampleSizeInBits == 16) {
                    // 16位有符号 PCM
                    short s = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xFF));
                    sample = s / 32768.0; // 归一化到 -1.0 ~ 1.0
                } else if (sampleSizeInBits == 8) {
                    // 8位有符号 PCM
                    sample = buffer[i] / 128.0;
                }

                listener.onAudioSample(sample);
            }
        }
    }

    /**
     * 获取音频格式
     */
    public AudioFormat getFormat() {
        return format;
    }

    /**
     * 检查是否正在运行
     */
    public boolean isRunning() {
        return isRunning;
    }
}
