package com.mygdx.game.systems;

import java.util.Iterator;

import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionData;
import com.mygdx.game.models.CollisionResults;
import com.mygdx.game.models.LineData;
import com.scs.awt.RectF;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

/**
 * This system just detects if two things collide.  it does not process the collisions.
 * @author StephenCS
 *
 */
public class CollisionSystem {

	private BasicECS ecs;

	public CollisionSystem(BasicECS _ecs) {
		ecs = _ecs;
	}


	public CollisionResults collided(AbstractEntity mover, float offY) {
		PositionData moverPos = (PositionData)mover.getComponent(PositionData.class);
		if (moverPos == null) {
			throw new RuntimeException(mover + " has no " + PositionData.class.getSimpleName());
		}
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				if (cc != null) {
					PositionData pos = (PositionData)e.getComponent(PositionData.class);
					if (pos != null) {
						if (pos.edge != null) {
							// Edges are always solid
							if (pos.edge.intersectsRect(moverPos.rect)) {
								return handleEdge(mover, e, moverPos, pos);
							}
						} else {
							if (moverPos.rect.intersects(pos.rect)) {
								if (cc.collidesAsPlatform) { // Check this first so we can kill baddies by jumping on them
									if (offY < 0) {
										if (moverPos.prevPos.intersects(pos.rect) == false) {
											return new CollisionResults(e, true, cc.blocksMovement);
										}									
									}
								} else if (cc.isLadder) {
									if (offY < 0) {
										return new CollisionResults(e, false, cc.blocksMovement);
									}
								} else if (cc.alwaysCollides) {
									return new CollisionResults(e, false, cc.blocksMovement);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}


	private CollisionResults handleEdge(AbstractEntity mover, AbstractEntity edge, PositionData moverPos, PositionData edgePos) {
		// Move player or mob up
		for (int i=0 ; i<10 ; i++) {
			if (edgePos.edge.intersectsRect(moverPos.rect) == false) {
				return null;
			}
			moverPos.rect.move(0, 1);
		}
		return new CollisionResults(edge, true, true);
	}


	public AbstractEntity getEntityAt(float x, float y) {
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			PositionData pos = (PositionData)e.getComponent(PositionData.class);
			if (pos != null) {
				if (pos.rect.contains(x, y)) {
					return e;
				}
			}
		}
		return null;
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
