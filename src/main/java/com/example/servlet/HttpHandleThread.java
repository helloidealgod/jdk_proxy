package com.example.servlet;

import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/29 16:16
 */
public class HttpHandleThread implements Runnable {
    private Socket client;

    public HttpHandleThread(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {
        try {
            //获取到客户端输入流
            InputStream in = client.getInputStream();
            //解析HTTP报文信息
            getHttpRequest(in);

            //制作响应报文
            StringBuffer response = new StringBuffer();
            //响应状态
            response.append("HTTP/1.1 200 OK\r\n");
            //响应头
            response.append("Content-type:text/html\r\n\r\n");
            //要返回的内容(当前时间)
            response.append("CurrentTime: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //获取客户端的输出流
            OutputStream out = client.getOutputStream();
            //将以上内容写入
            out.write(response.toString().getBytes());
            //关闭客户端和服务端的流和Socket
            client.shutdownOutput();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpRequest getHttpRequest(InputStream inputStream) throws IOException {
        StringBuffer stringBuilder = new StringBuffer();
        byte bytes[] = new byte[128];
        int len = 0;
        int totalLength = 0;
        while (-1 != (len = inputStream.read(bytes))) {
            stringBuilder.append(new String(bytes, 0, len));
            totalLength += len;
            if (bytes.length > len) break;
        }
        System.out.println(stringBuilder.toString());
        System.out.println("===================================");
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setData(stringBuilder.toString());
        System.out.println("http byte length=" + totalLength);
        System.out.println("string byte length=" + httpRequest.getData().getBytes().length);
//        String[] split = httpRequest.getData().split("\r\n\r\n");
        return httpRequest;
    }

}
