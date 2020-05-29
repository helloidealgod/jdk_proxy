package com.example.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/29 10:29
 */
public class Servlet {
    public static void main(String[] args) throws Exception {

        ServerSocket sk = new ServerSocket(8080);
        // 创建一个可重用固定个数的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                1000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        while (true) {
            Socket s = sk.accept();
            threadPoolExecutor.execute(new HttpHandleThread(s));
        }
    }
}
