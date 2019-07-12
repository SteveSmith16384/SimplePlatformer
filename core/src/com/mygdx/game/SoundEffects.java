package com.mygdx.game;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public final class SoundEffects {
	
	private HashMap<String, Sound> sfx = new HashMap<String, Sound>(); 

	public SoundEffects() {
	}

	
	public void play(String s) {
		if (!sfx.containsKey(s)) {
			sfx.put(s, Gdx.audio.newSound(Gdx.files.internal(s)));
		}
		Sound sound = sfx.get(s);
		sound.play();
	}
	
	
	public void dispose() { // todo -call this
		Iterator<Sound> it = sfx.values().iterator();
		while (it.hasNext()) {
			Sound s = it.next();
			s.dispose();
		}
		sfx.clear();
	}

	
}
