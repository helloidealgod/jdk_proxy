package com.example.kademlia;

import com.example.kademlia.node.Node;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private List<Node> nodeList;

    public Bucket() {
        nodeList = new ArrayList<>();
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
