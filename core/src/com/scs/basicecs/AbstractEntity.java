package com.scs.basicecs;

import java.util.HashMap;

public class AbstractEntity {

    private static int next_id = 0;

    public int id;
    public String name;
    private HashMap<Class, Object> components = new HashMap<Class, Object>();
    private boolean markForRemoval = false;

    public AbstractEntity(String _name) {
        this.id = next_id++;
        this.name = _name;
    }


    public void addComponent(Object component) {
        this.components.put(component.getClass(), component);
    }


    public void removeComponent(Object component) {
        this.components.remove(component.getClass());
    }


    public Object getComponent(Class name) {
        if (this.components.containsKey(name)) {
            return this.components.get(name);
        } else {
            return null;
        }
    }


    public HashMap<Class, Object> getComponents() {
        return this.components;
    }
    
    
    public boolean isMarkedForRemoval() {
    	return this.markForRemoval;
    }
    
    
    public void remove() {
    	this.markForRemoval = true;
    }
    
    
    @Override
    public String toString() {
    	return name;
    }

}
