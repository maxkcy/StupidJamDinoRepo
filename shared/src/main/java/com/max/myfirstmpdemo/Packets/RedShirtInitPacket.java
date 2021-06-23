package com.max.myfirstmpdemo.Packets;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;


public class RedShirtInitPacket implements Transferable<RedShirtInitPacket> {

    public String IDKey;
    public String userName;

    public RedShirtInitPacket() {
    }

    public RedShirtInitPacket(String IDKey, String userName) {
        this.IDKey = IDKey;
        this.userName = userName;
    }


    public void setIDKey(String IDKey) {
        this.IDKey = IDKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(IDKey);
        serializer.serializeString(userName);
    }

    @Override
    public RedShirtInitPacket deserialize(Deserializer deserializer) throws SerializationException {
        return new RedShirtInitPacket(deserializer.deserializeString(), deserializer.deserializeString());
    }
}
