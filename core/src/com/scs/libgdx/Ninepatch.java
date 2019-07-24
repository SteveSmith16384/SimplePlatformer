package com.scs.libgdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.Rect;

public class Ninepatch {

	private Rect rect;
	private Texture tex;
	
	public Ninepatch(Texture t, Rect _rect) {
		tex = t;
		rect = _rect;
	}
	
	
	public Sprite getImage(Texture baseTexture) {
		//Texture splashTexture = new Texture("texture1.png"); // Todo - Remember to dispose
		baseTexture.getTextureData().prepare(); // The api-doc says this is needed
		Pixmap basePixmap = baseTexture.getTextureData().consumePixmap(); // Strange name, but gives the pixmap of the texture. Remember to dispose this also
		//Pixmap pixmap = new Pixmap(splashTexture.getWidth(), splashTexture.getHeight(), Format.RGBA8888); // Remember to dispose
		
		Texture t1 = new Texture("coin_01.png");
		t1.getTextureData().prepare();
		Pixmap p1 = t1.getTextureData().consumePixmap();
		basePixmap.drawPixmap(p1, 0, 0);

		Texture t2 = new Texture("coin_06.png");
		t2.getTextureData().prepare();
		Pixmap p2 = t2.getTextureData().consumePixmap();
		basePixmap.drawPixmap(p2, 17, 0);
		
		Texture newTex = new Texture(basePixmap);

		// These are not needed anymore
		basePixmap.dispose();
		baseTexture.dispose();
		
		return new Sprite(newTex);
	}

}
