package com.max.myfirstmpdemo.headless;

import com.dongbat.jbump.Item;
import com.max.myfirstmpdemo.headless.Entities.Entity;
import com.max.myfirstmpdemo.headless.GameRoom;

import io.vertx.core.http.ServerWebSocket;

public class ClientID {

    private GameRoom clientGameRoom;
    private Item<Entity> clientPlayerItem;
    private ServerWebSocket client;

    public String playerID;

    public ClientID(ServerWebSocket client) {
        this.client = client;
        playerID = client.toString();
    }

   public enum Team{
        RED,
        BLUE;
    }

    public Team team;

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public String userName;

    public String getUserName() {
        return userName;
    }

    public ClientID setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public GameRoom getClientGameRoom() {
        return clientGameRoom;
    }

    public void setClientGameRoom(GameRoom clientGameRoom) {
        this.clientGameRoom = clientGameRoom;
    }

    public Item<Entity> getClientPlayerItem() {
        return clientPlayerItem;
    }

    public void setClientPlayerItem(Item<Entity> clientPlayerItem) {
        this.clientPlayerItem = clientPlayerItem;
    }

}
