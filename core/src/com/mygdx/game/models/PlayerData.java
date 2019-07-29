package com.mygdx.game.models;

import com.badlogic.gdx.controllers.Controller;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {

	public Controller controller; // If null, player is keyboard
	public boolean in_game = false;
	public AbstractEntity avatar;
	public float timeUntilAvatar;
	public int score;
	public int lives;

	public PlayerData(Controller _controller) {
		this.controller = _controller;
		lives = 3;
	}

}
