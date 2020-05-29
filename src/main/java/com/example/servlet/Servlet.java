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
            Socket socket = sk.accept();
            threadPoolExecutor.execute(new HttpHandleThread(socket));
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            OutputStream outputStream = socket.getOutputStream();
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            String path = "/swagger-ui.html";
//            String host = "localhost:8080";
//            outputStream.write(("GET " + path + " HTTP/1.1\r\n").getBytes());
//            outputStream.write(("Host: " + host + " \r\n").getBytes());
//            //http协议必须在报文头后面再加一个换行，通知服务器发送完成，不然服务器会一直等待
//            outputStream.write("\r\n".getBytes());
//            outputStream.flush();
//            socket.shutdownOutput();
//            socket.close();
        }
    }
}
