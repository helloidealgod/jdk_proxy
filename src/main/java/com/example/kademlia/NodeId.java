package com.example.kademlia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class NodeId {
    public final transient static int ID_LENGTH = 160;
    private byte[] keyBytes;

    public NodeId() {
        keyBytes = new byte[ID_LENGTH / 8];
        new Random().nextBytes(keyBytes);
    }

    public NodeId(String data) {
        keyBytes = data.getBytes();
        if (keyBytes.length != ID_LENGTH / 8) {
            throw new IllegalArgumentException("Specified Data need to be " + (ID_LENGTH / 8) + " characters long.");
        }
    }

    public NodeId(DataInputStream in) throws IOException {
        this.fromStream(in);
    }

    public NodeId(byte[] bytes) {
        if (bytes.length != ID_LENGTH / 8) {
            throw new IllegalArgumentException("Specified Data need to be " + (ID_LENGTH / 8) + " characters long. Data Given: '" + new String(bytes) + "'");
        }
        this.keyBytes = bytes;
    }

    public byte[] getBytes() {
        return this.keyBytes;
    }

    public NodeId xor(NodeId nid) {
        byte[] result = new byte[ID_LENGTH / 8];
        byte[] nidBytes = nid.getBytes();
        for (int i = 0; i < ID_LENGTH / 8; i++) {
            result[i] = (byte) (this.keyBytes[i] ^ nidBytes[i]);
        }
        NodeId nodeId = new NodeId(result);
        return nodeId;
    }

    public void toStream(DataOutputStream out) throws IOException {
        /* Add the NodeId to the stream */
        out.write(this.getBytes());
    }

    public final void fromStream(DataInputStream in) throws IOException {
        byte[] input = new byte[ID_LENGTH / 8];
        in.readFully(input);
        this.keyBytes = input;
    }
}
