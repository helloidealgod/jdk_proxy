package com.example.simulate.model;

/**
 * JK触发器基础实现
 * 特性：当J=K=1时，输出翻转
 */
public class JKFlipFlop {
    private boolean q;      // 当前输出Q
    private boolean qNot;   // 当前反相输出Q'
    private boolean prevClock; // 上一个时钟状态（用于边沿检测）

    public JKFlipFlop() {
        this.q = false;
        this.qNot = true;
        this.prevClock = false;
    }

    /**
     * 时钟上升沿触发
     *
     * @param j     J输入
     * @param k     K输入
     * @param clock 时钟信号
     */
    public void clockRisingEdge(boolean j, boolean k, boolean clock) {
        if (clock && !prevClock) { // 检测上升沿
            updateOutput(j, k);
        }
        prevClock = clock;
    }

    /**
     * 时钟下降沿触发
     */
    public void clockFallingEdge(boolean j, boolean k, boolean clock) {
        if (!clock && prevClock) { // 检测下降沿
            updateOutput(j, k);
        }
        prevClock = clock;
    }

    /**
     * 电平触发（非边沿触发）
     */
    public void clockLevel(boolean j, boolean k, boolean clock, boolean enable) {
        if (clock && enable) {
            updateOutput(j, k);
        }
    }

    private void updateOutput(boolean j, boolean k) {
        if (j && !k) {
            // J=1, K=0：置1
            q = true;
            qNot = false;
        } else if (!j && k) {
            // J=0, K=1：置0
            q = false;
            qNot = true;
        } else if (j && k) {
            // J=1, K=1：翻转
            q = !q;
            qNot = !qNot;
        }
        // J=0, K=0：保持（不做任何操作）
    }

    // 获取当前输出
    public boolean getQ() {
        return q;
    }

    public boolean getQNot() {
        return qNot;
    }

    // 异步置位/复位
    public void setPreset(boolean preset, boolean clear) {
        if (preset && !clear) {
            q = true;
            qNot = false;
        } else if (!preset && clear) {
            q = false;
            qNot = true;
        } else if (preset && clear) {
            // 非法状态（在实际电路中应避免）
            q = true;
            qNot = true;
        }
    }

    @Override
    public String toString() {
        return String.format("Q=%d, Q'=%d", q ? 1 : 0, qNot ? 1 : 0);
    }
}
