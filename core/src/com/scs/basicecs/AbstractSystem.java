package com.scs.basicecs;

import java.util.Iterator;

public abstract class AbstractSystem {

	protected BasicECS ecs;

	public AbstractSystem(BasicECS _ecs) {
		this.ecs = _ecs;

		this.ecs.addSystem(this);

	}

	
	public Class getEntityClass() {
		return null;
	}

	
	public String getName() {
		return this.getClass().getSimpleName();
	}


	// Override if required to run against all entities
	public void process() {
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
		}
	}


	public void processEntity(AbstractEntity entity) {
		// Override if required to run against a single entity.
	}

}
