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
        this.posY = posY + glyphLayout.height + 30;
    }

    String chatMessage2 = "";
    public void update(float posX, float posY){
        if (chatmessage.length() > 10){
            chatMessage2 = "";
            for(int i = 1; i <= chatmessage.length()/10f; i++){
                int subend = i*10;
                chatMessage2 += (chatmessage.substring((i - 1) * 10 , subend) + "\n");
            }
                int remainder = chatmessage.length()%10;
                if(chatmessage.length() - 1 - remainder != chatmessage.length() - 1){
                chatMessage2 += chatmessage.substring(chatmessage.length() - 1 - remainder, chatmessage.length() - 1) + "\n";
                }
//0123456789
        }else{chatMessage2 = chatmessage + "\n";}

        glyphLayout.setText(font, chatMessage2);
        font.getRegion().setRegion(font.getRegion(), 0,0,(int)glyphLayout.width, 50);
        setPos(posX, posY);
        font.draw(game.getBatch(), chatMessage2, this.posX, this.posY);
    }


    @Override
    public void dispose() {
        font.dispose();
    }
}
