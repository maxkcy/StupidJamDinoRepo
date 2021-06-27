package com.max.myfirstmpdemo.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.czyzby.websocket.GwtWebSockets;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.max.myfirstmpdemo.MyFirstMpDemoMain;

import de.golfgl.gdxgamesvcs.GpgsClient;
import de.golfgl.gdxgamesvcs.IGameServiceClient;
import de.golfgl.gdxgamesvcs.IGameServiceIdMapper;
import de.golfgl.gdxgamesvcs.IGameServiceListener;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
	GwtApplicationConfiguration cfg;
	private static final int PADDING = 100;


	@Override
	public GwtApplicationConfiguration getConfig() {
		Window.enableScrolling(false);
		Window.setMargin("0");
		Window.addResizeHandler(new ResizeListener());

		// Resizable application, uses available space in browser
		//return new GwtApplicationConfiguration(true);
		// Fixed size application:

		return new GwtApplicationConfiguration(Window.getClientWidth() - PADDING, Window.getClientHeight() /*- PADDING*/);
		//int w = Window.getClientWidth() - PADDING;
		//int h = Window.getClientHeight() - PADDING;
		//cfg = new GwtApplicationConfiguration(w, h);
		//return cfg;
	}

	class ResizeListener implements ResizeHandler {
		@Override
		public void onResize(ResizeEvent event) {
			if (Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
			} else {
				int width = event.getWidth() - PADDING;
				int height = event.getHeight() /*- PADDING*/;
				getRootPanel().setWidth("" + width + "px");
				getRootPanel().setHeight("" + height + "px");
				getApplicationListener().resize(width, height);
				Gdx.graphics.setWindowedMode(width, height);
			}
		}
	}

	@Override
	public ApplicationListener createApplicationListener() {
		GwtWebSockets.initiate();

		MyFirstMpDemoMain game = new MyFirstMpDemoMain();

		//game.initGWTgpgs();

		game.gsvlistener = new IGameServiceListener() {
			@Override
			public void gsOnSessionActive() {
				Gdx.app.log(this.toString(), "GPS connected/active");

				//game.loginScreen.userName.setText(game.gsClient.getPlayerDisplayName());
			}

			@Override
			public void gsOnSessionInactive() {
				Gdx.app.log(this.toString(), "GPS inactive");
			}

			@Override
			public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {
				Gdx.app.log(this.toString(), "error: " + et + "Message: " + msg + "\nthrowable: " + t);
				//Gdx.app.log(this.toString(), "is session logged in?: " );//+ game.gsClient.logIn());
			}
		};




		// for getting callbacks from the client
		if (game.gsClient != null) {
			game.gsClient.setListener(game.gsvlistener);
			game.gsClient.resumeSession();

			if (game.gsClient.isConnectionPending() == true) {
				boolean waitingtoconnect = true;

				while (waitingtoconnect) {
					if (game.gsClient.isConnectionPending() == false) {
						waitingtoconnect = false;
					}
				};
			}
			//Gdx.app.log(this.toString(), "is Session Active? ");
			if (game.gsClient.isSessionActive() == false) {
				//game.gsClient.logOff();
				//Gdx.app.log(this.toString(), "GS_ERROR: Cannot sign in: No credentials or session id given.");
				//game.gsClient.logIn();
			}

			//Gdx.app.log(this.toString(), "player display name: ");// + game.gsClient.getPlayerDisplayName());
			//Gdx.app.log(this.toString(), "Game Service Id: ");// + game.gsClient.getGameServiceId());
			//Gdx.app.log(this.toString(), "is feature supported: ");// + game.gsClient.isFeatureSupported(IGameServiceClient.GameServiceFeature.ShowAchievementsUI));
			//game.loginScreen.userName.setText(game.gsClient.getPlayerDisplayName());
		}
		if(game.gsClient == null) {
			game.gsClient = new GpgsClient().initialize("5060513955-id751dk44kkie320kvl94fk81johekt5.apps.googleusercontent.com", true);
			game.gsClient.setListener(game.gsvlistener);

			if (game.gsClient.isConnectionPending() == true) {
				boolean waitingtoconnect = true;

				while (waitingtoconnect) {
					if (game.gsClient.isConnectionPending() == false) {
						waitingtoconnect = false;
					}
				};
			}

			if (game.gsClient.isSessionActive() == false) {

			}

		}

		return game;
	}
}