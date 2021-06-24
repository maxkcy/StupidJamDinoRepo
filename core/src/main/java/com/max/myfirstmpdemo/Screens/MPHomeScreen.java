package com.max.myfirstmpdemo.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.max.myfirstmpdemo.GameAssetsAndStuff.GameAssets;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;
import com.max.myfirstmpdemo.Packets.RoomEnum;
import com.max.myfirstmpdemo.Packets.RoomPacket;

public class MPHomeScreen extends ScreenAdapter {

MyFirstMpDemoMain game;
public GameAssets gameAssets;
public Stage stage;
public  Skin skin;
public TextButton joinGameButton;
public List<String> list;
public static String string = "Pancakes! This is The Multiplayer Lobby";
public Label title;
public int numofconnected = 0;
public Label connectedPlayersLabel;

    public MPHomeScreen(MyFirstMpDemoMain game) {
        this.game = game;
        gameAssets = new GameAssets(game);

    }

    @Override
    public void show() {

        stage = new Stage(new FitViewport(1000,450));
        Gdx.input.setInputProcessor(this.stage);
        skin = gameAssets.getDinoskin();

        Table table = new Table();
        table.setFillParent(true);

        title = new Label(string, skin);
        table.add(title).align(Align.center).expandX().expandY().colspan(2);
        table.row();
        joinGameButton = new TextButton("Join Game!!!", skin);
        table.add(joinGameButton);

        connectedPlayersLabel = new Label("Connected Players: " + numofconnected, skin);
        table.add(connectedPlayersLabel).expandX();

        table.row();
        TextButton backToMenuButton = new TextButton("Back To Menu", skin);
        table.add(backToMenuButton);


        list = new List<>(skin);
        list.setAlignment(Align.center);
        ScrollPane scrollPane = new ScrollPane(list, skin);
        table.add(scrollPane).growY().growX();

        ClickListener clicky = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RoomPacket roomPacket = new RoomPacket(RoomEnum.QUE);
                //roomPacket.roomEnum = RoomEnum.QUE; //<-- redundant but works
                if (game.clientWS.webSocket.isOpen() && joinGameButton.isDisabled() == false) { //{joinGameButtom.removeListener(joinGameButtom.getClickListener());}
                    joinGameButton.setDisabled(true);
                    game.clientWS.webSocket.send(roomPacket);
                    System.out.println("packet sent to server to add you to que...");
                    super.clicked(event, x, y);
                }
            }
        };
        joinGameButton.addListener(clicky);

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.75f, .5f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().apply();
        game.getBatch().setProjectionMatrix(stage.getViewport().getCamera().combined);
        game.getBatch().begin();

        game.getBatch().end();

            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                string = "you pressed the " + Input.Keys.ANY_KEY  + " key  :) ...";
                //WTF is this BS^. well i will just leave it because i dont like to remove code
            }
        stage.draw();
        stage.act();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
    stage.dispose();
    }
}