package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.models.PlayerData;

public class DrawPreGameGuiSystem {

	private Sprite background;
	private Sprite logo;
	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawPreGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

		if (Settings.RELEASE_MODE) {
			Texture tex = new Texture("background3.jpg");
			background = new Sprite(tex);
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);

			Texture tex2 = new Texture("sb_logo.png");
			logo = new Sprite(tex2);
			logo.setSize(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
			logo.setPosition(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/4);
		}
	}


	public void process() {
		if (Settings.RELEASE_MODE) {
			background.draw(batch);
			logo.draw(batch);
		}

		//game.drawFont(batch, Controllers.getControllers().size + " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		int count = 0;
		for (PlayerData player : game.players) {
			if (player.isInGame()) {
				count++;
			}
		}
		game.drawFont(batch, count + " players in the game!", 20, Settings.LOGICAL_HEIGHT_PIXELS-80);
		game.drawFont(batch, "Press 'Space' for keyboard player", 20, Settings.LOGICAL_HEIGHT_PIXELS-140);
		game.drawFont(batch, "PRESS 'S' TO START!", 20, Settings.LOGICAL_HEIGHT_PIXELS-200);
	}



}
