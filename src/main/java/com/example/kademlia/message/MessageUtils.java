package com.example.kademlia.message;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.Node;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageUtils {
    public static void sendMessage(DatagramSocket ds, Node to, Message message) throws IOException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream(); DataOutputStream dout = new DataOutputStream(bout);)
        {
            /* Setup the message for transmission */
//            dout.writeInt(comm);
            dout.writeByte(message.code());
            dout.writeBytes(JSON.toJSONString(message));
//            message.toStream(dout);
            dout.close();

            byte[] data = bout.toByteArray();

            /* Everything is good, now create the packet and send it */
            DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
            pkt.setSocketAddress(to.getSocketAddress());
            ds.send(pkt);

//            byte[] bys = JSON.toJSONString(message).getBytes();
//            DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName(to.getInetAddress().getHostAddress()), to.getPort());
//            dp.setSocketAddress(to.getSocketAddress());
//            ds.send(dp);
        }


    }
}
