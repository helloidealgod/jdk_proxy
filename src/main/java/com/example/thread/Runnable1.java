package com.example.thread;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 09:00
 */
public class Runnable1 implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread");
    }

    public static void main(String[] argv) {
        Runnable1 r = new Runnable1();
        Thread t = new Thread(r);
        t.start();
        System.out.println("Main");
    }
}
