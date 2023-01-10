package com.example.kademlia;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.example.kademlia.Utils.sendMessage;

public class UDPServer1 {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        int port = ds.getLocalPort();
        Node node1 = new Node(new NodeId(), InetAddress.getLocalHost(), port);
        String hostAddress = node1.getInetAddress().getHostAddress();
        System.out.println("port=" + port);
        sendMessage("127.0.0.1",53420,"PING");
        while (true) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[1024];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);
            // 解析数据包，并把数据在控制台显示
            String s = new String(dp.getData(), 0, dp.getLength());
            System.out.println("数据是：" + s);
            if ("PING".equals(s)) {

            }else if("STORE".equals(s)){

            }else if("FIND_NODE".equals(s)){

            }else if("FIND_VALUE".equals(s)){

            }
            //关闭接收端
            //ds.close();
        }
    }
}
