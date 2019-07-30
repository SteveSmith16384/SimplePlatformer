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
		for (PlayerData player : game.players) {
			if (player.isInGame()) {
				if (player.lives > 0) {
					if (player.avatar == null) {
						player.timeUntilAvatar -= Gdx.graphics.getDeltaTime();
						if (player.timeUntilAvatar <= 0) {
							game.createPlayersAvatar(player, player.controller, game.lvl);
						}
					}
				}
			}
		}

		// Check for winner
		int winner = -1;
		int highestScore = -1;

		for (PlayerData player : game.players) {
			if (player.isInGame()) {
				if (player.lives <= 0) {
					if (player.score > highestScore) {
						highestScore = player.score;
						winner = player.imageId;
					}
				} else {
					return;
				}
			}
		}

		game.setWinner(winner);

	}

}
