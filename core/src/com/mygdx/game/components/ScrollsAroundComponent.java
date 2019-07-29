package com.mygdx.game.components;

/**
 * For entities that move around as the play area is scrolled.
 *
 */
public class ScrollsAroundComponent {
	
	public boolean removeWhenNearEdge; // othewise, removed only when off-screen
	
	public ScrollsAroundComponent(boolean _removeWhenNearEdge) {
		removeWhenNearEdge = _removeWhenNearEdge;		
	}

}
