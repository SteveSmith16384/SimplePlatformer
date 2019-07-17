package com.mygdx.game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimationCycleComponent {

	public int currentFrame;
	public Sprite[] frames;
	public float interval;
	public float timeUntilNextFrame;
	
	public AnimationCycleComponent(float _interval) {
		interval = _interval;
		timeUntilNextFrame = Gdx.graphics.getDeltaTime() % interval; 
	}
	
}
