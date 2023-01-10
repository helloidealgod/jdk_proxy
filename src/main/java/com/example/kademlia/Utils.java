package com.example.kademlia;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Utils {
    public static void sendMessage(String ip, int port, String message) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        byte[] bys = message.getBytes();
        DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName(ip), port);
        ds.send(dp);
        ds.close();
    }
}
