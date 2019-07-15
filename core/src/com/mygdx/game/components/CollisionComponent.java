package com.mygdx.game.components;

public class CollisionComponent {
	
	public boolean alwaysCollides, collidesAsPlatform;

	public CollisionComponent(boolean _alwaysCollides, boolean _collidesAsPlatform) {
		alwaysCollides = _alwaysCollides;
		collidesAsPlatform = _collidesAsPlatform;
	}

}
