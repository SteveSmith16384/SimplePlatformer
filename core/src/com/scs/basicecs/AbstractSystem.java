package com.scs.basicecs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractSystem {

	protected BasicECS ecs;
	protected List<AbstractEntity> entities;

	public AbstractSystem(BasicECS _ecs) {
		this.ecs = _ecs;

		this.ecs.addSystem(this);

		if (this.getComponentClass() != null) {
			entities = new ArrayList<AbstractEntity>();
		}
	}


	/**
	 * Override if this system should only deal with entities that have a specific component.
	 */
	public Class<?> getComponentClass() {
		return null;
	}


	public String getName() {
		return this.getClass().getSimpleName();
	}


	// Override if required to run against specific entities specified by getComponentClass()
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


	// Override if required to run against all entities.
	public void processEntity(AbstractEntity entity) {
	}

}
