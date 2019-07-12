package com.mygdx.game.systems;

import java.util.Iterator;

import com.mygdx.game.components.PositionData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class CollisionSystem {

	private BasicECS ecs;
	
	public CollisionSystem(BasicECS ecs) {
	}


	public boolean collided(AbstractEntity mover) {
		PositionData moverPos = (PositionData)mover.getComponent(PositionData.class);
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			PositionData pos = (PositionData)e.getComponent(PositionData.class);
			if (pos != null) {
				if (moverPos.rect.intersects(pos.rect)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean intersects(AbstractEntity e1, AbstractEntity e2) {
		PositionData p1 = (PositionData)e1.getComponent(PositionData.class);
		PositionData p2 = (PositionData)e2.getComponent(PositionData.class);
		if (p1 != null && p2 != null) {
			return p1.rect.intersects(p2.rect);
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity train, AbstractEntity track) {
		PositionData trainPos = (PositionData)train.getComponent(PositionData.class);
		PositionData trackPos = (PositionData)track.getComponent(PositionData.class);
		if (trainPos != null && trackPos != null) {
			return trackPos.rect.contains(trainPos.rect.centerX(), trainPos.rect.centerY());
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity track, float cx, float cy) {
		PositionData trackPos = (PositionData)track.getComponent(PositionData.class);
		if (trackPos != null) {
			return trackPos.rect.contains(cx, cy);
		}
		return false;
	}

}
