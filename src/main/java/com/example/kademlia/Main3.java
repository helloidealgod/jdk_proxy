package com.example.kademlia;

import java.io.IOException;
import java.net.InetAddress;

public class Main3 {
    public static void main(String[] args) throws IOException {
        KadServer server3 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));

        server3.setSeedNode(new Node(InetAddress.getLocalHost(), 54320));

        server3.start();
    }
}
