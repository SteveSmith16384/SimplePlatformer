package com.mygdx.game.systems;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.models.PlayerData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class InputSystem extends AbstractSystem {

	private MyGdxGame game;
	public volatile boolean key[] = new boolean[256];

	public InputSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs, PlayersAvatarComponent.class);

		game = _game;
	}


	@Override
	public void process() {
		// Process keys
		if (key[Keys.F1]) {
			key[Keys.F1] = false;
			if (Gdx.app.getType() == ApplicationType.WebGL) {
				if (!Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
				}
			} else {
				game.toggleFullscreen = true;
			}
		}

		if (key[Keys.S] && game.gameStage != 0) { // S to start
			key[Keys.S] = false;
			game.startNextStage();
		}

		if (game.gameStage == -1) {
			if (key[Keys.SPACE]) { // Space for keyboard player to join
				key[Keys.SPACE] = false;
				PlayerData player = game.getKeyboardPlayer();
				if (player.isInGame() == false) {
					MyGdxGame.p("Keyboard player joined");
					player.setInGame(true);
				}
			}

			// See if players want to join
			/*if (Settings.CONTROLLER_MODE_1) {
				for (PlayerData player : game.players.values()) {
					if (player.isInGame() == false) {
						if (player.controller != null) {
							if (player.controller.getButton(1)) {
								MyGdxGame.p("Controller player joined!");
								player.setInGame(true);
							}
						}
					}
				}
			}*/
		} else if (game.gameStage == 0) {
			super.process();
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PlayersAvatarComponent pac = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
		pac.moveLeft = pac.controller.isLeftPressed();
		pac.moveRight = pac.controller.isRightPressed();
		pac.jump = pac.controller.isJumpPressed();
	}


	public void keyDown(int keycode) {
		/*if (!Settings.RELEASE_MODE) {
			MyGdxGame.p("key pressed: " + keycode);
		}*/
		key[keycode] = true;
	}


	public void keyUp(int keycode) {
		/*if (!Settings.RELEASE_MODE) {
			MyGdxGame.p("key released: " + keycode);
		}*/

		key[keycode] = false;
	}


	public void buttonDown(Controller controller, int buttonCode) {
		if (game.gameStage == -1) {
			PlayerData player = game.getControllerPlayer(controller);
			player.setInGame(true);
		}
	}


}
