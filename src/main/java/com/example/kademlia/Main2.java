package com.example.kademlia;

import java.io.IOException;
import java.net.InetAddress;

public class Main2 {
    public static void main(String[] args) throws IOException {
        KadServer server2 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));

        server2.setSeedNode(new Node(InetAddress.getLocalHost(), 54320));

        server2.start();
    }
}
