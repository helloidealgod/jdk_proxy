package com.example.fskrealtime;

import java.util.ArrayList;
import java.util.List;

/**
 * FSK (Frequency Shift Keying) 解码器
 * 用于区分 1200Hz 和 2200Hz
 */
public class FSKDecoder {

    // 解码参数
    public static final double FREQ_MARK = 1200.0;   // 代表 "1"
    public static final double FREQ_SPACE = 2200.0;  // 代表 "0"

    // 解码状态
    private RealTimeGoertzel goertzel;
    private List<Integer> receivedBits;
    private DecodeListener listener;

    // 判决阈值 (0.5 ~ 0.8)
    private double threshold = 0.6;

    // 统计信息
    private int totalBitsDecoded = 0;
    private int ambiguousDecisions = 0;

    /**
     * 解码监听器
     */
    public interface DecodeListener {
        void onBitDecoded(int bit);

        void onByteDecoded(byte data);

        void onMessageReceived(String message);

        void onError(String error);
    }

    /**
     * 构造函数
     *
     * @param sampleRate    采样率
     * @param samplesPerBit 每个比特采样数
     */
    public FSKDecoder(double sampleRate, int samplesPerBit) {
        double[] targetFreqs = {FREQ_MARK, FREQ_SPACE};
        this.goertzel = new RealTimeGoertzel(sampleRate, targetFreqs, samplesPerBit);
        this.receivedBits = new ArrayList<>();
    }

    /**
     * 设置解码监听器
     */
    public void setListener(DecodeListener listener) {
        this.listener = listener;
    }

    /**
     * 设置判决阈值
     */
    public void setThreshold(double threshold) {
        if (threshold > 0 && threshold < 1) {
            this.threshold = threshold;
        }
    }

    /**
     * 处理新的音频采样值
     *
     * @param sample 归一化采样值 (-1.0 ~ 1.0)
     */
    public void processAudioSample(double sample) {
        goertzel.processSample(sample);

        if (goertzel.isReady()) {
            decodeCurrentBit();
        }
    }

    /**
     * 处理音频缓冲区（批量处理）
     *
     * @param samples 采样值数组
     */
    public void processAudioBuffer(double[] samples) {
        for (double sample : samples) {
            processAudioSample(sample);
        }
    }

    /**
     * 解码当前比特
     */
    private void decodeCurrentBit() {
        double[] powers = goertzel.getNormalizedPowersAndReset();
        double powerMark = powers[0];  // 1200Hz 能量
        double powerSpace = powers[1]; // 2200Hz 能量

        int bit = -1;
        String debug = String.format("能量比 - 1200Hz:%.3f 2200Hz:%.3f", powerMark, powerSpace);

        // 判决逻辑
        if (powerMark > threshold && powerMark > powerSpace) {
            bit = 1;  // 1200Hz 代表 1
            debug += " → 判决为 1";
        } else if (powerSpace > threshold && powerSpace > powerMark) {
            bit = 0;  // 2200Hz 代表 0
            debug += " → 判决为 0";
        } else {
            ambiguousDecisions++;
            debug += " → 无法判决 (噪声)";
        }

        System.out.println(debug);

        if (bit >= 0) {
            receivedBits.add(bit);
            totalBitsDecoded++;

            if (listener != null) {
                listener.onBitDecoded(bit);
            }

            // 每8比特组成一个字节
            if (receivedBits.size() == 8) {
                assembleByte();
            }
        }
    }

    /**
     * 将8个比特组装成一个字节
     */
    private void assembleByte() {
        if (receivedBits.size() < 8) return;

        byte data = 0;
        for (int i = 0; i < 8; i++) {
            if (receivedBits.get(i) == 1) {
                data |= (1 << (7 - i)); // MSB first
            }
        }

        System.out.printf("收到字节: 0x%02X (%c)\n", data, (data >= 32 && data <= 126) ? (char) data : '.');

        if (listener != null) {
            listener.onByteDecoded(data);
        }

        // 清除已处理的比特
        receivedBits.clear();
    }

    /**
     * 获取当前收到的比特列表
     */
    public List<Integer> getReceivedBits() {
        return new ArrayList<>(receivedBits);
    }

    /**
     * 获取统计信息
     */
    public String getStatistics() {
        return String.format("总解码比特: %d, 模糊判决: %d, 成功率: %.1f%%",
                totalBitsDecoded, ambiguousDecisions,
                totalBitsDecoded > 0 ?
                        100.0 * (totalBitsDecoded - ambiguousDecisions) / totalBitsDecoded : 0);
    }

    /**
     * 重置解码器
     */
    public void reset() {
        goertzel.reset();
        receivedBits.clear();
        totalBitsDecoded = 0;
        ambiguousDecisions = 0;
    }
}
