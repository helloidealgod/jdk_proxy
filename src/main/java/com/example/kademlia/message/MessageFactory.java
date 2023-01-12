package com.example.kademlia.message;

import com.alibaba.fastjson.JSON;

import java.io.DataInputStream;
import java.io.IOException;

public class MessageFactory {
    public Message createMessage(byte code, DataInputStream in) throws IOException {
        switch (code) {
            case 0x01:
                return new PingMessage(in);
            case 0x02:
                return new PongMessage(in);
            default:
                return null;
        }
    }

    public Message createMessage(byte code, String json) throws IOException {
        switch (code) {
            case 0x01:
                return JSON.parseObject(json, PingMessage.class);
            case 0x02:
                return JSON.parseObject(json, PongMessage.class);
            default:
                return null;
        }
    }
}
