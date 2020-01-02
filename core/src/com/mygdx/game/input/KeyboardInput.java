package com.mygdx.game.input;

import com.mygdx.game.systems.InputSystem;

public class KeyboardInput implements IPlayerInput {

	private InputSystem inputSystem;
	
	public KeyboardInput(InputSystem _inputSystem) {
		inputSystem = _inputSystem;
	}

	@Override
	public boolean isLeftPressed() {
		return this.inputSystem.key[29];
	}

	
	@Override
	public boolean isRightPressed() {
		return this.inputSystem.key[32];
	}

	
	@Override
	public boolean isJumpPressed() {
		return this.inputSystem.key[51] || this.inputSystem.key[62]; // w or space
	}

}
