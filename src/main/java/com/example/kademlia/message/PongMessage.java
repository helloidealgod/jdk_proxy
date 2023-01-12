package com.example.kademlia.message;

import com.example.kademlia.node.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PongMessage extends Message {
    public byte code = 0x02;
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

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    @Override
    public byte code() {
        return code;
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
