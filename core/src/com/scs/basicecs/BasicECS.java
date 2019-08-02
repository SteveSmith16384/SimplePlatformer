package com.scs.basicecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;

public class BasicECS {

	private HashMap<Class, AbstractSystem> systems = new HashMap<Class, AbstractSystem>();
	private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
	private List<AbstractEntity> to_add_entities = new ArrayList<AbstractEntity>();

	public BasicECS() {
	}


	public void addSystem(AbstractSystem system) {
		this.systems.put(system.getClass(), system);
	}



	public AbstractSystem getSystem(Class clazz) {
		return this.systems.get(clazz);
	}


	public void process() {
		// Remove any entities
		for (int i = this.entities.size()-1 ; i >= 0; i--) {
			AbstractEntity entity = this.entities.get(i);
			if (entity.isMarkedForRemoval()) {
		    	if (!Settings.RELEASE_MODE) {
		    		MyGdxGame.p("Removing " + entity);
		    	}
				this.entities.remove(entity);

				// Remove from systems
				for(AbstractSystem system : this.systems.values()) {
					Class clazz = system.getEntityClass();
					if (clazz != null) {
						if (entity.getComponents().containsKey(clazz)) {
							//MyGdxGame.p("Removing " + entity + " from " + system + " system");
							system.entities.remove(entity);
						}
					}
				}
			}
		}

		for(AbstractEntity e : this.to_add_entities) {
			for(AbstractSystem system : this.systems.values()) {
				Class clazz = system.getEntityClass();
				if (clazz != null) {
					if (e.getComponents().containsKey(clazz)) {
						system.entities.add(e);
					}
				}
			}
			this.entities.add(e);			
		}

		to_add_entities.clear();
	}


	public void addEntity(AbstractEntity e) {
		this.to_add_entities.add(e);
	}


	public AbstractEntity get(int i) {
		return this.entities.get(i);
	}


	public Iterator<AbstractEntity> getIterator() {
		return this.entities.iterator();
	}

	public void removeAllEntities() {
		/*while (this.entities.size() > 0) {
			AbstractEntity entity = this.entities.get(0);
			this.entities.remove(entity);
			//this.eventListener.EntityRemoved(entity);
		}*/
		for(AbstractEntity e : this.entities) {
			e.remove();
		}
	}


}
