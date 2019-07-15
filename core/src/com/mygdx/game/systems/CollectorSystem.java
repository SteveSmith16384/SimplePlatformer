package com.mygdx.game.systems;

import com.scs.basicecs.AbstractEntity;

public class CollectorSystem {

	public CollectorSystem() {

	}
	
	
	public void entityCollected(AbstractEntity collector, AbstractEntity powerup) {
		powerup.remove();
		// todo
	}

}
