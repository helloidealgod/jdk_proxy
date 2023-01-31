package com.example.kademlia.node;


public class NodeIdDto {
    private int distance;
    private Node node;

    public NodeIdDto(int distance, Node node) {
        this.distance = distance;
        this.node = node;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
