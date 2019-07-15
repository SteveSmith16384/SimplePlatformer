package com.mygdx.game.models;

import com.scs.basicecs.AbstractEntity;

public class CollisionResults {

	public AbstractEntity collidedWith;
	public boolean fromAbove, blocksMovement;
	
	public CollisionResults(AbstractEntity _collidedWith, boolean _fromAbove, boolean _blocksMovement) {
		collidedWith = _collidedWith;
		fromAbove = _fromAbove;
		blocksMovement = _blocksMovement;
	}

}
