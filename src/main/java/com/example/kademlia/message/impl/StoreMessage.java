package com.example.kademlia.message.impl;

import com.example.kademlia.message.Message;
import com.example.kademlia.node.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StoreMessage extends Message {
    public static final byte CODE = 0x03;
    private Node origin;
    private String key;
    private int total;
    private int current;
    private byte[] date;

    public StoreMessage() {

    }

    public StoreMessage(Node origin) {
        this.origin = origin;
    }

    public StoreMessage(DataInputStream in) throws IOException {
        this.fromStream(in);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public byte[] getDate() {
        return date;
    }

    public void setDate(byte[] date) {
        this.date = date;
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
