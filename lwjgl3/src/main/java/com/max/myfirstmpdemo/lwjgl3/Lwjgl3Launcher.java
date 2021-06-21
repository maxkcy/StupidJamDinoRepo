package com.max.myfirstmpdemo.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

import de.golfgl.gdxgamesvcs.GpgsClient;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceListener;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		CommonWebSockets.initiate();
		//line above is super important. add that
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application( new MyFirstMpDemoMain(){

			@Override
			public void create() {



				//Gdx.app.log(this.toString(), "Google Login button clicked");
				//System.out.println(Gdx.files.internal("gpgs-client_secret.json").readString());

				/*if (gsClient == null){
					gsClient = new GpgsClient().initialize("FinalDinoTest",
							Gdx.files.internal("gpgs-client_secret_5.json"), true);
					Gdx.app.log(this.toString(), "Client Initialized");
					System.out.println(Gdx.files.internal("gpgs-client_secret_3.json").readString());//one.. 3 works
				}*/

				gsvlistener = new IGameServiceListener() {
					@Override
					public void gsOnSessionActive() {
						Gdx.app.log(this.toString(), "GPS connected/active");
						loginScreen.userName.setText(gsClient.getPlayerDisplayName());
					}

					@Override
					public void gsOnSessionInactive() {
						Gdx.app.log(this.toString(), "GPS inactive");
					}

					@Override
					public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {
						Gdx.app.log(this.toString(), "error: " + et + "Message: " + msg + "\nthrowable: " + t);
						Gdx.app.log(this.toString(), "is session logged in?: "+ gsClient.logIn());

					}
				};

				// for getting callbacks from the client
				if(gsClient != null) {
					gsClient.setListener(gsvlistener);

					// establish a connection to the game service without error messages or login screens
					gsClient.resumeSession();


					if (gsClient.isConnectionPending() == true) {
						boolean waitingtoconnect = true;
						Gdx.app.log(this.toString(), "is connection pending? " + gsClient.isConnectionPending());
						while (waitingtoconnect) {
							if (gsClient.isConnectionPending() == false) {
								waitingtoconnect = false;
								Gdx.app.log(this.toString(), "is connection pending now? " + gsClient.isConnectionPending());
							}
						}

					}


						Gdx.app.log(this.toString(), "is Session Active? " + gsClient.isSessionActive());


					if (gsClient.isSessionActive() == false) {
						//game.gsClient.logOff();
						Gdx.app.log(this.toString(), "GS_ERROR: Cannot sign in: No credentials or session id given.");
						//game.gsClient.logIn();
					}

					Gdx.app.log(this.toString(), "player display name: " + gsClient.getPlayerDisplayName());
					Gdx.app.log(this.toString(), "Game Service Id: " + gsClient.getGameServiceId());
					Gdx.app.log(this.toString(), "is feature supported: " + gsClient.isFeatureSupported(IGameServiceClient.GameServiceFeature.ShowAchievementsUI));

					loginScreen.userName.setText(gsClient.getPlayerDisplayName());
				}
				super.create();
			}
		}, getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("MyFirstMpDemo");
		configuration.setWindowedMode(640, 480);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}
}