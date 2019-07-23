package com.mygdx.game.components;

public class CollisionComponent {
	
	public boolean alwaysCollides;
	public boolean fluidPlatform;
	public boolean blocksMovement;
	public boolean isLadder;

	public CollisionComponent(boolean _alwaysCollides, boolean _fluidPlatform, boolean _blocksMovement, boolean _isLadder) {
		alwaysCollides = _alwaysCollides;
		fluidPlatform = _fluidPlatform;
		blocksMovement = _blocksMovement;
		isLadder = _isLadder;
	}

}
