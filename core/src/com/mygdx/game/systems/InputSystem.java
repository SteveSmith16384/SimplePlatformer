package com.mygdx.game.systems;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.models.PlayerData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class InputSystem extends AbstractSystem {//implements ControllerListener {

	private MyGdxGame game;
	private volatile boolean key[] = new boolean[256];

	public InputSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getEntityClass() {
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

		if (key[Keys.S] && game.gameStage != 0) { // S to start
			key[Keys.S] = false;
			game.startNextStage();
		}

		if (game.gameStage == -1) {
			if (key[Keys.SPACE]) { // Space for keyboard player to join
				key[Keys.SPACE] = false;
				for (PlayerData player : game.players.values()) {
					if (player.controller == null) {
						if (player.isInGame() == false) {
							MyGdxGame.p("Keyboard player joined");
							player.setInGame(true);
							break;
						}
					}
				}
			}

			// See if players want to join
			if (Settings.CONTROLLER_MODE_1) {
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
					//MyGdxGame.p("Axis:" + uic.controller.getAxis(3));
					// 0 =square
					if (uic.controller.getButton(1)) {
						MyGdxGame.p("button!");
					}
				}
				if (Settings.CONTROLLER_MODE_1) {
					uic.moveLeft = uic.controller.getAxis(Settings.AXIS) < -0.5f;
					uic.moveRight = uic.controller.getAxis(Settings.AXIS) > 0.5f;
					uic.jump = uic.controller.getButton(1);
				}
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
	}


	public boolean buttonDown(Controller controller, int buttonCode) {
		if (buttonCode == 1) {
			if (game.gameStage == -1) {
				PlayerData player = game.players.get(controller);
				player.setInGame(true);
			} else if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.jump = true;
				}
			}
		}
		return false;
	}


	public boolean buttonUp(Controller controller, int buttonCode) {
		if (buttonCode == 1) {
			if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.jump = false;
				}
			}
		}
		return false;
	}


	public boolean axisMoved(Controller controller, int axisCode, float value) {
		if (Settings.RELEASE_MODE == false) {
			float val = controller.getAxis(axisCode);
			if (val < -0.5f || val > .5f) {
				MyGdxGame.p("Axis " + axisCode + ": " + controller.getAxis(axisCode));
			}
		}
		if (axisCode == Settings.AXIS) {
			if (game.gameStage == 0) {
				AbstractEntity entity = game.players.get(controller).avatar;
				if (entity != null) {
					PlayersAvatarComponent uic = (PlayersAvatarComponent)entity.getComponent(PlayersAvatarComponent.class);
					uic.moveLeft = uic.controller.getAxis(Settings.AXIS) < -0.5f;
					uic.moveRight = uic.controller.getAxis(Settings.AXIS) > 0.5f;
				}
			}
		}
		return false;
	}

}
