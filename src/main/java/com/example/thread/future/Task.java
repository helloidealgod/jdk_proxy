package com.example.thread.future;

import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 10:03
 */
public class Task implements Callable<Integer> {
    private String msg;

    public Task(String msg) {
        this.msg = msg;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(msg);
        return null;
    }
}
