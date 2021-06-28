package com.max.myfirstmpdemo.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.max.myfirstmpdemo.GameAssetsAndStuff.AsteroidBall;
import com.max.myfirstmpdemo.GameAssetsAndStuff.BluePlayer;
import com.max.myfirstmpdemo.GameAssetsAndStuff.GameAssets;
import com.max.myfirstmpdemo.GameAssetsAndStuff.RedPlayer;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SoundPaths;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;
import com.max.myfirstmpdemo.Packets.ChatPacket;
import com.max.myfirstmpdemo.Packets.TouchUpPacket;
import com.max.myfirstmpdemo.Packets.TouchDownPacket;

public class RoomScreen extends ScreenAdapter {

    MyFirstMpDemoMain game;
OrthographicCamera cam;
FitViewport viewport;
public BitmapFont font;
public Sprite footBallPitchBackround;
public static String message;
public GameAssets gameAssets;
public ArrayMap<String, RedPlayer> redPlayers;
public ArrayMap<String, BluePlayer> bluePlayers;
Vector3 touch;
TouchDownPacket touchDownPacket;
TouchUpPacket touchUpPacket;
public AsteroidBall asteroidBall;
public Hud hud;
public Stage stage;
public TextField chatTextField;
public Music crowdMusic;

    public RoomScreen(MyFirstMpDemoMain game) {
        this.game = game;
        gameAssets = new GameAssets(game);
        redPlayers = new ArrayMap<>();
        bluePlayers = new ArrayMap<>();
        asteroidBall = new AsteroidBall(game);
        hud = new Hud(game);
        crowdMusic = game.dinoSplashScreen.gameAssets.crowd;
        crowdMusic.setLooping(true);

    }
    String chatMessage;
    @Override
    public void show() {
        super.show();
        touch = new Vector3();
        touchDownPacket = new TouchDownPacket();
        touchUpPacket = new TouchUpPacket();
        cam = new OrthographicCamera();
        viewport = new FitViewport(600f, 400f + 100f, cam);
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2 - 100f, 0);// put this line before assigning viewport to generate error
        footBallPitchBackround = gameAssets.getFootballPitchBackground();
        footBallPitchBackround.setBounds(0,0, 600f, 400f);
        font = gameAssets.getSgx().getFont("font");
        message = "welcome to the game room";
        hud.init();
        stage = new Stage(viewport);
        stage.getCamera().position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2 - 100f, 0);
        Gdx.input.setInputProcessor(this.stage);
        chatTextField = new TextField("", gameAssets.getDinoskin());
        chatTextField.setBounds(0,-100, viewport.getWorldWidth() - viewport.getWorldWidth()/5, 100f);
        chatTextField.setAlignment(Align.left);
        chatTextField.setMaxLength(50);
        stage.addActor(chatTextField);

        TextButton sendButton = new TextButton("SEND", gameAssets.getDinoskin());
        sendButton.setBounds(viewport.getWorldWidth() - viewport.getWorldWidth()/5, -100, viewport.getWorldWidth()/5, 100f);
        sendButton.addListener(new ClickListener(){

            ChatPacket chatPacket = new ChatPacket();
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chatMessage = chatTextField.getText();
                chatTextField.setText("");
               /* stupid testing code for (RedPlayer rp: redPlayers.values()){
                    rp.chat.setChat(chatMessage);

                }*/
                chatPacket.setChat(chatMessage);
                game.clientWS.webSocket.send(chatPacket);
                Gdx.app.log(this.toString(), "Chat Packet sent with message: " + chatMessage);
            }
        });
        stage.addActor(sendButton);
    }
    boolean click;
    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(.2f, .88f, .2f, .65f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        cam.update();

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        footBallPitchBackround.draw(game.getBatch());

        font.draw(game.getBatch(), message, 250, 385);

        for (RedPlayer redPlayer : redPlayers.values()) {
            redPlayer.update(delta);
        }
        for (BluePlayer bluePlayer : bluePlayers.values()){
            bluePlayer.update(delta);
        }

        asteroidBall.update(delta);
        game.getBatch().end();

        if (Gdx.input.isTouched()){
            touch = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            touchDownPacket.setX(touch.x);
            touchDownPacket.setY(touch.y);
            game.clientWS.webSocket.send(touchDownPacket);
            Gdx.app.log(this.toString(), "TouchDownPacket sent with coords " + touch.x + " " + touch.y);
            click = true;
        }
        if(!Gdx.input.isTouched() && click == true){
            touchUpPacket.setX(touch.x);
            touchUpPacket.setY(touch.y);
            game.clientWS.webSocket.send(touchUpPacket);
            click = false;
            Gdx.app.log(this.toString(), "TouchUpPacket sent");
        }

        hud.update();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        hud.resize(width, height);
        stage.getViewport().update(width, height);
    }



    @Override
    public void dispose() {
        super.dispose();
        redPlayers.clear();
        bluePlayers.clear();
        stage.dispose();
    }
}
