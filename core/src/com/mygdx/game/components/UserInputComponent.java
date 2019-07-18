package com.mygdx.game.components;

import com.badlogic.gdx.controllers.Controller;

public class UserInputComponent {

	public Controller controller; // If null, player is keyboard
	public boolean moveLeft, moveRight, jump;
	
	public UserInputComponent(Controller _controller) {
		controller = _controller;
	}
	
}
