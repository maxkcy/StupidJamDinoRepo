package com.max.myfirstmpdemo.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.max.myfirstmpdemo.GameAssetsAndStuff.GameAssets;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

import de.golfgl.gdxgamesvcs.GpgsClient;
import de.golfgl.gdxgamesvcs.IGameServiceClient;

public class LoginScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private MyFirstMpDemoMain game;
    private GameAssets gameAssets;

    public LoginScreen(MyFirstMpDemoMain game) {
        this.game = game;
    }
    public TextField userName;
    private boolean continueWithGoogle = true;

    @Override
    public void show() {
        gameAssets = new GameAssets(game);
        stage = new Stage(new FitViewport(700,700));
        skin = gameAssets.getDinoskin();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("It's Time To LOGIN!", skin);
        table.add(label);

        table.row();
        Table table1 = new Table();

        label = new Label("What should other dinos call you?", skin);
        table1.add(label).expandX();

        userName = new TextField("Handle", skin);
        table1.add(userName).expandX().align(Align.center);
        userName.setAlignment(Align.center);
        table.add(table1);

        table.row();
        CheckBox checkBox = new CheckBox("Save handle for next time?", skin);
        table.add(checkBox);

        table.row();
        TextButton textButton = new TextButton("Click Here To Begin", skin);
        textButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("HI u clicked login button :)");
                game.clientWS.init();
                //Gdx.app.postRunnable(()-> game.setScreen(game.mpHomeScreen));
                System.out.println("new Screen");
            }
        });
        table.add(textButton);

        table.row();

       TextButton GoogletextButton = new TextButton("Continue with Google?", skin, "google");
        GoogletextButton.addListener(new ClickListener(){
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            if (continueWithGoogle) {
                                                if (game.gsClient == null) {
                                                    game.gsClient = new GpgsClient().initialize("FinalDinoTest",
                                                            Gdx.files.internal("gpgs-client_secret_5.json"), true);
                                                    Gdx.app.log(this.toString(), "Client Initialized");
                                                    System.out.println(Gdx.files.internal("gpgs-client_secret_3.json").readString());//one.. 3 works
                                                } else {
                                                    game.gsClient.resumeSession();
                                                }
                                                game.gsClient.setListener(game.gsvlistener);
                                                if (game.gsClient.isConnectionPending() == true) {
                                                    boolean waitingtoconnect = true;
                                                    Gdx.app.log(this.toString(), "is connection pending? " + game.gsClient.isConnectionPending());
                                                    while (waitingtoconnect) {
                                                        if (game.gsClient.isConnectionPending() == false) {
                                                            waitingtoconnect = false;
                                                            Gdx.app.log(this.toString(), "is connection pending now? " + game.gsClient.isConnectionPending());
                                                        }
                                                    }

                                                }


                                                Gdx.app.log(this.toString(), "is Session Active? " + game.gsClient.isSessionActive());


                                                if (game.gsClient.isSessionActive() == false) {
                                                    //game.gsClient.logOff();
                                                    Gdx.app.log(this.toString(), "GS_ERROR: Cannot sign in: No credentials or session id given.");
                                                    //game.gsClient.logIn();
                                                } else {

                                                    Gdx.app.log(this.toString(), "player display name: " + game.gsClient.getPlayerDisplayName());
                                                    Gdx.app.log(this.toString(), "Game Service Id: " + game.gsClient.getGameServiceId());
                                                    Gdx.app.log(this.toString(), "is feature supported: " + game.gsClient.isFeatureSupported(IGameServiceClient.GameServiceFeature.ShowAchievementsUI));


                                                    Gdx.app.log(this.toString(), "Google Login button clicked");
                                                    Gdx.app.log(this.toString(), "is session logged in? " + game.gsClient.logIn());
                                                    game.gsClient.logIn();
                                                    Gdx.app.log(this.toString(), "is session logged in now? " + game.gsClient.logIn());
                                                    Gdx.app.log(this.toString(), "" + game.gsClient.getPlayerDisplayName());
                                                    Gdx.app.log(this.toString(), "" + game.gsClient.getGameServiceId());
                                                    userName.setText(game.gsClient.getPlayerDisplayName());

                                                    GoogletextButton.setText("Log Out?");
                                                    continueWithGoogle = false;
                                                }
                                            }else{
                                                GoogletextButton.setText("Continue With Google");
                                                continueWithGoogle = true;
                                                game.gsClient.logOff();
                                                userName.setText("Handle");
                                            }
                                        }

                                    }
        );
        table.add(GoogletextButton);

        table.row();
        textButton = new TextButton("How To Play ", skin);
        table.add(textButton);
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
