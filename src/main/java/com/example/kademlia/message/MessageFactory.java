package com.example.kademlia.message;

import com.alibaba.fastjson.JSON;
import com.example.kademlia.message.impl.*;

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
            case PingMessage.CODE:
                return JSON.parseObject(json, PingMessage.class);
            case PongMessage.CODE:
                return JSON.parseObject(json, PongMessage.class);
            case StoreMessage.CODE:
                return JSON.parseObject(json, StoreMessage.class);
            case StoreReplyMessage.CODE:
                return JSON.parseObject(json, StoreReplyMessage.class);
            case FindNodeMessage.CODE:
                return JSON.parseObject(json, FindNodeMessage.class);
            case FindNodeReplyMessage.CODE:
                return JSON.parseObject(json, FindNodeReplyMessage.class);
            case FindValueMessage.CODE:
                return JSON.parseObject(json, FindValueMessage.class);
            case FindValueReplyMessage.CODE:
                return JSON.parseObject(json, FindValueReplyMessage.class);
            default:
                return null;
        }
    }
}
