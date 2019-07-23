package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.models.PlayerData;

public class ProcessPlayersSystem {

	private MyGdxGame game;

	public ProcessPlayersSystem(MyGdxGame _game) {
		game = _game;
	}


	public void process() {
		for (PlayerData player : game.players.values()) {
			if (player.avatar == null) {
				player.timeUntilAvatar -= Gdx.graphics.getDeltaTime();
				if (player.timeUntilAvatar <= 0) {
					game.createPlayersAvatar(player.playerId, player.controller, game.lvl);
				}
			}
		}

	}

}
