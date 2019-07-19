package com.mygdx.game.systems;

import com.mygdx.game.components.CollectableComponent;
import com.scs.basicecs.AbstractEntity;

public class CollectorSystem {

	public CollectorSystem() {

	}
	
	
	public void entityCollected(AbstractEntity collector, AbstractEntity coin) {
		coin.remove();
		
		CollectableComponent cc = (CollectableComponent)coin.getComponent(CollectableComponent.class);
		switch (cc.type) {
		case Coin:
			
			break;
		default:
			throw new RuntimeException("todo");
			break;
		
		}
	}

}
