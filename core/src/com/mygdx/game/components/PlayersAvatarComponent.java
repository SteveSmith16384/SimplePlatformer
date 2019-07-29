package com.mygdx.game.components;

import com.badlogic.gdx.controllers.Controller;
import com.mygdx.game.models.PlayerData;

public class PlayersAvatarComponent {

	public PlayerData player;//int playerId;
	public Controller controller; // If null, player is keyboard
	public boolean moveLeft, moveRight, jump;
	public long timeStarted;
	
	public PlayersAvatarComponent(PlayerData _player, Controller _controller) {
		player = _player;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
