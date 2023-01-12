package com.example.kademlia;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.MessageFactory;
import com.example.kademlia.message.MessageUtils;
import com.example.kademlia.message.PingMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer2 {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        int port = ds.getLocalPort();
        Node origin = new Node(new NodeId(), InetAddress.getLocalHost(), port);
        System.out.println("port=" + port);
        Node seedNode = new Node(null, InetAddress.getByName("127.0.0.1"), 53420);
        MessageUtils.sendMessage(ds, seedNode, new PingMessage(origin));
        MessageFactory messageFactory = new MessageFactory();
        while (true) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[64 * 1024];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);

            byte code = dp.getData()[0];
            String json = new String(dp.getData(), 1, dp.getLength()-1);
            com.example.kademlia.message.Message message = messageFactory.createMessage(code, json);
            System.out.println(JSON.toJSONString(message));
            //ds.close();
        }
    }
}
