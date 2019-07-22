package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractEntity;

public class CollectorSystem {

	private MyGdxGame game;

	public CollectorSystem(MyGdxGame _game) {
		game = _game;
	}


	public void entityCollected(AbstractEntity collector, AbstractEntity coin) {
		coin.remove();

		CollectableComponent cc = (CollectableComponent)coin.getComponent(CollectableComponent.class);
		switch (cc.type) {
		case Coin:
			UserInputComponent uic = (UserInputComponent)collector.getComponent(UserInputComponent.class);
			if (uic != null) {
				uic.score += 100;
			}
			game.ecs.addEntity(game.entityFactory.createRisingCoin(coin));
			break;
		default:
			throw new RuntimeException("todo");
		}
	}

}
