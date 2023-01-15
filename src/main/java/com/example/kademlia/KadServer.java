package com.example.kademlia;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.Message;
import com.example.kademlia.message.MessageFactory;
import com.example.kademlia.message.impl.*;
import com.example.kademlia.node.Node;
import com.example.kademlia.node.NodeId;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class KadServer {
    private static final int DATAGRAM_BUFFER_SIZE = 64 * 1024;
    private int port;
    private boolean isRun;
    private Node seedNode;
    private Node origin;
    private DatagramSocket ds;
    private Bucket bucket = new Bucket();
    private Map<String, Node> table = new HashMap<>();
    private Timer timer;
    private final Map<Integer, TimerTask> tasks;

    {
        isRun = true;
        this.tasks = new HashMap<>();
        this.timer = new Timer(true);
    }

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
        new Thread() {
            @Override
            public void run() {
                try {
                    listen();
                } catch (IOException e) {

                }
            }
        }.start();
        Scanner sc = new Scanner(System.in);
        while (isRun) {
            System.out.print("ready:");
            String nextLine = sc.nextLine();
            String[] split = nextLine.split(" ");
            if (split[0].equals("getList")) {
                for (Map.Entry<String, Node> next : table.entrySet()) {
                    System.out.println(next.getValue());
                }
            } else if (split[0].equals("me")) {
                System.out.println(this.origin.getNodeId().toString());
            } else if (split[0].equals("ping")) {
                if (split[1] != null && !split[1].equals(this.origin.getNodeId().toString())) {
                    Node to = table.get(split[1]);
                    PingMessage ping = new PingMessage(this.origin);
                    sendMessage(ds, to, ping);
                }
            } else if (split[0].equals("find_node")) {
                Node to = new Node(InetAddress.getLocalHost(), Integer.parseInt(split[1]));
                FindNodeMessage findNodeMessage = new FindNodeMessage(origin);
                this.sendMessage(ds, to, findNodeMessage);
            }
        }
    }

    public void listen() throws IOException {
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
        System.out.println(origin.getPort() + " " + this.origin.getNodeId().toString());
        table.put(origin.getNodeId().toString(), origin);
        if (null != seedNode) {
            FindNodeMessage findNodeMessage = new FindNodeMessage(origin);
            this.sendMessage(ds, seedNode, findNodeMessage);
        }
        byte[] commBytes = new byte[4];
        while (isRun) {
            //创建一个数据包，用于接收数据
            byte[] bys = new byte[DATAGRAM_BUFFER_SIZE];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            // 调用DatagramSocket对象的方法接收数据
            ds.receive(dp);

            commBytes[3] = dp.getData()[0];
            commBytes[2] = dp.getData()[1];
            commBytes[1] = dp.getData()[2];
            commBytes[0] = dp.getData()[3];
            int comm = byteArrayToIntBest(commBytes);
            System.out.println("receive comm:" + comm);
            byte code = dp.getData()[4];
            String json = new String(dp.getData(), 5, dp.getLength() - 5);
            if (this.tasks.containsKey(comm)) {
                TimerTask remove = tasks.remove(comm);
                if (null != remove) {
                    remove.cancel();
                }
            }
            Message message = messageFactory.createMessage(code, json);

            reply(comm, code, message);
        }
        ds.close();
    }

    public void stop() {
        this.isRun = false;
    }

    public void reply(int comm, byte code, Message message) throws IOException {
        switch (code) {
            case PingMessage.CODE:
                PongMessage pongMessage = new PongMessage(this.origin);
                this.sendMessage(comm, ds, ((PingMessage) message).getOrigin(), pongMessage);
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
                System.out.println("Find Node form:" + nodeIdStr + " port = " + origin.getPort() + " table size = " + table.size());
                List<Node> nodeList = new ArrayList<>();
                for (Map.Entry<String, Node> next : table.entrySet()) {
                    nodeList.add(next.getValue());
                }
                FindNodeReplyMessage findNodeReplyMessage = new FindNodeReplyMessage(this.origin, nodeList);
                this.sendMessage(comm, ds, origin, findNodeReplyMessage);
                table.put(nodeIdStr, origin);
                System.out.println("table size = " + table.size());
                break;
            case FindNodeReplyMessage.CODE:
                FindNodeReplyMessage findNodeReplyMessage1 = (FindNodeReplyMessage) message;
                Node origin1 = findNodeReplyMessage1.getOrigin();
                String string = findNodeReplyMessage1.getOrigin().getNodeId().toString();
                List<Node> nodeList1 = findNodeReplyMessage1.getNodeList();
                //System.out.println(JSON.toJSONString(nodeList1));
                System.out.println("Find Node reply form:" + string + " port = " + origin1.getPort() + " table size = " + table.size());
                for (Node node : nodeList1) {
                    Node put = table.get(node.getNodeId().toString());
                    if (null == put) {
                        if (!findNodeReplyMessage1.getOrigin().getNodeId().equals(node.getNodeId())) {
                            FindNodeMessage findNodeMessage = new FindNodeMessage(this.origin);
                            this.sendMessage(comm, ds, node, findNodeMessage);
                        }
                    }
                    table.put(node.getNodeId().toString(), node);
                }
                System.out.println("table size = " + table.size());
                break;
            case FindValueMessage.CODE:
                break;
            case FindValueReplyMessage.CODE:
                break;
            default:
                break;
        }
    }

    public class TimeoutTask extends TimerTask {
        private int comm;

        public TimeoutTask(int comm) {
            this.comm = comm;
        }

        @Override
        public void run() {
            System.out.println(this.comm + " Time Out");
        }
    }

    public void sendMessage(DatagramSocket ds, Node to, Message message) throws IOException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream(); DataOutputStream dout = new DataOutputStream(bout);) {
            int comm = new Random().nextInt();
            System.out.println("send comm:" + comm);
            dout.writeInt(comm);
            dout.writeByte(message.code());
            dout.writeBytes(JSON.toJSONString(message));
            dout.close();

            byte[] data = bout.toByteArray();
            DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
            pkt.setSocketAddress(to.getSocketAddress());
            TimeoutTask task = new TimeoutTask(comm);
            timer.schedule(task, 20000);
            tasks.put(comm, task);
            ds.send(pkt);
        }
    }

    public void sendMessage(int comm, DatagramSocket ds, Node to, Message message) throws IOException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream(); DataOutputStream dout = new DataOutputStream(bout);) {
            System.out.println("send comm:" + comm);
            dout.writeInt(comm);
            dout.writeByte(message.code());
            dout.writeBytes(JSON.toJSONString(message));
            dout.close();

            byte[] data = bout.toByteArray();
            DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
            pkt.setSocketAddress(to.getSocketAddress());
            ds.send(pkt);
        }
    }

    public int byteArrayToIntBest(byte[] bytes) {
        return (bytes[0] & 0xff)
                | ((bytes[1] & 0xff) << 8)
                | ((bytes[2] & 0xff) << 16)
                | ((bytes[3] & 0xff) << 24);
    }
}
