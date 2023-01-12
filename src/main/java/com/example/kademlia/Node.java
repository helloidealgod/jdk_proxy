package com.example.kademlia;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Node {
    private NodeId nodeId;
    private InetAddress inetAddress;
    private int port;

    public Node() {
    }

    public Node(NodeId nid, InetAddress ip, int port) {
        this.nodeId = nid;
        this.inetAddress = ip;
        this.port = port;
    }

    public Node(DataInputStream in) throws IOException {
        this.fromStream(in);
    }

    public void setInetAddress(InetAddress addr) {
        this.inetAddress = addr;
    }

    public NodeId getNodeId() {
        return this.nodeId;
    }

    public InetAddress getInetAddress() {
        return this.inetAddress;
    }

    public void setNodeId(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(this.inetAddress, this.port);
    }

    public void toStream(DataOutputStream out) throws IOException {
        /* Add the NodeId to the stream */
        this.nodeId.toStream(out);

        /* Add the Node's IP address to the stream */
        byte[] a = inetAddress.getAddress();
        if (a.length != 4) {
            throw new RuntimeException("Expected InetAddress of 4 bytes, got " + a.length);
        }
        out.write(a);

        /* Add the port to the stream */
        out.writeInt(port);
    }

    public final void fromStream(DataInputStream in) throws IOException {
        /* Load the NodeId */
        this.nodeId = new NodeId(in);

        /* Load the IP Address */
        byte[] ip = new byte[4];
        in.readFully(ip);
        this.inetAddress = InetAddress.getByAddress(ip);

        /* Read in the port */
        this.port = in.readInt();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node n = (Node) o;
            if (n == this) {
                return true;
            }
            return this.getNodeId().equals(n.getNodeId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getNodeId().hashCode();
    }

    @Override
    public String toString() {
        return this.getNodeId().toString();
    }
}
