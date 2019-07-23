package com.mygdx.game.models;

import com.badlogic.gdx.controllers.Controller;
import com.scs.basicecs.AbstractEntity;

public class PlayerData {

	public int playerId;
	public Controller controller; // If null, player is keyboard
	public int score;
	public AbstractEntity avatar;
	public float timeUntilAvatar;

	public PlayerData(int _id, Controller _controller) {
		playerId = _id;
		this.controller = _controller;
	}

}
