package com.example.morse;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AudioGenerator {
    public static void main(String[] args) {
        byte[] audioData = generateAudioData();
        // 将音频数据保存到文件或进行其他处理
    }

    public static byte[] generateAudioData() {
        // 设置音频格式
        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,     // 音频编码方式
                44100,                               // 采样率
                16,                                  // 样本大小（比特数）
                1,                                   // 声道数
                2,                                   // 每帧字节数 = (样本大小/8) * 声道数
                44100,                               // 每秒帧数 = 采样率
                false                                // 大端字节序
        );
        // 创建音频输入流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        AudioInputStream audioInputStream = new AudioInputStream(
                new ByteArrayInputStream(outputStream.toByteArray()), audioFormat,
                AudioSystem.NOT_SPECIFIED);
        // 生成音频数据
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;
            // 按照需要的格式向音频输入流写入数据
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                // 这里可以根据需要生成音频数据
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回生成的音频数据
        return outputStream.toByteArray();
    }
}
