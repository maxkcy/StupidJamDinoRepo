package com.max.myfirstmpdemo.GameAssetsAndStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.AnimationAtlasPaths;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SkinPaths;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SoundPaths;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SpritePaths;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

public class GameAssets implements Disposable {
    MyFirstMpDemoMain game;

    private Skin cleanCrispy;
    private Skin sgx;
    private Skin dinoskin;
    TextureAtlas textureAtlas;
    TextureAtlas asteroidTextureAtlas;
    TextureAtlas asteroidNewAtlas;
    public Sound kick;
    public Sound whistle;
    public Music addingTheSun;
    public Music crowd;



    private Sprite footballPitchBackground;

    public GameAssets(MyFirstMpDemoMain game) {
        this.game = game;


        if(game.getAssetManager().isLoaded(SkinPaths.SKIN_1_CLEANCRISPY)){
            cleanCrispy = game.getAssetManager().get(SkinPaths.SKIN_1_CLEANCRISPY);
        } else {game.getAssetManager().finishLoadingAsset(SkinPaths.SKIN_1_CLEANCRISPY);}

        if(game.getAssetManager().isLoaded(SkinPaths.DINOSKIN)){
            dinoskin = game.getAssetManager().get(SkinPaths.DINOSKIN);
        } else {game.getAssetManager().finishLoadingAsset(SkinPaths.DINOSKIN);}


        if(game.getAssetManager().isLoaded(SkinPaths.Skin_2_SGX)){
        sgx = game.getAssetManager().get(SkinPaths.Skin_2_SGX);
        } else {game.getAssetManager().finishLoadingAsset(SkinPaths.Skin_2_SGX);}

        game.getAssetManager().finishLoading();
        footballPitchBackground = new Sprite((Texture) game.getAssetManager().get(SpritePaths.FOOTBALL_PITCH_BACKGROUND));
        textureAtlas = game.getAssetManager().get(AnimationAtlasPaths.PLAYERS_ATLAS, TextureAtlas.class);
        asteroidTextureAtlas = game.getAssetManager().get(AnimationAtlasPaths.ASTEROID_ATLAS, TextureAtlas.class);
        asteroidNewAtlas = game.getAssetManager().get(AnimationAtlasPaths.ASTEROID_NEW, TextureAtlas.class);

        kick = game.getAssetManager().get(SoundPaths.KICK);
        whistle = game.getAssetManager().get(SoundPaths.WHISTLE);
        addingTheSun = game.getAssetManager().get(SoundPaths.ADDING_THE_SUN);
        crowd = game.getAssetManager().get(SoundPaths.CROWD);

    }
    public Skin getCleanCrispy() { return cleanCrispy; }
    public Skin getSgx() { return sgx; }
    public Sprite getFootballPitchBackground() {
        return footballPitchBackground;
    }
    public Skin getDinoskin() {return dinoskin;}


    @Override
    public void dispose() {

    }
}
