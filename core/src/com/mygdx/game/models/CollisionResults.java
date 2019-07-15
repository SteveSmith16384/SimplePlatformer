package com.mygdx.game.models;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean fromAbove;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _fromAbove) {
		collidedWith = _collidedWith;
		fromAbove = _fromAbove;
	}

}
