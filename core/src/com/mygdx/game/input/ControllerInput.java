package com.mygdx.game.input;

import com.badlogic.gdx.controllers.Controller;
import com.mygdx.game.Settings;

public class ControllerInput implements IPlayerInput {

	private Controller controller;
	
	public ControllerInput(Controller _controller) {
		controller = _controller;
	}

	@Override
	public boolean isLeftPressed() {
		return controller.getAxis(Settings.AXIS) < -0.5f;
	}

	
	@Override
	public boolean isRightPressed() {
		return controller.getAxis(Settings.AXIS) > 0.5f;
	}

	
	@Override
	public boolean isJumpPressed() {
		return this.controller.getButton(1);
	}

}
