package com.example.fsk;

/**
 * Goertzel 算法实现 - 用于检测特定频率的能量
 */
public class GoertzelDetector {

    /**
     * 计算指定频率的能量
     * @param samples     采样数据数组
     * @param sampleRate  采样率 (Hz)
     * @param targetFreq  目标检测频率 (Hz)
     * @return            目标频率的能量值
     */
    public static double goertzel(double[] samples, double sampleRate, double targetFreq) {
        int N = samples.length;

        // 计算归一化频率
        double normalizedFreq = targetFreq / sampleRate;

        // 计算系数
        double omega = 2 * Math.PI * normalizedFreq;
        double coeff = 2 * Math.cos(omega);

        // 初始化状态变量
        double q0 = 0, q1 = 0, q2 = 0;

        // 迭代计算
        for (int i = 0; i < N; i++) {
            q0 = coeff * q1 - q2 + samples[i];
            q2 = q1;
            q1 = q0;
        }

        // 计算能量
        double power = q1 * q1 + q2 * q2 - coeff * q1 * q2;

        return power;
    }

    /**
     * 计算多个频率的能量
     * @param samples     采样数据数组
     * @param sampleRate  采样率 (Hz)
     * @param targetFreqs 目标频率数组
     * @return            每个频率的能量值数组
     */
    public static double[] goertzelMultiple(double[] samples, double sampleRate, double[] targetFreqs) {
        int N = samples.length;
        double[] powers = new double[targetFreqs.length];

        for (int f = 0; f < targetFreqs.length; f++) {
            double normalizedFreq = targetFreqs[f] / sampleRate;
            double omega = 2 * Math.PI * normalizedFreq;
            double coeff = 2 * Math.cos(omega);

            double q0 = 0, q1 = 0, q2 = 0;

            // 对每个频率重新计算
            for (int i = 0; i < N; i++) {
                q0 = coeff * q1 - q2 + samples[i];
                q2 = q1;
                q1 = q0;
            }

            powers[f] = q1 * q1 + q2 * q2 - coeff * q1 * q2;
        }

        return powers;
    }

    /**
     * 使用预计算系数的优化版本（适合固定频率）
     */
    public static class OptimizedDetector {
        private final double coeff;
        private double q1 = 0;
        private double q2 = 0;
        private int sampleCount = 0;
        private final int requiredSamples;

        /**
         * 创建优化检测器
         * @param sampleRate    采样率
         * @param targetFreq    目标频率
         * @param requiredSamples 需要的采样点数
         */
        public OptimizedDetector(double sampleRate, double targetFreq, int requiredSamples) {
            double normalizedFreq = targetFreq / sampleRate;
            double omega = 2 * Math.PI * normalizedFreq;
            this.coeff = 2 * Math.cos(omega);
            this.requiredSamples = requiredSamples;
        }

        /**
         * 添加一个采样值
         */
        public void addSample(double sample) {
            double q0 = coeff * q1 - q2 + sample;
            q2 = q1;
            q1 = q0;
            sampleCount++;
        }

        /**
         * 获取当前能量并重置状态
         */
        public double getPowerAndReset() {
            double power = q1 * q1 + q2 * q2 - coeff * q1 * q2;
            q1 = q2 = 0;
            sampleCount = 0;
            return power;
        }

        /**
         * 检查是否已收集足够采样点
         */
        public boolean isReady() {
            return sampleCount >= requiredSamples;
        }

        /**
         * 获取已收集采样点数
         */
        public int getSampleCount() {
            return sampleCount;
        }
    }
}
