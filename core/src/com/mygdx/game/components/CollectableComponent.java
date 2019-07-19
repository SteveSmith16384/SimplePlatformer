package com.mygdx.game.components;

public class CollectableComponent {
	
	public enum Type {Coin}
	
	public Type type;
	
	public CollectableComponent(Type _type) {
		type = _type;
	}
	

}
