package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.PlayerData;

public class DrawScoreSystem {

	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawScoreSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

	}


	public void process() {
		for (PlayerData player : game.players.values()) {
			game.drawFont(batch, "Score: " + player.score, 20+(player.playerId*100), 40);
		}
	}
}
