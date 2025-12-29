package com.example.simulate.model;

/**
 * 增强版8位寄存器
 * 特性：使能、异步置位/复位、字节/半字节操作
 */
public class EnhancedEightBitRegister {
    private boolean[] data = new boolean[8];
    private boolean prevClock = false;

    // 控制信号
    public enum OperationMode {
        HOLD,       // 保持
        LOAD,       // 加载数据
        CLEAR,      // 清零
        SET,        // 置位（全1）
        SHIFT_LEFT, // 左移
        SHIFT_RIGHT // 右移
    }

    /**
     * 完整更新函数
     *
     * @param inputs  输入数据
     * @param clock   时钟
     * @param enable  使能
     * @param preset  异步置位（低有效）
     * @param clear   异步复位（低有效）
     * @param mode    操作模式
     * @param shiftIn 移位时的输入位
     */
    public void update(boolean[] inputs, boolean clock, boolean enable,
                       boolean preset, boolean clear, OperationMode mode,
                       boolean shiftIn) {

        // 异步控制（优先级最高）
        if (!clear && !preset) {
            // 两者都有效 - 非法状态，通常清零优先
            asyncClear();
            System.err.println("警告：PRESET和CLEAR同时有效，执行清零");
            return;
        }

        if (!clear) {
            asyncClear();
            return;
        }

        if (!preset) {
            asyncSet();
            return;
        }

        // 同步操作
        if (!enable) return;

        boolean clockEdge = clock && !prevClock; // 上升沿检测
        if (!clockEdge) return;

        switch (mode) {
            case HOLD:
                // 保持原值，不操作
                break;

            case LOAD:
                if (inputs != null) {
                    loadData(inputs);
                }
                break;

            case CLEAR:
                asyncClear();
                break;

            case SET:
                asyncSet();
                break;

            case SHIFT_LEFT:
                shiftLeft(shiftIn);
                break;

            case SHIFT_RIGHT:
                shiftRight(shiftIn);
                break;
        }

        prevClock = clock;
    }

    /**
     * 异步清零（所有位置0）
     */
    private void asyncClear() {
        for (int i = 0; i < 8; i++) {
            data[i] = false;
        }
    }
    public void setValue(int value) {
        for (int i = 0; i < 8; i++) {
            data[i] = ((value >> i) & 1) == 1;
        }
    }
    /**
     * 获取指定位的值
     */
    public boolean getBit(int index) {
        if (index < 0 || index >= 8) {
            throw new IllegalArgumentException("位索引超出范围: " + index);
        }
        return data[index];
    }

    /**
     * 设置指定位的值（直接设置，不受时钟控制）
     */
    public void setBit(int index, boolean value) {
        if (index < 0 || index >= 8) {
            throw new IllegalArgumentException("位索引超出范围: " + index);
        }
        data[index] = value;
    }
    /**
     * 异步置位（所有位置1）
     */
    private void asyncSet() {
        for (int i = 0; i < 8; i++) {
            data[i] = true;
        }
    }

    /**
     * 加载数据
     */
    private void loadData(boolean[] inputs) {
        int len = Math.min(8, inputs.length);
        System.arraycopy(inputs, 0, data, 0, len);

        // 如果输入数据不足8位，高位保持原值
        for (int i = len; i < 8; i++) {
            data[i] = false;
        }
    }

    /**
     * 左移操作
     */
    private void shiftLeft(boolean shiftIn) {
        // data[7] ← data[6] ← ... ← data[0] ← shiftIn
        for (int i = 7; i > 0; i--) {
            data[i] = data[i - 1];
        }
        data[0] = shiftIn;
    }

    /**
     * 右移操作
     */
    private void shiftRight(boolean shiftIn) {
        // shiftIn → data[7] → data[6] → ... → data[0]
        for (int i = 0; i < 7; i++) {
            data[i] = data[i + 1];
        }
        data[7] = shiftIn;
    }

    /**
     * 字节操作
     */
    public void loadByte(byte b) {
        for (int i = 0; i < 8; i++) {
            data[i] = ((b >> i) & 1) == 1;
        }
    }

    public byte toByte() {
        byte result = 0;
        for (int i = 0; i < 8; i++) {
            if (data[i]) {
                result |= (1 << i);
            }
        }
        return result;
    }

    /**
     * 半字节（4位）操作
     */
    public void setLowNibble(boolean[] nibble) {
        for (int i = 0; i < 4 && i < nibble.length; i++) {
            data[i] = nibble[i];
        }
    }

    public void setHighNibble(boolean[] nibble) {
        for (int i = 0; i < 4 && i < nibble.length; i++) {
            data[i + 4] = nibble[i];
        }
    }

    public boolean[] getLowNibble() {
        boolean[] nibble = new boolean[4];
        System.arraycopy(data, 0, nibble, 0, 4);
        return nibble;
    }

    public boolean[] getHighNibble() {
        boolean[] nibble = new boolean[4];
        System.arraycopy(data, 4, nibble, 0, 4);
        return nibble;
    }

    /**
     * 位运算操作
     */
    public void andWith(boolean[] mask) {
        for (int i = 0; i < 8 && i < mask.length; i++) {
            data[i] = data[i] && mask[i];
        }
    }

    public void orWith(boolean[] mask) {
        for (int i = 0; i < 8 && i < mask.length; i++) {
            data[i] = data[i] || mask[i];
        }
    }

    public void xorWith(boolean[] mask) {
        for (int i = 0; i < 8 && i < mask.length; i++) {
            data[i] = data[i] ^ mask[i];
        }
    }

    public void not() {
        for (int i = 0; i < 8; i++) {
            data[i] = !data[i];
        }
    }

    // 获取数据
    public boolean[] getData() {
        boolean[] copy = new boolean[8];
        System.arraycopy(data, 0, copy, 0, 8);
        return copy;
    }

    public int getValue() {
        int value = 0;
        for (int i = 0; i < 8; i++) {
            if (data[i]) {
                value |= (1 << i);
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return String.format("0x%02X [%s]",
                getValue(),
                toBinaryString());
    }

    public String toBinaryString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            sb.append(data[i] ? "1" : "0");
        }
        return sb.toString();
    }
}
