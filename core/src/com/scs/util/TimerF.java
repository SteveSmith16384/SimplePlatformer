package com.scs.util;

/**
 * We can't just use currentTimeMillis as when the game is paused, it will still continue.
 *
 */
public class TimerF {
	
	private float interval;
	private float remaining;
	
	public TimerF(float _interval, boolean fireImmed) {
		super();
		
		remaining = fireImmed ? 0 : _interval;
		interval = _interval;
	}
	
	
	public boolean hasHit(float amt) {
		remaining -= amt;
		boolean result = false;
		if (remaining <= 0) {
			remaining = interval;
			result = true;
		}
		return result;
	}
	
	
	public float getTimeRemaining() {
		return remaining;
	}

}
