package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		try {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setTitle(Settings.TITLE);
			config.setWindowSizeLimits(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS, Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
			new Lwjgl3Application(new MyGdxGame(), config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
