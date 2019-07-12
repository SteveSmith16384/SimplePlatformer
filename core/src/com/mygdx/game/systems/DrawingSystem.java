package com.mygdx.game.systems;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ImageData;
import com.mygdx.game.components.PositionData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class DrawingSystem extends AbstractSystem {

	private MyGdxGame game;
	private SpriteBatch batch;
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	//private Sprite cursor;

	public DrawingSystem(MyGdxGame _game, BasicECS ecs, SpriteBatch _batch) {
		super(ecs);

		game = _game;
		batch = _batch;
	}


	@Override
	public void process() {
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			this.processEntity(entity);
		}
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		ImageData imageData = (ImageData)entity.getComponent(ImageData.class);
		PositionData posData = (PositionData)entity.getComponent(PositionData.class);
		if (imageData != null) {
			if (imageData.sprite == null) {
				Texture tex = getTexture(imageData.imageFilename);
				if (imageData.atlasPosition == null) {
					imageData.sprite = new Sprite(tex);
				} else {
					TextureAtlas atlas = new TextureAtlas();
					atlas.addRegion("r", tex, (int)imageData.atlasPosition.left, (int)imageData.atlasPosition.bottom, (int)imageData.atlasPosition.width(), (int)imageData.atlasPosition.height());
					imageData.sprite = atlas.createSprite("r");
				}
				imageData.sprite.setSize(imageData.w, imageData.h);
			}
			imageData.sprite.setPosition(posData.rect.getX(), posData.rect.getY());
			imageData.sprite.draw(batch);
		}
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

