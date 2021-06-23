package com.max.myfirstmpdemo.GameAssetsAndStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SkinPaths;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

public class Handle implements Disposable {
    public String userName;
    Skin skin;
    BitmapFont font;
    MyFirstMpDemoMain game;

    public Handle(MyFirstMpDemoMain game, String userName) {
        this.game = game;
        this.userName = userName;
        skin = game.getAssetManager().get(SkinPaths.DINOSKIN);
        font = skin.getFont("varela");
        font.setColor(1f,1f,1f,.35f);
        GlyphLayout glyphLayout = new GlyphLayout(font, userName);
        font.getRegion().setRegion(font.getRegion(), 0,0,(int)glyphLayout.width, 50);
    }

    float posX, posY;

    public void setPos(float posX, float posY) {
        this.posX = posX - font.getRegion().getRegionWidth()/2f + 13;
        this.posY = posY + 50;
    }


    public void update(float posX, float posY){
        setPos(posX, posY);
        font.draw(game.getBatch(), userName, this.posX, this.posY);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
