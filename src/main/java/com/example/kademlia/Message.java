package com.example.kademlia;

public class Message {
    private String operateType;
    private String fromIp;
    private int fromPort;
    private String toIp;
    private int toPort;
    private Node node;
    private String data;

    public Message() {

    }

    public Message(String operateType, String fromIp, int fromPort, String toIp, int toPort, String data) {
        this.operateType = operateType;
        this.fromIp = fromIp;
        this.fromPort = fromPort;
        this.toIp = toIp;
        this.toPort = toPort;
        this.data = data;
    }

    public Message(String operateType, Node node, String toIp, int toPort, String data) {
        this.operateType = operateType;
        this.fromIp = node.getInetAddress().getHostAddress();
        this.fromPort = node.getPort();
        this.toIp = toIp;
        this.toPort = toPort;
        this.data = data;
        this.node = node;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public String getToIp() {
        return toIp;
    }

    public void setToIp(String toIp) {
        this.toIp = toIp;
    }

    public int getToPort() {
        return toPort;
    }

    public void setToPort(int toPort) {
        this.toPort = toPort;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
