package com.mygdx.game.helpers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.AnimationCycleComponent;

public class AnimationFramesHelper {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static AnimationCycleComponent generateForCoin(float size) {
		AnimationCycleComponent acd = new AnimationCycleComponent(.2f);
		acd.frames = new Sprite[9];
		
		for(int i=1 ; i<=8 ; i++) {
			Texture tex = getTexture("coin_0" + i + ".png");
			Sprite sprite = new Sprite(tex);
			sprite.setSize(size, size);
			acd.frames[i-1] = sprite;
		}

		return acd;
	}


	private static Texture getTexture(String filename) {
		if (textures.containsKey(filename)) {
			return textures.get(filename);
		}
		Texture t = new Texture(filename);
		textures.put(filename, t);
		return t;
	}


	public void dispose() {
		for(Texture t : this.textures.values()) {
			t.dispose();
		}

	}


}
