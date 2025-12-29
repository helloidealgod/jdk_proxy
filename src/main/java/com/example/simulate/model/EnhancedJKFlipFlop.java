package com.example.simulate.model;

/**
 * 增强版JK触发器
 * 包含使能端和异步置位/复位
 */
public class EnhancedJKFlipFlop {
    private boolean q;
    private boolean qNot;
    private boolean prevClock;
    private boolean lastJ;
    private boolean lastK;

    public EnhancedJKFlipFlop() {
        this.q = false;
        this.qNot = true;
        this.prevClock = false;
    }

    /**
     * 完整功能JK触发器
     *
     * @param j        J输入
     * @param k        K输入
     * @param clock    时钟
     * @param enable   使能（高有效）
     * @param preset   异步置位（低有效）
     * @param clear    异步复位（低有效）
     * @param edgeType 边沿类型：'R'-上升沿，'F'-下降沿
     */
    public void update(boolean j, boolean k, boolean clock,
                       boolean enable, boolean preset, boolean clear,
                       char edgeType) {

        // 异步置位/复位（优先级最高）
        if (!preset && !clear) {
            // 两个都低电平 - 非法状态
            q = true;
            qNot = true;
            System.out.println("警告：PRESET和CLEAR同时为低电平！");
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

        boolean edgeDetected = false;

        switch (edgeType) {
            case 'R': // 上升沿
                edgeDetected = clock && !prevClock;
                break;
            case 'F': // 下降沿
                edgeDetected = !clock && prevClock;
                break;
            case 'L': // 高电平
                edgeDetected = clock;
                break;
            case 'H': // 低电平
                edgeDetected = !clock;
                break;
        }

        if (edgeDetected) {
            processJK(j, k);
        }

        prevClock = clock;
        lastJ = j;
        lastK = k;
    }

    private void processJK(boolean j, boolean k) {
        if (j && !k) {
            // 置1
            q = true;
            qNot = false;
        } else if (!j && k) {
            // 置0
            q = false;
            qNot = true;
        } else if (j && k) {
            // 翻转
            boolean newQ = !q;
            q = newQ;
            qNot = !newQ;
        }
        // J=0,K=0 保持
    }

    public boolean getQ() {
        return q;
    }

    public boolean getQNot() {
        return qNot;
    }

    public String getState() {
        return String.format("Q=%d, Q'=%d | J=%d, K=%d",
                q ? 1 : 0, qNot ? 1 : 0, lastJ ? 1 : 0, lastK ? 1 : 0);
    }
}
