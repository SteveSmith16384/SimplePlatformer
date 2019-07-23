package com.mygdx.game.helpers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.scs.basicecs.AbstractEntity;

public class AnimationFramesHelper { // todo - don 't make everything static, and dispose at end

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static AnimationCycleComponent generateForCoin(float size) {
		AnimationCycleComponent acd = new AnimationCycleComponent(.2f);
		acd.frames = new Sprite[9];
		
		for(int i=1 ; i<=8 ; i++) {
			Texture tex = getTexture("coin_0" + i + ".png");
			Sprite sprite = new Sprite(tex);
			sprite.setSize(size * (tex.getWidth()/45f), size);
			acd.frames[i-1] = sprite;
		}

		return acd;
	}
	
	
	public static void createPlayersFrames(AbstractEntity player, float w, float h) {
		WalkingAnimationComponent wac = (WalkingAnimationComponent)player.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[3];
		wac.framesRight = new Sprite[3];
		wac.framesLeft[0] = new Sprite(getTexture("player1_right1.png"));
		wac.framesLeft[0].flip(true,  false);
		wac.framesLeft[0].setSize(w, h);
		wac.framesLeft[1] = new Sprite(getTexture("player1_right2.png"));
		wac.framesLeft[1].setSize(w, h);
		wac.framesLeft[1].flip(true,  false);
		wac.framesLeft[2] = new Sprite(getTexture("player1_right3.png"));
		wac.framesLeft[2].setSize(w, h);
		wac.framesLeft[2].flip(true, false);

		wac.framesRight[0] = new Sprite(getTexture("player1_right1.png"));
		wac.framesRight[0].setSize(w, h);
		wac.framesRight[1] = new Sprite(getTexture("player1_right2.png"));
		wac.framesRight[1].setSize(w, h);
		wac.framesRight[2] = new Sprite(getTexture("player1_right3.png"));
		wac.framesRight[2].setSize(w, h);
		
		wac.idleFrame = wac.framesRight[0];
		
	}


	public static void createMob1Frames(AbstractEntity mob, float w, float h) {
		WalkingAnimationComponent wac = (WalkingAnimationComponent)mob.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[7];
		wac.framesRight = new Sprite[7];

		Texture texture = getTexture("mob1_frames.png");
		TextureAtlas atlas = new TextureAtlas();
		for (int i=0 ; i<6 ; i++) {
			atlas.addRegion("frame"+i, texture, i*16, 0, 16, 13);
		/*}
		for (int i=0 ; i<6 ; i++) {*/
			wac.framesLeft[i] = atlas.createSprite("frame"+i);
			wac.framesLeft[i].setSize(w, h);
			wac.framesRight[i] = atlas.createSprite("frame"+i);
			wac.framesRight[i].setSize(w, h);
			wac.framesRight[i].flip(true, false);
		}
		wac.idleFrame = wac.framesLeft[0];
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
