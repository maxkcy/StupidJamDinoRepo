package com.max.myfirstmpdemo.Packets;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class ChatPacket implements Transferable<ChatPacket> {
    String chat;
    String playerID;

    public ChatPacket() {
    }

    public ChatPacket(String chat, String playerID) {
        this.chat = chat;
        this.playerID = playerID;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(chat);
        serializer.serializeString(playerID);
    }

    @Override
    public ChatPacket deserialize(Deserializer deserializer) throws SerializationException {
        return new ChatPacket(deserializer.deserializeString(), deserializer.deserializeString());
    }
}
