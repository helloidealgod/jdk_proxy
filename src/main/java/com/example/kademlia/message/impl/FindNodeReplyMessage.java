package com.example.kademlia.message.impl;

import com.example.kademlia.message.Message;
import com.example.kademlia.node.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FindNodeReplyMessage extends Message {
    public static final byte CODE = 0x06;
    private Node origin;

    public FindNodeReplyMessage() {

    }

    public FindNodeReplyMessage(Node origin) {
        this.origin = origin;
    }

    public FindNodeReplyMessage(DataInputStream in) throws IOException {
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
