package com.scs.basicecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractSystem {

	protected BasicECS ecs;
	protected List<AbstractEntity> entities;// = new ArrayList<AbstractEntity>();

	public AbstractSystem(BasicECS _ecs) {
		this.ecs = _ecs;

		this.ecs.addSystem(this);

		if (this.getEntityClass() != null) {
			entities = new ArrayList<AbstractEntity>();
		}
	}


	public Class getEntityClass() {
		return null;
	}


	public String getName() {
		return this.getClass().getSimpleName();
	}


	// Override if required to run against all entities
	public void process() {
		if (this.entities == null) {
			Iterator<AbstractEntity> it = ecs.getIterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				this.processEntity(entity);
			}
		} else {
			Iterator<AbstractEntity> it = entities.iterator();
			while (it.hasNext()) {
				AbstractEntity entity = it.next();
				this.processEntity(entity);
			}
		}
	}


	public void processEntity(AbstractEntity entity) {
		// Override if required to run against a single entity.
	}

}
