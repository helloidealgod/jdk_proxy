package com.example.thread;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 08:54
 */
public class ThreadExtend extends Thread {
    @Override
    public void run() {
        System.out.println("Thread");
    }

    public static void main(String[] argv) {
        Thread t = new ThreadExtend();
        t.start();
        System.out.println("Main");
    }
}
