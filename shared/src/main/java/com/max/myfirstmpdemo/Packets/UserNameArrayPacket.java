package com.max.myfirstmpdemo.Packets;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class UserNameArrayPacket implements Transferable<UserNameArrayPacket> {
    public String[] userNamesArray;

    public UserNameArrayPacket() {
    }

    public UserNameArrayPacket(String[] userNamesArray) {
        this.userNamesArray = userNamesArray;
    }

    public String[] getUserNamesArray() {
        return userNamesArray;
    }

    public void setUserNamesArray(String[] userNamesArray) {
        this.userNamesArray = userNamesArray;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeStringArray(userNamesArray);
    }

    @Override
    public UserNameArrayPacket deserialize(Deserializer deserializer) throws SerializationException {
        return new UserNameArrayPacket(deserializer.deserializeStringArray());
    }
}
