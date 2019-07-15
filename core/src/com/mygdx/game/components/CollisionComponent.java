package com.mygdx.game.components;

public class CollisionComponent {
	
	public boolean alwaysCollides, collidesAsPlatform, blocksMovement, isLadder;

	public CollisionComponent(boolean _alwaysCollides, boolean _collidesAsPlatform, boolean _blocksMovement, boolean _isLadder) {
		alwaysCollides = _alwaysCollides;
		collidesAsPlatform = _collidesAsPlatform;
		blocksMovement = _blocksMovement;
		isLadder = _isLadder;
	}

}
