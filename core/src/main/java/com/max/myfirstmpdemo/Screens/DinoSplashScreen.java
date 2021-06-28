package com.max.myfirstmpdemo.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.max.myfirstmpdemo.GameAssetsAndStuff.GameAssets;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.Loader;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

public class DinoSplashScreen extends ScreenAdapter {
    public DinoSplashScreen() {
    }

    private Stage stage;
    public GameAssets gameAssets;
    Skin skin;
    MyFirstMpDemoMain game;
    ProgressBar progressBar;
    Loader loader;

    public DinoSplashScreen(MyFirstMpDemoMain game) {this.game = game;}

    @Override
    public void show() {
        loader = new Loader(game);
        gameAssets = new GameAssets(game);
        stage = new Stage(new FitViewport(500,300));
        skin = gameAssets.getDinoskin();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Image image = new Image(skin, "Splashimage");
        table.add(image).growX();

        table.row();
        progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        progressBar.setAnimateDuration(5.0f);
        progressBar.setAnimateInterpolation(Interpolation.linear);
        table.add(progressBar).padTop(-50.0f).growX();
        stage.addActor(table);
    }
    float value = 0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        value += Gdx.graphics.getDeltaTime();
        stage.draw();
        stage.act();
        progressBar.setValue(game.getAssetManager().getProgress() * progressBar.getMaxValue());
        System.out.println(game.getAssetManager().getProgress() * progressBar.getMaxValue());
        //game.getAssetManager().update();
        if (game.getAssetManager().isFinished() && progressBar.getValue() == progressBar.getMaxValue() && value > 6f) {
            game.loginScreen = new LoginScreen(game);
            game.mpHomeScreen = new MPHomeScreen(game);
            game.roomScreen = new RoomScreen(game);
            //game.mpHomeScreen.show(); // this doesnt do anything. doesnt fix the problem
            if (value > 6f){
            Gdx.app.postRunnable(() -> game.setScreen(game.loginScreen));}
        }
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
