package com.max.myfirstmpdemo.LoadingPathsAndScreen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

public class Loader {
    private LoadingPaths loadingPaths;
    private MyFirstMpDemoMain game;

    public Loader(MyFirstMpDemoMain game) {
       this.game = game;
        loadingPaths = new LoadingPaths();
        loadSkinPaths();
        loadSpritePaths();
        loadAtlasPaths();
        loadSoundPaths();
        loadMusicPaths();
        //this.game.getAssetManager().finishLoading();
    }

    public void loadSkinPaths() {
        for (String skinPath : loadingPaths.getSkinPaths()) {
            if (skinPath != null) {
                //System.out.println(game.getAssetManager());
                game.getAssetManager().load(skinPath, Skin.class);

                if (skinPath == SkinPaths.DINOSKIN) {
                    game.getAssetManager().finishLoadingAsset(SkinPaths.DINOSKIN);
                    }
                }
            }
        }

        public void loadSpritePaths(){
            for (String spritePath : loadingPaths.getSpritePaths()){
                if (spritePath != null){
                    game.getAssetManager().load(spritePath, Texture.class);
                }
            }
        }

        public void loadAtlasPaths(){
            for (String atlasPath : loadingPaths.getAnimationAtlasPaths()){
                if (atlasPath != null){
                    game.getAssetManager().load(atlasPath, TextureAtlas.class);
                }
            }
        }

        public void loadSoundPaths(){
            for (String soundPath : loadingPaths.getSoundPaths()){
                if (soundPath != null){
                    game.getAssetManager().load(soundPath, Sound.class);
                }
            }
        }

        public void loadMusicPaths(){
            for (String musicPath : loadingPaths.getMusticPaths()){
                if (musicPath != null){
                    game.getAssetManager().load(musicPath, Music.class);
                }
            }
        }
}


