package com.mygdx.game.components;

import com.badlogic.gdx.controllers.Controller;

public class PlayersAvatarComponent {

	public int playerId;
	public Controller controller; // If null, player is keyboard
	public boolean moveLeft, moveRight, jump;
	public long timeStarted;
	
	public PlayersAvatarComponent(int _id, Controller _controller) {
		playerId = _id;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
