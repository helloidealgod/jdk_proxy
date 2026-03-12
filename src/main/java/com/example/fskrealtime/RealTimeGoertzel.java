package com.example.fskrealtime;

/**
 * 实时流 Goertzel 算法优化版本
 * 专门用于检测固定频率的能量
 */
public class RealTimeGoertzel {

    // 目标频率数组 (Hz)
    private final double[] targetFreqs;

    // 每个频率的状态变量 [freqIndex][0] = q1, [freqIndex][1] = q2
    private final double[][] states;

    // 每个频率的预计算系数
    private final double[] coeffs;

    // 采样率 (Hz)
    private final double sampleRate;

    // 每个比特需要的采样点数
    private final int samplesPerBit;

    // 当前已收集的采样点数
    private int sampleCount = 0;

    // 采样值缓存（可选，用于调试）
    private double[] sampleBuffer;
    private boolean enableBuffer = false;

    /**
     * 构造函数
     * @param sampleRate 采样率 (Hz)
     * @param targetFreqs 目标频率数组
     * @param samplesPerBit 每个比特需要的采样点数
     */
    public RealTimeGoertzel(double sampleRate, double[] targetFreqs, int samplesPerBit) {
        this.sampleRate = sampleRate;
        this.targetFreqs = targetFreqs.clone();
        this.samplesPerBit = samplesPerBit;

        int numFreqs = targetFreqs.length;
        this.states = new double[numFreqs][2];
        this.coeffs = new double[numFreqs];

        // 预计算所有频率的系数
        for (int i = 0; i < numFreqs; i++) {
            // 归一化角频率
            double omega = 2 * Math.PI * targetFreqs[i] / sampleRate;
            // Goertzel 系数: 2 * cos(ω)
            coeffs[i] = 2 * Math.cos(omega);
        }
    }

    /**
     * 启用采样缓存（用于调试）
     */
    public void enableSampleBuffer() {
        this.enableBuffer = true;
        this.sampleBuffer = new double[samplesPerBit];
    }

    /**
     * 处理一个新的采样值
     * @param sample 归一化的采样值 (-1.0 ~ 1.0)
     */
    public void processSample(double sample) {
        // 缓存采样值（如果启用）
        if (enableBuffer && sampleCount < samplesPerBit) {
            sampleBuffer[sampleCount] = sample;
        }

        // 对每个目标频率执行 Goertzel 迭代
        for (int i = 0; i < targetFreqs.length; i++) {
            double q0 = coeffs[i] * states[i][0] - states[i][1] + sample;
            states[i][1] = states[i][0];
            states[i][0] = q0;
        }

        sampleCount++;
    }

    /**
     * 获取所有频率的能量并重置状态
     * @return 每个频率的能量值数组
     */
    public double[] getPowersAndReset() {
        double[] powers = new double[targetFreqs.length];

        for (int i = 0; i < targetFreqs.length; i++) {
            // 计算能量: |X|² = q1² + q2² - coeff * q1 * q2
            double q1 = states[i][0];
            double q2 = states[i][1];
            double coeff = coeffs[i];

            powers[i] = q1 * q1 + q2 * q2 - coeff * q1 * q2;

            // 重置状态
            states[i][0] = 0;
            states[i][1] = 0;
        }

        sampleCount = 0;
        return powers;
    }

    /**
     * 获取归一化的能量（便于比较）
     */
    public double[] getNormalizedPowersAndReset() {
        double[] powers = getPowersAndReset();
        double max = 0;

        // 找到最大值
        for (double p : powers) {
            if (p > max) max = p;
        }

        // 归一化
        if (max > 0) {
            for (int i = 0; i < powers.length; i++) {
                powers[i] /= max;
            }
        }

        return powers;
    }

    /**
     * 检查是否已收集足够采样点
     */
    public boolean isReady() {
        return sampleCount >= samplesPerBit;
    }

    /**
     * 获取当前进度 (0.0 ~ 1.0)
     */
    public double getProgress() {
        return (double) sampleCount / samplesPerBit;
    }

    /**
     * 获取采样缓存（用于调试）
     */
    public double[] getSampleBuffer() {
        return enableBuffer ? sampleBuffer.clone() : null;
    }

    /**
     * 重置所有状态
     */
    public void reset() {
        for (int i = 0; i < targetFreqs.length; i++) {
            states[i][0] = 0;
            states[i][1] = 0;
        }
        sampleCount = 0;
    }

    /**
     * 获取目标频率数组
     */
    public double[] getTargetFreqs() {
        return targetFreqs.clone();
    }

    /**
     * 获取采样率
     */
    public double getSampleRate() {
        return sampleRate;
    }

    /**
     * 获取每个比特的采样点数
     */
    public int getSamplesPerBit() {
        return samplesPerBit;
    }
}
