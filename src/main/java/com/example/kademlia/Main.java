package com.example.kademlia;

import com.example.kademlia.node.Node;
import com.example.kademlia.node.NodeId;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        KadServer server1 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost(), 54320));
//        KadServer server2 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));
//        KadServer server3 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));
//        KadServer server4 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));

//        server2.setSeedNode(server1.getOrigin());
//        server3.setSeedNode(server1.getOrigin());
//        server4.setSeedNode(new Node(InetAddress.getLocalHost(), 54320));

        server1.start();
//        server2.start();
//        server3.start();
//        server4.start();
    }
}
