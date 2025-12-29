package com.example.simulate.model;

/**
 * 8位带使能寄存器（基础版）
 * 特性：8位并行输入/输出，时钟使能控制
 */
public class EightBitRegister {
    protected boolean[] data;       // 8位数据存储
    protected boolean prevClock;    // 上一时钟状态
    private final int WIDTH = 8;    // 寄存器宽度

    public EightBitRegister() {
        data = new boolean[WIDTH];
        prevClock = false;
    }

    /**
     * 寄存器更新
     *
     * @param inputs 8位输入数据
     * @param clock  时钟信号
     * @param enable 使能信号（高电平有效）
     */
    public void update(boolean[] inputs, boolean clock, boolean enable) {
        if (enable && clock && !prevClock) { // 上升沿且使能有效
            System.arraycopy(inputs, 0, data, 0, Math.min(WIDTH, inputs.length));
        }
        prevClock = clock;
    }

    /**
     * 获取寄存器当前值
     */
    public boolean[] getData() {
        boolean[] copy = new boolean[WIDTH];
        System.arraycopy(data, 0, copy, 0, WIDTH);
        return copy;
    }

    /**
     * 获取指定位的值
     */
    public boolean getBit(int index) {
        if (index < 0 || index >= WIDTH) {
            throw new IllegalArgumentException("位索引超出范围: " + index);
        }
        return data[index];
    }

    /**
     * 设置指定位的值（直接设置，不受时钟控制）
     */
    public void setBit(int index, boolean value) {
        if (index < 0 || index >= WIDTH) {
            throw new IllegalArgumentException("位索引超出范围: " + index);
        }
        data[index] = value;
    }

    /**
     * 获取寄存器值的整数表示
     */
    public int getValue() {
        int value = 0;
        for (int i = 0; i < WIDTH; i++) {
            if (data[i]) {
                value |= (1 << i);
            }
        }
        return value;
    }

    /**
     * 设置寄存器值（整数形式）
     */
    public void setValue(int value) {
        for (int i = 0; i < WIDTH; i++) {
            data[i] = ((value >> i) & 1) == 1;
        }
    }

    /**
     * 清空寄存器（所有位设为0）
     */
    public void clear() {
        for (int i = 0; i < WIDTH; i++) {
            data[i] = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = WIDTH - 1; i >= 0; i--) {
            sb.append(data[i] ? "1" : "0");
            if (i > 0) sb.append(" ");
        }
        sb.append("] (");
        sb.append(String.format("0x%02X", getValue()));
        sb.append(")");
        return sb.toString();
    }
}
