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
			game.sfx.play("Retro_Game_Sounds_SFX_01.ogg");
			if (uic != null) {
				uic.player.score += 100;
			}
			game.ecs.addEntity(game.entityFactory.createRisingCoin(coin));
			break;
		default:
			throw new RuntimeException("Unknown collectable type: " + cc.type);
		}
	}

}
