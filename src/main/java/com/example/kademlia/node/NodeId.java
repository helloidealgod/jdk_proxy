package com.example.kademlia.node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
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

    public byte[] getKeyBytes() {
        return keyBytes;
    }

    public void setKeyBytes(byte[] keyBytes) {
        this.keyBytes = keyBytes;
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

    public int getFirstSetBitIndex() {
        int prefixLength = 0;
        for (byte b : this.keyBytes) {
            if (b == 0) {
                prefixLength += 8;
            } else {
                /* If the byte is not 0, we need to count how many MSBs are 0 */
                int count = 0;
                for (int i = 7; i >= 0; i--) {
                    boolean a = (b & (1 << i)) == 0;
                    if (a) {
                        count++;
                    } else {
                        break;   // Reset the count if we encounter a non-zero number
                    }
                }
                /* Add the count of MSB 0s to the prefix length */
                prefixLength += count;
                /* Break here since we've now covered the MSB 0s */
                break;
            }
        }
        return prefixLength;
    }

    public int getDistance(NodeId to) {
        return ID_LENGTH - this.xor(to).getFirstSetBitIndex();
    }

    public String hexRepresentation() {
        /* Returns the hex format of this NodeId */
        BigInteger bi = new BigInteger(1, this.keyBytes);
        return String.format("%0" + (this.keyBytes.length << 1) + "X", bi);
    }

    @Override
    public String toString() {
        return this.hexRepresentation();
    }
}
