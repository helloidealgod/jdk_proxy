package com.example.kademlia.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Message {
    public abstract byte code();

    protected abstract void fromStream(DataInputStream in) throws IOException;

    protected abstract void toStream(DataOutputStream out) throws IOException;
}
