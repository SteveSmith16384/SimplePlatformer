package com.mygdx.game.models;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean fromAbove, moveBack;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _fromAbove, boolean _moveBack) {
		collidedWith = _collidedWith;
		fromAbove = _fromAbove;
		moveBack = _moveBack;
	}

}
