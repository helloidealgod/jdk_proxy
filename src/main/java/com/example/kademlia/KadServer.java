package com.example.kademlia;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.Message;
import com.example.kademlia.message.MessageFactory;
import com.example.kademlia.message.MessageUtils;
import com.example.kademlia.message.impl.*;
import com.example.kademlia.node.Node;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class KadServer {
    private static final int DATAGRAM_BUFFER_SIZE = 64 * 1024;
    private int port;
    private boolean isRun = true;
    private Node seedNode;
    private Node origin;
    private DatagramSocket ds;
    private Bucket bucket = new Bucket();
    private Map<String, Node> table = new HashMap<>();

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
        if (0 != this.origin.getPort()) {
            ds = new DatagramSocket(this.origin.getPort());
        } else {
            ds = new DatagramSocket();
            this.origin.setPort(ds.getLocalPort());
        }
        MessageFactory messageFactory = new MessageFactory();
        System.out.println(origin.getPort());
        table.put(origin.getNodeId().toString(), origin);
//        if (null != seedNode) {
//            PingMessage pingMessage = new PingMessage(origin);
//            MessageUtils.sendMessage(ds, seedNode, pingMessage);
//        }
        if (null != seedNode) {
            FindNodeMessage findNodeMessage = new FindNodeMessage(origin);
            MessageUtils.sendMessage(ds, seedNode, findNodeMessage);
        }
        while (isRun) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[DATAGRAM_BUFFER_SIZE];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);

            byte code = dp.getData()[0];
            String json = new String(dp.getData(), 1, dp.getLength() - 1);
            Message message = messageFactory.createMessage(code, json);
            System.out.println(JSON.toJSONString(message));

            reply(code, message);
        }
        ds.close();
    }

    public void stop() {
        this.isRun = false;
    }

    public void reply(byte code, Message message) throws IOException {
        switch (code) {
            case PingMessage.CODE:
                PongMessage pongMessage = new PongMessage(this.origin);
                MessageUtils.sendMessage(ds, ((PingMessage) message).getOrigin(), pongMessage);
                break;
            case PongMessage.CODE:
                break;
            case StoreMessage.CODE:
                break;
            case StoreReplyMessage.CODE:
                break;
            case FindNodeMessage.CODE:
                Node origin = ((FindNodeMessage) message).getOrigin();
                String nodeIdStr = origin.getNodeId().toString();
                List<Node> nodeList = new ArrayList<>();
                for (Map.Entry<String, Node> next : table.entrySet()) {
                    nodeList.add(next.getValue());
                }
                FindNodeReplyMessage findNodeReplyMessage = new FindNodeReplyMessage(this.origin, nodeList);
                MessageUtils.sendMessage(ds, origin, findNodeReplyMessage);
                table.put(nodeIdStr, origin);
                break;
            case FindNodeReplyMessage.CODE:
                FindNodeReplyMessage findNodeReplyMessage1 = (FindNodeReplyMessage) message;
                List<Node> nodeList1 = findNodeReplyMessage1.getNodeList();
                System.out.println(JSON.toJSONString(nodeList1));
                break;
            case FindValueMessage.CODE:
                break;
            case FindValueReplyMessage.CODE:
                break;
            default:
                break;
        }
    }
}
