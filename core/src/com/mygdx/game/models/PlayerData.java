package com.mygdx.game.models;

import com.badlogic.gdx.controllers.Controller;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {

	//public int playerId;
	public Controller controller; // If null, player is keyboard
	public boolean in_game = false;
	public int score;
	public AbstractEntity avatar;
	public float timeUntilAvatar;

	public PlayerData(Controller _controller) {
		//playerId = _id;
		this.controller = _controller;
	}

}
