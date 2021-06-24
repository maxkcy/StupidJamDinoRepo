package com.max.myfirstmpdemo.GameAssetsAndStuff;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.max.myfirstmpdemo.LoadingPathsAndScreen.SkinPaths;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

public class Chat implements Disposable {
    public String chatmessage;
    Skin skin;
    private BitmapFont font;
    MyFirstMpDemoMain game;
    private GlyphLayout glyphLayout;

    public Chat(MyFirstMpDemoMain game) {
        this.game = game;
        chatmessage = "";
        skin = game.getAssetManager().get(SkinPaths.DINOSKIN);
        font = skin.getFont("varela");
        font.setColor(1f,1f,1f,.35f);
        glyphLayout = new GlyphLayout();
    }

    public String getChat() {
        return chatmessage;
    }

    public void setChat(String chatmessage) {
        this.chatmessage = chatmessage;
    }

    float posX, posY;
    public void setPos(float posX, float posY) {
        this.posX = posX - font.getRegion().getRegionWidth()/2f + 13;
        this.posY = posY + 150;
    }


    public void update(float posX, float posY){
        glyphLayout.setText(font, chatmessage);
        font.getRegion().setRegion(font.getRegion(), 0,0,(int)glyphLayout.width, 50);
        setPos(posX, posY);
        font.draw(game.getBatch(), chatmessage, this.posX, this.posY);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
