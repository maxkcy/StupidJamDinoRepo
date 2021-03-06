package com.max.myfirstmpdemo.LoadingPathsAndScreen;

import java.util.ArrayList;
import java.util.List;

public class LoadingPaths {

    public LoadingPaths() {
    }

    public List<String> getSkinPaths(){
        List<String> list = new ArrayList<>();
        list.add(SkinPaths.DINOSKIN);
        list.add(SkinPaths.SKIN_1_CLEANCRISPY);
        list.add(SkinPaths.Skin_2_SGX);
        return list;
    }

    public List<String> getSpritePaths(){
        List<String> list = new ArrayList<>();
        list.add(SpritePaths.FOOTBALL_PITCH_BACKGROUND);
        return list;
    }

    public List<String> getAnimationAtlasPaths(){
        List<String> list = new ArrayList<>();
        list.add(AnimationAtlasPaths.PLAYERS_ATLAS);
        list.add(AnimationAtlasPaths.ASTEROID_ATLAS);
        list.add(AnimationAtlasPaths.ASTEROID_NEW);
        return list;
    }

    public List<String> getSoundPaths(){
        List<String> list = new ArrayList<>();
        list.add(SoundPaths.KICK);
        list.add(SoundPaths.WHISTLE);
        return list;
    }

    public List<String> getMusticPaths(){
        List<String> list = new ArrayList<>();
        list.add(SoundPaths.ADDING_THE_SUN);
        list.add(SoundPaths.CROWD);
        return list;
    }
}
