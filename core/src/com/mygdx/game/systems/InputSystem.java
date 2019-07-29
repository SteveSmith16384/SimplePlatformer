package com.mygdx.game.systems;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.models.PlayerData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class InputSystem extends AbstractSystem {

	private MyGdxGame game;
	private volatile boolean key[] = new boolean[256];

	public InputSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class getEntityClass() {
		return PlayersAvatarComponent.class;
	}


	@Override
	public void process() {
		// Process keys
		if (key[Keys.F1]) {// && Gdx.app.getType() == ApplicationType.WebGL) {
			key[Keys.F1] = false;
			if (Gdx.app.getType() == ApplicationType.WebGL) {
				if (!Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
				}
			} else {
				game.toggleFullscreen = true;
			}
		}

		if (key[Keys.S]) { // S to start
			game.startNextStage();
		}

		if (key[Keys.SPACE]) { // Space for keyboard player to join
			for (PlayerData player : game.players) {
				if (player.controller == null) {
				if (player.in_game == false) {
						MyGdxGame.p("Keyboard player joined");
						player.in_game = true;
						break;
					}
				}
			}
		}

		if (game.gameStage == -1) {
			for (Controller controller : Controllers.getControllers()) {
				boolean playerFound = false;
				for (PlayerData player : game.players) {
					if (player.controller == controller) {
						playerFound = true;
						break;
					}
				}
				if (!playerFound) {
					game.createPlayer(controller);
				}
			}

			// See if players want to join
			for (PlayerData player : game.players) {
				if (player.in_game == false) {
					if (player.controller != null) {
						if (player.controller.getButton(1)) {
							MyGdxGame.p("Controller player joined!");
							player.in_game = true;
						}
					}
				}
			}

		} else if (game.gameStage == 0) {
			super.process();
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
		if (uic != null) {
			if (uic.controller != null) {
				if (!Settings.RELEASE_MODE) {
					// 1 = right stick l/r
					// 2 = left stick u/d
					// 3 = left stick l.r
					MyGdxGame.p("Axis:" + uic.controller.getAxis(3));
					// 0 =square
					if (uic.controller.getButton(1)) {
						MyGdxGame.p("button!");
					}
				}
				uic.moveLeft = uic.controller.getAxis(3) < -0.5f;
				uic.moveRight = uic.controller.getAxis(3) > 0.5f;
				uic.jump = uic.controller.getButton(1);
			} else {
				uic.moveLeft = key[29];
				uic.moveRight = key[32];
				uic.jump = key[51] || key[62];  // W or space
			}
		}
	}


	public void keyDown(int keycode) {
		if (!Settings.RELEASE_MODE) {
			MyGdxGame.p("key pressed: " + keycode);
		}
		key[keycode] = true;
	}


	public void keyUp(int keycode) {
		if (!Settings.RELEASE_MODE) {
			//Settings.p("key released: " + keycode);
		}

		key[keycode] = false;

		/*if (gameStage == -1) {
			nextStage = true;
		}
		if (keycode == 66) { //Enter
			if (gameStage == 1) {
				nextStage = true;
			}
		} else if (keycode == Keys.F1) {// && Gdx.app.getType() == ApplicationType.WebGL) {
			if (Gdx.app.getType() == ApplicationType.WebGL) {
				if (!Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
				}
			} else {
				toggleFullscreen = true;
			}
		}*/
	}

}
