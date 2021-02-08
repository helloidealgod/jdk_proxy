package com.example.thread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2021/2/8 10:03
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        System.out.println("task submit");
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);
        //阻塞等待任务1完成
        future1.get();
        //阻塞等待任务2完成
        future2.get();
        System.out.println("task done");
    }
}
