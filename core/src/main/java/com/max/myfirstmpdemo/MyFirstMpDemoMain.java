package com.max.myfirstmpdemo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.czyzby.websocket.WebSockets;
import com.max.myfirstmpdemo.ClientWS.ClientWS;
import com.max.myfirstmpdemo.Screens.DinoSplashScreen;
import com.max.myfirstmpdemo.Screens.LoginScreen;
import com.max.myfirstmpdemo.Screens.MPHomeScreen;
import com.max.myfirstmpdemo.Screens.RoomScreen;
import com.max.myfirstmpdemo.Screens.SplashScreen;

import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MyFirstMpDemoMain extends Game  {


	private SpriteBatch batch;
	private AssetManager assetManager;
	public SplashScreen splashScreen;
	public DinoSplashScreen dinoSplashScreen;
	public ClientWS clientWS;
	public LoginScreen loginScreen;
	public MPHomeScreen mpHomeScreen;
	public RoomScreen roomScreen;

	public IGameServiceClient gsClient;
	public IGameServiceListener gsvlistener;

	public MyFirstMpDemoMain(SpriteBatch batch, AssetManager assetManager) {
		this.batch = batch;
		this.assetManager = assetManager;
	}

	public MyFirstMpDemoMain() {
	}

	@Override
	public void create() {


		batch = new SpriteBatch();
		assetManager = new AssetManager();
		splashScreen = new SplashScreen(this);
		dinoSplashScreen = new DinoSplashScreen(this);

		clientWS = new ClientWS(this);

		Gdx.app.postRunnable(()-> setScreen(dinoSplashScreen));
		Gdx.input.setCatchKey(Input.Keys.SPACE, true);//disables gwt, android keys for scrolling

	}

	@Override
	public void render() {
		//Gdx.gl.glClearColor(.8f, 1, .9f, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //works across all screens because this render is called before all screens aka super.render() which calls render from next screen
		}
		/*if(Gdx.input.isKeyPressed(Input.Keys.N)){
			Gdx.graphics.setWindowedMode(Gdx.graphics.)
		}*/ //needs work on later because of full screen bug, unable to press escape, and display on tv monitor gets stuck
		super.render();
	}

	@Override
	public void dispose() {
		System.out.println("dispose called");
		batch.dispose();
		assetManager.dispose();
		WebSockets.closeGracefully(clientWS.webSocket);
		try{clientWS.webSocket.close();
			System.out.println("Yay it works");
		}catch (Exception exception){
			System.out.println(exception);
			System.out.println("NOY it doesnt work :(");
		}
		splashScreen.dispose();
		loginScreen.dispose();
		mpHomeScreen.dispose();
		//clientWS.dispose();
		gsClient.logOff();
		super.dispose();

	}

	@Override
	public void pause() {
		super.pause();
		gsClient.pauseSession();
	}

	@Override
	public void resume() {
		super.resume();
		gsClient.resumeSession();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}


}