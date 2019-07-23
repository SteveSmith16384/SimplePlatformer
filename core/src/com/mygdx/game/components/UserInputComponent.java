package com.mygdx.game.components;

import com.badlogic.gdx.controllers.Controller;

public class UserInputComponent {

	public int id;
	public Controller controller; // If null, player is keyboard
	public boolean moveLeft, moveRight, jump;
	public int score;
	
	public UserInputComponent(int _id, Controller _controller) {
		id = _id;
		controller = _controller;
	}
	
}
