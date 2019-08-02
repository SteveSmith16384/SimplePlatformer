package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;

public class DrawPostGameGuiSystem {

	private Sprite background;
	private MyGdxGame game;
	private SpriteBatch batch;
	private Sprite winnerSprite;

	public DrawPostGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;

		if (Settings.RELEASE_MODE) {
			Texture tex = new Texture("background3.jpg");
			background = new Sprite(tex);
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);
		}
	}


	public void process() {
		if (Settings.RELEASE_MODE) {
			background.draw(batch);
		}
		
		game.drawFont(batch, "WINNER!", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);
		if (winnerSprite == null) {
			Texture tex = new Texture("player" + game.winnerImageId + "_right1.png");
			winnerSprite = new Sprite(tex);
			winnerSprite.setSize(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/4);
			winnerSprite.setPosition(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
		}
		winnerSprite.draw(batch);
		
		game.drawFont(batch, "PRESS 'S' TO RESTART", 20, Settings.LOGICAL_HEIGHT_PIXELS-120);
	}
	
	

}
