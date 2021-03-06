package com.mygdx.game.systems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.models.CollisionResults;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

/**
 * This system just detects if two things collide.  it does not actually handle the collisions.
 * @author StephenCS
 *
 */
public class CollisionSystem extends AbstractSystem {

	public CollisionSystem(BasicECS ecs) {
		super(ecs, CollisionComponent.class);
	}


	public CollisionResults collided(AbstractEntity mover, float offX, float offY) {
		PositionComponent moverPos = (PositionComponent)mover.getComponent(PositionComponent.class);
		if (moverPos == null) {
			throw new RuntimeException(mover + " has no " + PositionComponent.class.getSimpleName());
		}
		/*Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();*/
		Iterator<AbstractEntity> it = entities.iterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			if (e != mover) {
				CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
				if (cc != null) {
					PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
					if (pos != null) {
						if (pos.edge != null) {
							// Edges are always solid
							if (pos.edge.intersectsRect(moverPos.rect)) {
								if (offX != 0) {
									return handleEdge(mover, e, moverPos, pos, offX);
								} else {
									return new CollisionResults(e, true, cc.blocksMovement);
								}
							}
						} else {
							if (moverPos.rect.intersects(pos.rect)) {
								if (cc.fluidPlatform) { // Check this first so we can kill baddies by jumping on them
									if (offY < 0) { // Going down
										if (moverPos.prevPos.intersects(pos.rect) == false) { // Didn't collide previously, so we have hit
											return new CollisionResults(e, true, true);
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


	private CollisionResults handleEdge(AbstractEntity mover, AbstractEntity edge, PositionComponent moverPos, PositionComponent edgePos, float offX) {
		// Move player or mob up
		//int max = (int)Math.abs(offX);
		//MyGdxGame.p("Testing up to " + max);
		for (int i=0 ; i<3 ; i++) {
			if (edgePos.edge.intersectsRect(moverPos.rect) == false) {
				MyGdxGame.p("Moved after " + i);
				return null;
				//return new CollisionResults(edge, true, true);
			}
			moverPos.rect.move(0, 1);
		}
		return new CollisionResults(edge, true, true); // Will move us back
	}


	public List<AbstractEntity> getEntitiesAt(float x, float y) {
		List<AbstractEntity> ret = new ArrayList<AbstractEntity>();

		Iterator<AbstractEntity> it = this.entities.iterator();// ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity e = it.next();
			PositionComponent pos = (PositionComponent)e.getComponent(PositionComponent.class);
			if (pos != null) {
				if (pos.rect.contains(x, y)) {
					ret.add(e);
				}
			}
		}
		return ret;
	}


	public boolean intersects(AbstractEntity e1, AbstractEntity e2) {
		PositionComponent p1 = (PositionComponent)e1.getComponent(PositionComponent.class);
		PositionComponent p2 = (PositionComponent)e2.getComponent(PositionComponent.class);
		if (p1 != null && p2 != null) {
			return p1.rect.intersects(p2.rect);
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity train, AbstractEntity track) {
		PositionComponent trainPos = (PositionComponent)train.getComponent(PositionComponent.class);
		PositionComponent trackPos = (PositionComponent)track.getComponent(PositionComponent.class);
		if (trainPos != null && trackPos != null) {
			return trackPos.rect.contains(trainPos.rect.centerX(), trainPos.rect.centerY());
		}
		return false;
	}


	public boolean isCentreOver(AbstractEntity track, float cx, float cy) {
		PositionComponent trackPos = (PositionComponent)track.getComponent(PositionComponent.class);
		if (trackPos != null) {
			return trackPos.rect.contains(cx, cy);
		}
		return false;
	}


}
