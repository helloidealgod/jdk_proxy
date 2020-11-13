package com.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static com.example.nio.Utils.byte2Byffer;
import static com.example.nio.Utils.bytebuffer2ByteArray;

/**
 * @Description: 这两个方法read()和readLine()都会读取对端发送过来的数据，如果无数据可读，就会阻塞直到有数据可读。或者到达流的末尾，这个时候分别返回-1和null。
 * 这个特性使得编程非常方便也很高效。
 * 但是这样也有一个问题，就是如何让程序从这两个方法的阻塞调用中返回。
 * <p>
 * 总结一下，有这么几个方法：
 * 1）发送完后调用Socket的shutdownOutput()方法关闭输出流，这样对端的输入流上的read操作就会返回-1。
 * 注意不能调用socket.getInputStream().close()。这样会导致socket被关闭。
 * 当然如果不需要继续在socket上进行读操作，也可以直接关闭socket。
 * 但是这个方法不能用于通信双方需要多次交互的情况。
 * <p>
 * 2）发送数据时，约定数据的首部固定字节数为数据长度。这样读取到这个长度的数据后，就不继续调用read方法。
 * @Author: xiankun.jiang
 * @Date: 2020/11/12 19:25
 */
public class Client {
    public static void main(String[] args) {
        try {
            InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8090);
            SocketChannel socketChannel = SocketChannel.open(address);
            //设置为非阻塞模式
            socketChannel.configureBlocking(false);
//            socketChannel.connect(address);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        if (socketChannel.isConnected()) {
                            System.out.print("Please Input: ");
                            String msg = scanner.nextLine();
                            byte[] bytes = msg.getBytes();
                            ByteBuffer buffer = byte2Byffer(bytes);
                            try {
                                socketChannel.write(buffer);
//                            socketChannel.shutdownOutput();
                            } catch (IOException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        //阻塞，直到有数据可读或者到达流的末尾
                        int read = 0;
                        if (socketChannel.isConnected()) {


                            try {
                                read = socketChannel.read(byteBuffer);
                                if (0 < read) {
                                    byte[] bytes0 = bytebuffer2ByteArray(byteBuffer);
                                    System.out.println("read=" + read);
                                    System.out.println("content：" + new String(bytes0));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
