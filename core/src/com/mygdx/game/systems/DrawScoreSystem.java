package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class DrawScoreSystem extends AbstractSystem {

	private MyGdxGame game;
	private SpriteBatch batch;

	public DrawScoreSystem(MyGdxGame _game, BasicECS ecs, SpriteBatch _batch) {
		super(ecs);

		game = _game;
		batch = _batch;

	}


	@Override
	public void processEntity(AbstractEntity entity) {
		UserInputComponent uic = (UserInputComponent)entity.getComponent(UserInputComponent.class);
		if (uic != null) {
			game.drawFont(batch, "Creds: " + uic.score, 20, 40);
		}
	}


}
