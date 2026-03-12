package com.example.fsk;

public class GoertzelExample {
    public static void main(String[] args) {
        // 示例 1: 基本用法
        double[] samples = generateTestSignal(1200.0, 9600.0, 96);

        double power1200 = GoertzelDetector.goertzel(samples, 9600.0, 1200.0);
        double power2200 = GoertzelDetector.goertzel(samples, 9600.0, 2200.0);

        System.out.printf("1200Hz 能量: %.2f\n", power1200);
        System.out.printf("2200Hz 能量: %.2f\n", power2200);

        // 示例 2: FSK 实时解码
        FSKDecoder decoder = new FSKDecoder();

        // 模拟实时采样
        double[] signal = generateTestSignal(1200.0, 9600.0, 960); // 10个比特
        for (int i = 0; i < signal.length; i++) {
            int bit = decoder.processSample(signal[i]);
            if (bit >= 0) {
                System.out.println("解码到比特: " + bit);
            }
        }

        // 示例 3: 多个频率检测
        double[] freqs = {697.0, 770.0, 852.0, 941.0}; // DTMF 频率
        double[] powers = GoertzelDetector.goertzelMultiple(samples, 9600.0, freqs);
        for (int i = 0; i < freqs.length; i++) {
            System.out.printf("%.0fHz: %.2f\n", freqs[i], powers[i]);
        }

        // 示例 4: 优化版本（适合实时流）
        GoertzelDetector.OptimizedDetector detector =
                new GoertzelDetector.OptimizedDetector(9600.0, 1200.0, 96);

        for (int i = 0; i < 96; i++) {
            detector.addSample(samples[i]);
        }

        if (detector.isReady()) {
            double power = detector.getPowerAndReset();
            System.out.println("优化版能量: " + power);
        }
    }

    /**
     * 生成测试信号
     */
    private static double[] generateTestSignal(double freq, double sampleRate, int numSamples) {
        double[] signal = new double[numSamples];
        for (int i = 0; i < numSamples; i++) {
            signal[i] = Math.sin(2 * Math.PI * freq * i / sampleRate);
        }
        return signal;
    }
}
