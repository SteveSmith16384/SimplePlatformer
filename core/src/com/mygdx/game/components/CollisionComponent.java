package com.mygdx.game.components;

public class CollisionComponent {
	
	public boolean alwaysCollides, collidesAsPlatform, blocksMovement;

	public CollisionComponent(boolean _alwaysCollides, boolean _collidesAsPlatform, boolean _blocksMovement) {
		alwaysCollides = _alwaysCollides;
		collidesAsPlatform = _collidesAsPlatform;
		blocksMovement = _blocksMovement;
	}

}
