package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
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
			PlayersAvatarComponent uic = (PlayersAvatarComponent)collector.getComponent(PlayersAvatarComponent.class);
			if (uic != null) {
				game.players.get(uic.playerId).score += 100;
			}
			game.ecs.addEntity(game.entityFactory.createRisingCoin(coin));
			break;
		default:
			throw new RuntimeException("Unknown collectable type: " + cc.type);
		}
	}

}
