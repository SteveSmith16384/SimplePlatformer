package com.mygdx.game.systems;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.models.PlayerData;

public class DrawPreGameGuiSystem {

	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawPreGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

	}


	public void process() {
		game.drawFont(batch, Controllers.getControllers().size +  " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		int count = 0;
		for (PlayerData player : game.players) {
			if (player.in_game) {
				count++;
			}
		}
		game.drawFont(batch, count +  " players in the game!", 20, Settings.LOGICAL_HEIGHT_PIXELS-60);
	}
	
	

}
