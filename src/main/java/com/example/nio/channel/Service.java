package com.example.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static com.example.nio.Utils.byte2Byffer;
import static com.example.nio.Utils.bytebuffer2ByteArray;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/12 19:25
 */
public class Service {
    public static void main(String[] argv) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //todo ??
            //serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.socket().bind(new InetSocketAddress(8090));
            //非阻塞模式
            //serverSocketChannel.configureBlocking(false);
            while (true) {
                System.out.println("wait...");
                SocketChannel accept = serverSocketChannel.accept();
                //非阻塞模式打开后，serverSocketChannel.accept() 无论有没有连接都会立即返回，即 accept 有可能 = null;
                if (null != accept) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("accept");
                            while (true) {
                                try {
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    int read = accept.read(byteBuffer);
                                    byte[] bytes = bytebuffer2ByteArray(byteBuffer);
                                    System.out.println("read=" + read);
                                    String msg0 = new String(bytes);
                                    if("bye".equals(msg0)){
                                        break;
                                    }
                                    System.out.println("content：" + msg0);
                                    String msg = "hello client, this is server " + msg0;
                                    byte[] bytes0 = msg.getBytes();
                                    ByteBuffer buffer = byte2Byffer(bytes0);
                                    accept.write(buffer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                            }
                            System.out.println("结束");
//                            try {
////                                accept.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }).start();

                } else {
                    System.out.println("No accept");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
