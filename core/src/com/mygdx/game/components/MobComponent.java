package com.mygdx.game.components;

/**
 * Generic mob - moved by AI, can be 
 */
public class MobComponent {
	
	public int dirX;
	public float speed;
	public boolean dontWalkOffEdge;
	
	public MobComponent(float _speed, boolean _dontWalkOffEdge) {
		speed = _speed;
		dontWalkOffEdge = _dontWalkOffEdge;
	}
	
}
