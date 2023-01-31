package com.example.kademlia.message.impl;

import com.example.kademlia.message.Message;
import com.example.kademlia.node.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FindValueMessage extends Message {
    public static final byte CODE = 0x07;
    private Node origin;
    private String fileId;

    public FindValueMessage() {

    }

    public FindValueMessage(Node origin) {
        this.origin = origin;
    }

    public FindValueMessage(Node origin, String fileId) {
        this.origin = origin;
        this.fileId = fileId;
    }

    public FindValueMessage(DataInputStream in) throws IOException {
        this.fromStream(in);
    }


    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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
