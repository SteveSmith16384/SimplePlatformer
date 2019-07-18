package com.mygdx.game.systems;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.PositionData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class DrawingSystem extends AbstractSystem {

	private MyGdxGame game;
	private SpriteBatch batch;
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private ShapeRenderer shapeRenderer;

	public DrawingSystem(MyGdxGame _game, BasicECS ecs, SpriteBatch _batch) {
		super(ecs);

		game = _game;
		batch = _batch;

		shapeRenderer = new ShapeRenderer();
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
		ImageComponent imageData = (ImageComponent)entity.getComponent(ImageComponent.class);
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


	public void drawDebug(SpriteBatch batch) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			PositionData posData = (PositionData)entity.getComponent(PositionData.class);

			if (posData.edge != null) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				shapeRenderer.setColor(Color.RED);
				shapeRenderer.line(posData.edge.x1, posData.edge.y1, posData.edge.x2, posData.edge.y2);
				shapeRenderer.end();
			}
			if (posData.rect != null) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
				shapeRenderer.setColor(Color.GREEN);
				shapeRenderer.rect(posData.rect.left, posData.rect.bottom, posData.rect.width(), posData.rect.height()); 
				shapeRenderer.end();
			}
		}
	}


	public void dispose() {
		for(Texture t : this.textures.values()) {
			t.dispose();
		}
		shapeRenderer.dispose();
	}

}

