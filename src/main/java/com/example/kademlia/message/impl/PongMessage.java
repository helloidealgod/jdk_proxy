package com.example.kademlia.message.impl;

import com.example.kademlia.message.Message;
import com.example.kademlia.node.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PongMessage extends Message {
    public static final byte CODE = 0x02;
    private Node origin;

    public PongMessage() {

    }

    public PongMessage(Node origin) {
        this.origin = origin;
    }

    public PongMessage(DataInputStream in) throws IOException {
        this.fromStream(in);
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }


    @Override
    public byte code() {
        return CODE;
    }

    @Override
    protected void fromStream(DataInputStream in) throws IOException {
        this.origin = new Node(in);
    }

    @Override
    public void toStream(DataOutputStream out) throws IOException {
        origin.toStream(out);
    }
}
