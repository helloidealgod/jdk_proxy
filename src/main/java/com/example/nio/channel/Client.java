package com.example.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.util.UUID;

import static com.example.nio.Utils.byte2Byffer;
import static com.example.nio.Utils.bytebuffer2ByteArray;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/12 19:25
 */
public class Client {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            //设置为非阻塞模式
//            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
            while (true) {
//                for (int i = 0; i < 10; i++) {
                String msg = "hello server, this is client " + UUID.randomUUID().toString();
                byte[] bytes = msg.getBytes();
                ByteBuffer buffer = byte2Byffer(bytes);
                socketChannel.write(buffer);
//                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = 0;
//                    while (1024 <= (read = socketChannel.read(byteBuffer))) {
                byte[] bytes0 = bytebuffer2ByteArray(byteBuffer);
                System.out.println("read=" + read);
                System.out.println("content：" + new String(bytes0));
//                    }
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
