package com.max.myfirstmpdemo.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Queue;
import com.dongbat.jbump.Item;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import com.max.myfirstmpdemo.Packets.ChatPacket;
import com.max.myfirstmpdemo.Packets.CountDownPacket;
import com.max.myfirstmpdemo.Packets.RoomEnum;
import com.max.myfirstmpdemo.Packets.RoomPacket;
import com.max.myfirstmpdemo.Packets.TouchDownPacket;
import com.max.myfirstmpdemo.headless.Entities.Entity;
import com.max.myfirstmpdemo.headless.Entities.PlayerEntity;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

public class GameRoom extends ScreenAdapter {
    public ServerMain serverMain;
    public Array<ServerWebSocket> playersList;
    public float time;// = 300.0f;
    public boolean isActive;// = true;
    public GameWorld gameWorld;




    //public CountDownPacket countDownPacket = new CountDownPacket(300.0f);
    //for testies and this is demo im going to create a pool to keep pooling fresh in my memory
    Pool<CountDownPacket> countDownPacketPool;// = new Pool<CountDownPacket>() {
    Pool<RoomPacket> roomPacketPool;

    public GameRoom(ServerMain serverMain) {
        this.serverMain = serverMain;
        playersList = new Array<>();
        chatPacketQueue = new ConcurrentLinkedQueue<Transferable>();
        System.out.println("New GameRoom initiated");
    }


    @Override
    public void show() {

        time = 60.0f;
        isActive = true;

        countDownPacketPool = new Pool<CountDownPacket>() {
            @Override
            protected CountDownPacket newObject() {
                return new CountDownPacket();
            }

            @Override
            protected void reset(CountDownPacket object) {
                super.reset(object);
                object.setTime(time);
            }
        };

        roomPacketPool = new Pool<RoomPacket>() {
            @Override
            protected RoomPacket newObject() {
                return new RoomPacket();
            }
        };
        //System.out.println("The GameRoom has players: " + playersList);
        gameWorld = new GameWorld(playersList);
        gameWorld.initGameWorld();
        //last edits you do, send packet to switch to room screen here and change countdown packet logic
    }


    @Override
    public void render(float delta) {
        //super.render(delta);
        if (isActive) {
            gameWorld.update(delta);
            chatPacketHandlingMethod();

            for (ServerWebSocket serverWebSocket :
                    playersList) {
                CountDownPacket countDownPacket = countDownPacketPool.obtain();
                //CountDownPacket countDownPacket = new CountDownPacket();
                countDownPacket.setTime(time);
                //System.out.println(countDownPacket.getTime());
                serverWebSocket.writeFinalBinaryFrame(Buffer.buffer(serverMain.manualSerializer.serialize(countDownPacket)));
                //Gdx.app.log(this.toString(), "CountDownPacket w/ time: " + countDownPacket.getTime() + " sent to: " + serverWebSocket);
                countDownPacketPool.free(countDownPacket);
            }
            //System.out.println("Countdown packets sent");
            //System.out.println(time);
        }

        if (time <= 0.0f || playersList.isEmpty()) {
            if (isActive == true) {
                System.out.println("Game Room has ended");
                isActive = false;
                for (ServerWebSocket serverWebSocket :
                        playersList) {
                    RoomPacket roomPacket = roomPacketPool.obtain();
                    roomPacket.roomEnum = RoomEnum.MPHOMELOBBY;
                    serverWebSocket.writeFinalBinaryFrame(Buffer.buffer(serverMain.manualSerializer.serialize(roomPacket)));
                    roomPacketPool.free(roomPacket);
                }

            }
        }
        time = time - delta;



    }

        @Override
        public void dispose(){
            super.dispose();
            countDownPacketPool.clear();
            roomPacketPool.clear();
        }


    public ConcurrentLinkedQueue<Transferable> chatPacketQueue;
    int cap = 100;
    Transferable element;
        public void chatPacketHandlingMethod(){
            for (int i = 0; i <= cap && (element = chatPacketQueue.poll()) != null; i++){
               if (element instanceof ChatPacket){
                    for(ServerWebSocket client : playersList){
                        client.writeFinalBinaryFrame(Buffer.buffer(ServerMain.manualSerializer.serialize(element)));
                        Gdx.app.log(this.toString(), "ChatPacket with message" + ((ChatPacket) element).getChat()+ "\nfrom: " + ((ChatPacket) element).getPlayerID() + " sent");
                    }
               }
            }

        }
    }

