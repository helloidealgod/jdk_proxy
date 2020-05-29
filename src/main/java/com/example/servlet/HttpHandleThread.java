package com.example.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/29 16:16
 */
public class HttpHandleThread implements Runnable {
    private Socket socket;

    public HttpHandleThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream outputStream = socket.getOutputStream();
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            socket.shutdownInput();
            outputStream.write("hao".getBytes());
            outputStream.flush();
            socket.shutdownOutput();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
