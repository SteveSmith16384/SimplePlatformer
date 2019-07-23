package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.PlayerData;

public class DrawScoreSystem { //extends AbstractSystem {

	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawScoreSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

	}

/*
	@Override
	public Class getEntityClass() {
		return UserInputComponent.class;
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		UserInputComponent uic = (UserInputComponent)entity.getComponent(UserInputComponent.class);
		if (uic != null) {
			game.drawFont(batch, "Score: " + uic.score, 20, 40); // todo - position based on player
		}
	}
*/

	public void process() {
		for (PlayerData player : game.players.values()) {
			game.drawFont(batch, "Score: " + player.score, 20, 40); // todo - position based on player
		}
	}
}
