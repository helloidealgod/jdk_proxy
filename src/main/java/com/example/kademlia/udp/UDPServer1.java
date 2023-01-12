package com.example.kademlia.udp;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.MessageFactory;
import com.example.kademlia.message.MessageUtils;
import com.example.kademlia.message.impl.PingMessage;
import com.example.kademlia.node.Node;
import com.example.kademlia.node.NodeId;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer1 {

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        int port = ds.getLocalPort();
        Node origin = new Node(new NodeId(), InetAddress.getLocalHost(), port);
        System.out.println("port=" + port);
        Node seedNode = new Node(null, InetAddress.getByName("127.0.0.1"), 53420);
        PingMessage pingMessage = new PingMessage(origin);
        MessageUtils.sendMessage(ds, seedNode, pingMessage);
        MessageFactory messageFactory = new MessageFactory();
        while (true) {
            byte[] bys = new byte[64 * 1024];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            ds.receive(dp);

            byte code = dp.getData()[0];
            String json = new String(dp.getData(), 1, dp.getLength()-1);
            com.example.kademlia.message.Message message = messageFactory.createMessage(code, json);
            System.out.println(JSON.toJSONString(message));

            //ds.close();
        }
    }
}
