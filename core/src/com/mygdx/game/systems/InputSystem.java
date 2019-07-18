package com.mygdx.game.systems;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.libgdx.MouseData;

public class InputSystem extends AbstractSystem {

	private MyGdxGame game;
	private volatile boolean key[] = new boolean[256];
	private List<MouseData> mouseDataList = new LinkedList<MouseData>();
	private int lastMouseX, lastMouseY;

	public InputSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


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

		AbstractEntity player1 = game.playersAvatars.get(0);
		UserInputComponent uic = (UserInputComponent)player1.getComponent(UserInputComponent.class);
		if (uic.controller != null) {
			uic.moveLeft = uic.controller.getAxis(0) < 0; // todo
			uic.moveRight = uic.controller.getAxis(0) > 0; // todo
			uic.jump = uic.controller.getButton(0); // todo
		} else {
			uic.moveLeft = key[29];
			uic.moveRight = key[32];
			uic.jump = key[51] || key[62];  // W or space
		}

		while (mouseDataList.size() > 0) {
			synchronized (mouseDataList) {
				MouseData mouseData = this.mouseDataList.remove(0);
				processMouseData(mouseData);
			}
		}
	}


	private void processMouseData(MouseData mouseData) {
	}


	public void keyDown(int keycode) {
		MyGdxGame.p("key pressed: " + keycode);
		key[keycode] = true;
	}


	public void keyUp(int keycode) {
		//Settings.p("key released: " + keycode);
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


	public void mouseMoved(int screenX, int screenY) {
		Vector3 pos = game.camera.unproject(new Vector3(screenX, screenY, 0));
		lastMouseX = (int)pos.x;
		lastMouseY = (int)pos.y;
		//MyGdxGame.p("Mouse moved at " + lastMouseX + ", " + lastMouseY);
	}


	public void touchDown(int screenX, int screenY, int pointer, int button) {
		//MyGdxGame.p("Mouse clicked at " + screenX + ", " + screenY);
		MouseData mouseData = new MouseData();
		Vector3 pos = game.camera.unproject(new Vector3(screenX, screenY, 0));
		mouseData.screenX = (int)pos.x;// screenX;//(int)newpos.x;
		mouseData.screenY = (int)pos.y;// screenY;
		mouseData.pointer = pointer;
		mouseData.button = button;

		//MyGdxGame.p("Converted:" + mouseData.screenX + ", " + mouseData.screenY);

		synchronized (mouseDataList) {
			this.mouseDataList.add(mouseData);
		}
	}

}
