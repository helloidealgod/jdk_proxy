package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.example.nio.Utils.byte2Byffer;
import static com.example.nio.Utils.bytebuffer2ByteArray;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/12 19:25
 */
public class Service {
    public static void main(String[] argv) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8090));
            //非阻塞模式
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 6、采用轮询的方式，查询获取“准备就绪”的注册过的操作
            while (true) {
                if (selector.select() > 0) {
                    // 7、获取当前选择器中所有注册的选择键（“已经准备就绪的操作”）
                    Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext()) {
                        // 8、获取“准备就绪”的时间
                        SelectionKey selectedKey = selectedKeys.next();
                        // 9、判断key是具体的什么事件
                        if (selectedKey.isAcceptable()) {
                            // 10、若接受的事件是“接收就绪” 操作,就获取客户端连接
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // 11、切换为非阻塞模式
                            socketChannel.configureBlocking(false);
                            // 12、将该通道注册到selector选择器上
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (selectedKey.isReadable()) {
                            // 13、获取该选择器上的“读就绪”状态的通道
                            SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
                            // 14、读取数据
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int read = socketChannel.read(byteBuffer);
                            byte[] bytes = bytebuffer2ByteArray(byteBuffer);
                            System.out.println("read=" + read);
                            String msg0 = new String(bytes);
                            if ("bye".equals(msg0)) {
                                break;
                            }
                            System.out.println("content：" + msg0);
//                            socketChannel.close();
                        }
                        // 15、移除选择键
                        selectedKeys.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 7、关闭连接
            try {
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
