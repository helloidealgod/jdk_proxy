package com.example.thread.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

import static org.reflections.util.ConfigurationBuilder.build;

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
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(availableProcessors, availableProcessors, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(), namedThreadFactory);
//        如果是CPU密集型应用，则线程池大小设置为N+1
//        如果是IO密集型应用，则线程池大小设置为2N+1
//        最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
//        最佳线程数目 = （线程等待时间与线程CPU时间之比 + 1）* CPU数目

        Future<Integer> future = executorService.submit(task1);
    }
}
