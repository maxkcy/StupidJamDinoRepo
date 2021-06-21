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
import de.golfgl.gdxgamesvcs.IGameServiceListener;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
	GwtApplicationConfiguration cfg;
	private static final int PADDING = 100;

	@Override
		public GwtApplicationConfiguration getConfig () {
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
		public ApplicationListener createApplicationListener () {
			GwtWebSockets.initiate();


			return new MyFirstMpDemoMain(){
				@Override
				public void create() {
					if (gsClient == null){
						gsClient = new GpgsClient().initialize("656291588145-p9k73044aoojidtaetaup9qnlh19cj8m.apps.googleusercontent.com", true);
						Gdx.app.log(this.toString(), "Client Initialized");
					}

					gsvlistener = new IGameServiceListener() {
						@Override
						public void gsOnSessionActive() {
							Gdx.app.log(this.toString(), "GPS connected/active");
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
					gsClient.setListener(gsvlistener);

					// establish a connection to the game service without error messages or login screens
					gsClient.resumeSession();


					if(gsClient.isConnectionPending() == true){
						boolean waitingtoconnect = true;
						Gdx.app.log(this.toString(), "is connection pending? " + gsClient.isConnectionPending());
						while(waitingtoconnect){
							if(gsClient.isConnectionPending() == false){
								waitingtoconnect = false;
								Gdx.app.log(this.toString(), "is connection pending now? " + gsClient.isConnectionPending());
							}
						}

					}
					Gdx.app.log(this.toString(), "is Session Active? " + gsClient.isSessionActive());
					if(gsClient.isSessionActive() == false){
						//game.gsClient.logOff();
						Gdx.app.log(this.toString(),"GS_ERROR: Cannot sign in: No credentials or session id given.");
						//game.gsClient.logIn();
					}

					Gdx.app.log(this.toString(),"player display name: " + gsClient.getPlayerDisplayName());
					Gdx.app.log(this.toString(),"Game Service Id: " + gsClient.getGameServiceId());
					Gdx.app.log(this.toString(),"is feature supported: " + gsClient.isFeatureSupported(IGameServiceClient.GameServiceFeature.ShowAchievementsUI));
					super.create();
				}
			};
		}


}
