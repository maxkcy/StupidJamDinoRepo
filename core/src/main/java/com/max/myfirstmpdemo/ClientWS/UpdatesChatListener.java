package com.max.myfirstmpdemo.ClientWS;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketHandler;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketException;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;
import com.max.myfirstmpdemo.Packets.ChatPacket;
import com.max.myfirstmpdemo.Packets.UserNameArrayPacket;

import java.util.ArrayList;

public class UpdatesChatListener {
    private MyFirstMpDemoMain game;

    public UpdatesChatListener(MyFirstMpDemoMain game) {
        this.game = game;
    }

    public WebSocketHandler getListener(){
        WebSocketHandler webSocketHandler = new WebSocketHandler(){

            @Override
            public void registerHandler(Class<?> packetClass, Handler<?> handler) {
                super.registerHandler(packetClass, handler);
            }

            @Override
            public void setFailIfNoHandler(boolean failIfNoHandler) {
                super.setFailIfNoHandler(failIfNoHandler);
            }

            @Override
            public boolean onOpen(WebSocket webSocket) {
                return super.onOpen(webSocket);
            }

            @Override
            public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
                return super.onClose(webSocket, code, reason);
            }

            @Override
            public boolean onMessage(WebSocket webSocket, String packet) {
                return super.onMessage(webSocket, packet);
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                return super.onMessage(webSocket, packet);
            }

            @Override
            protected boolean onMessage(WebSocket webSocket, Object packet) throws WebSocketException {
                return super.onMessage(webSocket, packet);
            }

            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                return super.onError(webSocket, error);
            }
        };

        webSocketHandler.registerHandler(UserNameArrayPacket.class, new WebSocketHandler.Handler<UserNameArrayPacket>() {
            @Override
            public boolean handle(final WebSocket webSocket, final UserNameArrayPacket userNameArrayPacket) {
                game.mpHomeScreen.list.clearItems();
                game.mpHomeScreen.list.setItems(userNameArrayPacket.userNamesArray);
                game.mpHomeScreen.numofconnected = game.mpHomeScreen.list.getItems().size;
                game.mpHomeScreen.connectedPlayersLabel.setText("Connected Players: " + game.mpHomeScreen.numofconnected);
                return true;
            }
        });

        webSocketHandler.registerHandler(ChatPacket.class, new WebSocketHandler.Handler<ChatPacket>() {
            @Override
            public boolean handle(final WebSocket webSocket,final ChatPacket chatPacket) {
                if(game.roomScreen.redPlayers.containsKey(chatPacket.getPlayerID())){
                    Gdx.app.log(this.toString(), "CHATPACKET.PlayerID has matched w/ a RedPlayer in redPlayers Array");
                    game.roomScreen.redPlayers.get(chatPacket.getPlayerID()).chat.setChat(chatPacket.getChat());
                    game.roomScreen.redPlayers.get(chatPacket.getPlayerID()).chatTimer = 0;
                }else if(game.roomScreen.bluePlayers.containsKey(chatPacket.getPlayerID())){
                    Gdx.app.log(this.toString(), "CHATPACKET.PlayerID has matched w/ a BluePlayer in bluePlayers Array");
                    game.roomScreen.bluePlayers.get(chatPacket.getPlayerID()).chat.setChat(chatPacket.getChat());
                    game.roomScreen.bluePlayers.get(chatPacket.getPlayerID()).chatTimer = 0;
                }else{ Gdx.app.log(this.toString(), "CHATPACKET... DID NOT MATCH ANY PLAYER"); }
                return true;
            }
        });
        return webSocketHandler;
    }
}
