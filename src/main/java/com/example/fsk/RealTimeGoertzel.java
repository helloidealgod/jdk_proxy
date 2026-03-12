package com.example.fsk;

/**
 * 实时 Goertzel 滤波器
 * 适合流式音频数据（如从麦克风实时采集）
 */
public class RealTimeGoertzel {
    private final double[] targetFreqs;
    private final double[][] state; // [freqIndex][2] -> [q1, q2]
    private final double[] coeffs;
    private final double sampleRate;
    private final int requiredSamples;
    private int sampleCount = 0;

    public RealTimeGoertzel(double sampleRate, double[] targetFreqs, int requiredSamples) {
        this.sampleRate = sampleRate;
        this.targetFreqs = targetFreqs;
        this.requiredSamples = requiredSamples;

        int numFreqs = targetFreqs.length;
        this.state = new double[numFreqs][2]; // [freq][q1, q2]
        this.coeffs = new double[numFreqs];

        // 预计算所有频率的系数
        for (int i = 0; i < numFreqs; i++) {
            double omega = 2 * Math.PI * targetFreqs[i] / sampleRate;
            coeffs[i] = 2 * Math.cos(omega);
        }
    }

    /**
     * 处理一个新的采样值
     */
    public void processSample(double sample) {
        for (int i = 0; i < targetFreqs.length; i++) {
            double q0 = coeffs[i] * state[i][0] - state[i][1] + sample;
            state[i][1] = state[i][0];
            state[i][0] = q0;
        }
        sampleCount++;
    }

    /**
     * 获取所有频率的能量并重置
     */
    public double[] getPowersAndReset() {
        double[] powers = new double[targetFreqs.length];

        for (int i = 0; i < targetFreqs.length; i++) {
            powers[i] = state[i][0] * state[i][0]
                    + state[i][1] * state[i][1]
                    - coeffs[i] * state[i][0] * state[i][1];

            // 重置状态
            state[i][0] = 0;
            state[i][1] = 0;
        }

        sampleCount = 0;
        return powers;
    }

    /**
     * 检查是否已收集足够采样点
     */
    public boolean isReady() {
        return sampleCount >= requiredSamples;
    }

    /**
     * 获取当前进度
     */
    public double getProgress() {
        return (double) sampleCount / requiredSamples;
    }
}
