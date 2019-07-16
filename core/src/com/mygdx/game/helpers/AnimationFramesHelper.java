package com.mygdx.game.helpers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Settings;
import com.mygdx.game.components.AnimationCycleData;

public class AnimationFramesHelper {

	private HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public AnimationCycleData generateForCoin() {
		AnimationCycleData acd = new AnimationCycleData(.5f);
		acd.frames = new Sprite[8];
		
		Texture tex = getTexture("conveyor_lr_wide.png");
		TextureAtlas atlas = new TextureAtlas();
		for(int i=0 ; i<4 ; i++) {
			atlas.addRegion("r"+i, tex, (i*2), 0, 12, 12);
			Sprite sprite = atlas.createSprite("r"+i);
			sprite.setSize(Settings.DEFAULT_SQ_SIZE, Settings.DEFAULT_SQ_SIZE);
			
			switch (dir) {
			case Left:
				acd.frames[i] = sprite;
				break;
			case Right:
				acd.frames[3-i] = sprite;
				break;
			case Up:
				sprite.rotate90(true);
				acd.frames[i] = sprite;
				break;
			case Down:
				sprite.rotate90(false);
				acd.frames[3-i] = sprite;
				break;
			default:
				throw new RuntimeException("Unknown dir: " + dir);
			
			}
		}

		return acd;


	}


	public Texture getTexture(String filename) {
		if (textures.containsKey(filename)) {
			return textures.get(filename);
		}
		Texture t = new Texture(filename);
		this.textures.put(filename, t);
		return t;
	}


	public void dispose() {
		for(Texture t : this.textures.values()) {
			t.dispose();
		}

	}


}
