package com.example.thread;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 09:00
 */
public class RunnableCallback implements Runnable {
    private RunnableCallbackImpl runnableCallback;
    private RunnableCallbackImpl0 runnableCallback0;

    public RunnableCallback(RunnableCallbackImpl runnableCallback) {
        this.runnableCallback = runnableCallback;
    }

    public RunnableCallback(RunnableCallbackImpl runnableCallback, RunnableCallbackImpl0 runnableCallback0) {
        this.runnableCallback = runnableCallback;
        this.runnableCallback0 = runnableCallback0;
    }

    @Override
    public void run() {
        System.out.println("Thread");
        runnableCallback.callback("Thread Return");
        RunnableCallbackImpl.callBackStatic("Thread Return static");
        if (null != runnableCallback0) {
            runnableCallback0.callback("Thread Return abstract");
        }
    }
}
