package com.example.kademlia;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.MessageFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.example.kademlia.Utils.sendMessage;

public class UDPServer {

    public static void main(String[] args) throws IOException {
        //Bucket buckets = new Bucket;
        //创建接收端的Socket对象(DatagramSocket)
        DatagramSocket ds = new DatagramSocket(53420);
        int port = ds.getLocalPort();
        Node node1 = new Node(new NodeId(), InetAddress.getLocalHost(), port);
        System.out.println("port=" + port);
        Bucket bucket = new Bucket();
        bucket.getNodeList().add(node1);
        MessageFactory messageFactory = new MessageFactory();
        while (true) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[1024];
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
