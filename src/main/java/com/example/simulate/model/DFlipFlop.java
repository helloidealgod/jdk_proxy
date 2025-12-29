package com.example.simulate.model;

/**
 * 基础D触发器
 * 特性：在时钟边沿将D的值传递给Q
 */
public class DFlipFlop {
    protected boolean q;        // 输出Q
    protected boolean qNot;     // 反相输出Q'
    protected boolean prevClock; // 上一时钟状态（用于边沿检测）

    public DFlipFlop() {
        this.q = false;
        this.qNot = true;
        this.prevClock = false;
    }

    /**
     * 上升沿触发
     *
     * @param d     数据输入
     * @param clock 时钟信号
     */
    public void risingEdgeTrigger(boolean d, boolean clock) {
        if (clock && !prevClock) { // 检测上升沿
            q = d;
            qNot = !d;
        }
        prevClock = clock;
    }

    /**
     * 下降沿触发
     */
    public void fallingEdgeTrigger(boolean d, boolean clock) {
        if (!clock && prevClock) { // 检测下降沿
            q = d;
            qNot = !d;
        }
        prevClock = clock;
    }

    /**
     * 电平触发（透明锁存器）
     */
    public void levelTrigger(boolean d, boolean clock) {
        if (clock) { // 时钟为高时透明
            q = d;
            qNot = !d;
        }
        // 时钟为低时保持
    }

    // 获取输出
    public boolean getQ() {
        return q;
    }

    public boolean getQNot() {
        return qNot;
    }

    // 异步置位/复位
    public void asyncSet(boolean set, boolean reset) {
        if (set && !reset) {
            q = true;
            qNot = false;
        } else if (!set && reset) {
            q = false;
            qNot = true;
        } else if (set && reset) {
            // 非法状态
            q = true;
            qNot = true;
        }
    }

    @Override
    public String toString() {
        return String.format("Q=%d, Q'=%d", q ? 1 : 0, qNot ? 1 : 0);
    }
}
