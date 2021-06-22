package com.max.myfirstmpdemo.Packets;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class UserNamePacket implements Transferable<UserNamePacket> {
    public String userName;

    public UserNamePacket() {
    }

    public UserNamePacket(String userName) {
        this.userName = userName;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(userName);
    }

    @Override
    public UserNamePacket deserialize(Deserializer deserializer) throws SerializationException {
        return new UserNamePacket(deserializer.deserializeString());
    }
}
