package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		try {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.title = Settings.TITLE;
			config.width = Settings.WINDOW_WIDTH_PIXELS;
			config.height = Settings.WINDOW_HEIGHT_PIXELS;
			new LwjglApplication(new MyGdxGame(), config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
