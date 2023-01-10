package com.example.kademlia;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.example.kademlia.Utils.sendMessage;

public class UDPServer2 {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        int port = ds.getLocalPort();
        Node node1 = new Node(new NodeId(), InetAddress.getLocalHost(), port);
        String hostAddress = node1.getInetAddress().getHostAddress();
        System.out.println("port=" + port);
        sendMessage(new Message("PING", node1, "127.0.0.1", 53420, "hello i am server2"));
        while (true) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[1024];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);
            // 解析数据包，并把数据在控制台显示
            String s = new String(dp.getData(), 0, dp.getLength());
            System.out.println("数据是：" + s);
            Message message = JSON.parseObject(s, Message.class);
            if ("PING".equals(message.getOperateType())) {
                System.out.println("ip=" + message.getFromIp() + " port=" + message.getFromPort());
            } else if ("STORE".equals(message.getOperateType())) {

            } else if ("FIND_NODE".equals(message.getOperateType())) {

            } else if ("FIND_VALUE".equals(message.getOperateType())) {

            }
            //关闭接收端
            //ds.close();
        }
    }
}
