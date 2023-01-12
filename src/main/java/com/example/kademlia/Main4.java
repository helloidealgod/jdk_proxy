package com.example.kademlia;

import java.io.IOException;
import java.net.InetAddress;

public class Main4 {
    public static void main(String[] args) throws IOException {
        KadServer server4 = new KadServer(new Node(new NodeId(), InetAddress.getLocalHost()));

        server4.setSeedNode(new Node(InetAddress.getLocalHost(), 54320));

        server4.start();
    }
}
