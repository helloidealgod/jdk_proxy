package com.example.simulate.model;

/**
 * 增强版D触发器
 * 包含使能、异步置位/复位
 */
public class EnhancedDFlipFlop {
    private boolean q;
    private boolean qNot;
    private boolean prevClock;

    public EnhancedDFlipFlop() {
        this.q = false;
        this.qNot = true;
        this.prevClock = false;
    }

    /**
     * 完整功能D触发器更新
     *
     * @param d        数据输入
     * @param clock    时钟
     * @param enable   使能（高有效）
     * @param preset   异步置位（低有效）
     * @param clear    异步复位（低有效）
     * @param edgeType 触发类型：'R'-上升沿，'F'-下降沿，'H'-高电平，'L'-低电平
     */
    public void update(boolean d, boolean clock, boolean enable,
                       boolean preset, boolean clear, char edgeType) {

        // 异步置位/复位（优先级最高）
        if (!preset && !clear) {
            // 非法状态
            q = true;
            qNot = true;
            System.err.println("警告：PRESET和CLEAR同时有效！");
            return;
        }

        if (!preset) { // 异步置位
            q = true;
            qNot = false;
            return;
        }

        if (!clear) { // 异步复位
            q = false;
            qNot = true;
            return;
        }

        // 同步操作
        if (!enable) return; // 使能无效

        boolean edgeDetected = detectEdge(clock, edgeType);

        if (edgeDetected) {
            q = d;
            qNot = !d;
        }

        prevClock = clock;
    }

    private boolean detectEdge(boolean clock, char edgeType) {
        switch (edgeType) {
            case 'R': // 上升沿
                return clock && !prevClock;
            case 'F': // 下降沿
                return !clock && prevClock;
            case 'H': // 高电平
                return clock;
            case 'L': // 低电平
                return !clock;
            default:
                return false;
        }
    }

    // 获取状态
    public boolean getQ() {
        return q;
    }

    public boolean getQNot() {
        return qNot;
    }

    public String getState() {
        return String.format("Q=%d, Q'=%d", q ? 1 : 0, qNot ? 1 : 0);
    }
}
