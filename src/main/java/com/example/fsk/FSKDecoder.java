package com.example.fsk;

/**
 * FSK (Frequency Shift Keying) 解码器
 * 用于区分 1200Hz 和 2200Hz
 */
public class FSKDecoder {

    // 解码参数
    private static final double SAMPLE_RATE = 9600.0;  // 采样率 Hz
    private static final double FREQ_MARK = 1200.0;    // 代表 "1" (或"0", 根据你的定义)
    private static final double FREQ_SPACE = 2200.0;   // 代表 "0" (或"1")
    private static final int SAMPLES_PER_BIT = 96;     // 每个比特采样数 (9600/100bps = 96)

    private double[] buffer;
    private int bufferIndex = 0;

    public FSKDecoder() {
        buffer = new double[SAMPLES_PER_BIT];
    }

    /**
     * 处理新的采样值
     *
     * @param sample ADC采样值（应已归一化）
     * @return 如果解码出一个比特返回该比特，否则返回 -1
     */
    public int processSample(double sample) {
        buffer[bufferIndex++] = sample;

        if (bufferIndex >= SAMPLES_PER_BIT) {
            // 解码一个比特
            int bit = decodeBit();

            // 重置缓冲区
            bufferIndex = 0;

            return bit;
        }

        return -1; // 还在收集采样点
    }

    /**
     * 解码当前缓冲区中的一个比特
     */
    private int decodeBit() {
        // 计算两个频率的能量
        double powerMark = GoertzelDetector.goertzel(buffer, SAMPLE_RATE, FREQ_MARK);
        double powerSpace = GoertzelDetector.goertzel(buffer, SAMPLE_RATE, FREQ_SPACE);

        // 比较能量并判决
        if (powerMark > powerSpace) {
            return 1; // 1200Hz 代表 1
        } else {
            return 0; // 2200Hz 代表 0
        }
    }

    /**
     * 带能量阈值的解码（更稳健）
     */
    private int decodeBitWithThreshold() {
        double powerMark = GoertzelDetector.goertzel(buffer, SAMPLE_RATE, FREQ_MARK);
        double powerSpace = GoertzelDetector.goertzel(buffer, SAMPLE_RATE, FREQ_SPACE);

        double totalPower = powerMark + powerSpace;
        double ratio = powerMark / totalPower;

        // 设定阈值，防止噪声误判
        double THRESHOLD = 0.6; // 60% 能量集中在某一频率

        if (ratio > THRESHOLD) {
            return 1;
        } else if (ratio < (1 - THRESHOLD)) {
            return 0;
        } else {
            return -2; // 无法判决（噪声）
        }
    }
}
