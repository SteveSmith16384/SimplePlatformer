package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.PlayerData;

public class DrawInGameGuiSystem {

	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawInGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;
	}


	public void process() {
		int num = 0;
		for (PlayerData player : game.players) {
			if (player.in_game) {
				game.drawFont(batch, "Score: " + player.score, 20+(num*100), 50);
				game.drawFont(batch, player.lives > 0 ? "Lives: " + player.lives : "GAME OVER!", 20+(num*100), 30);
				num++;
			}
		}
	}
}
