package com.mygdx.game.models;

import com.badlogic.gdx.controllers.Controller;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {
	
	private static int nextImageId = 1;

	public Controller controller; // If null, player is keyboard
	private boolean in_game = false;
	public AbstractEntity avatar;
	public float timeUntilAvatar;
	public int score;
	public int lives;
	public int imageId;

	public PlayerData(Controller _controller) {
		this.controller = _controller;
		lives = 3;
	}
	
	
	public void setInGame() {
		this.in_game = true;
		imageId = nextImageId++;
	}
	
	
	public boolean isInGame() {
		return this.in_game;
	}

}
