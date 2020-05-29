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
//            BufferedReader bw = new BufferedReader(new InputStreamReader(System.in));
//            OutputStream os = socket.getOutputStream();
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("Client \t" + line);
//                os.write(bw.readLine().getBytes());
            }
//            os.flush();

//            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
