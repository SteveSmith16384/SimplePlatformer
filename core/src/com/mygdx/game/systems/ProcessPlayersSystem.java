package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.mygdx.game.LevelGenerator;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.models.PlayerData;
import com.scs.basicecs.AbstractEntity;
import com.scs.lang.NumberFunctions;

public class ProcessPlayersSystem {

	private MyGdxGame game;

	public ProcessPlayersSystem(MyGdxGame _game) {
		game = _game;
	}


	public void process() {
		for (PlayerData player : game.players.values()) {
			if (player.isInGame() && player.quit == false) {
				if (player.lives > 0) {
					if (player.avatar == null) {
						player.timeUntilAvatar -= Gdx.graphics.getDeltaTime();
						if (player.timeUntilAvatar <= 0) {
							createPlayersAvatar(player, player.controller);
						}
					}
				}
			}
		}

		// Check for winner
		int winner = -1;
		int highestScore = -1;

		for (PlayerData player : game.players.values()) {
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

	
	private void createPlayersAvatar(PlayerData player, Controller controller) {
		int xPos = NumberFunctions.rnd(50,  Settings.LOGICAL_WIDTH_PIXELS-50);
		AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, xPos, Settings.LOGICAL_HEIGHT_PIXELS);
		game.ecs.addEntity(avatar);

		player.avatar = avatar;
	}

}
