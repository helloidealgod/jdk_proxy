package com.example.kademlia;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.MessageFactory;
import com.example.kademlia.message.MessageUtils;
import com.example.kademlia.message.PingMessage;
import com.example.kademlia.node.Node;

import java.io.IOException;
import java.net.*;

public class KadServer {
    private static final int DATAGRAM_BUFFER_SIZE = 64 * 1024;
    private int port;
    private boolean isRun = true;
    private Node seedNode;
    private Node origin;
    private Bucket bucket = new Bucket();

    public KadServer() {

    }

    public KadServer(Node origin) {
        this.origin = origin;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Node getSeedNode() {
        return seedNode;
    }

    public void setSeedNode(Node seedNode) {
        this.seedNode = seedNode;
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public void start() throws IOException {
        if (null == this.origin) {
            return;
        }
        DatagramSocket ds;
        if (0 != this.origin.getPort()) {
            ds = new DatagramSocket(this.origin.getPort());
        } else {
            ds = new DatagramSocket();
            this.origin.setPort(ds.getLocalPort());
        }
        MessageFactory messageFactory = new MessageFactory();
        if (null != seedNode) {
            PingMessage pingMessage = new PingMessage(origin);
            MessageUtils.sendMessage(ds, seedNode, pingMessage);
        }
        while (isRun) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[DATAGRAM_BUFFER_SIZE];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);

            byte code = dp.getData()[0];
            String json = new String(dp.getData(), 1, dp.getLength() - 1);
            com.example.kademlia.message.Message message = messageFactory.createMessage(code, json);
            System.out.println(JSON.toJSONString(message));

        }
        ds.close();
    }

    public void stop() {
        this.isRun = false;
    }
}
