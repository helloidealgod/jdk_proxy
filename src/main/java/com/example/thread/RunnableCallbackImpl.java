package com.example.thread;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 09:09
 */
public class RunnableCallbackImpl {
    public void callback(String msg) {
        System.out.println(msg);
    }

    public static void callBackStatic(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] argv) {
        RunnableCallbackImpl runnableCallback = new RunnableCallbackImpl();
        RunnableCallback r = new RunnableCallback(runnableCallback);
        Thread t = new Thread(r);
        t.start();
        System.out.println("Main");
        RunnableCallback r1 = new RunnableCallback(runnableCallback, new RunnableCallbackImpl0() {
            @Override
            public void callback(String msg) {
                System.out.println(msg);
            }
        });
        Thread t1 = new Thread(r1);
        t1.start();
        System.out.println("Main1");
    }
}
