package com.mygdx.game.components;

import com.mygdx.game.input.IPlayerInput;
import com.mygdx.game.models.PlayerData;

public class PlayersAvatarComponent {

	public PlayerData player;
	public IPlayerInput controller;
	public boolean moveLeft, moveRight, jump;
	public long timeStarted;
	
	public PlayersAvatarComponent(PlayerData _player, IPlayerInput _controller) {
		player = _player;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
