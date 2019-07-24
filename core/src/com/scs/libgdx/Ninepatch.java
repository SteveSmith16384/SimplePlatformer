package com.scs.libgdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.Rect;

public class Ninepatch {

	private Rect rect;
	private String filename;
	
	public Ninepatch(String _filename, Rect _rect) {
		filename = _filename;
		rect = _rect;
	}
	
	
	public Sprite getImage(int w, int h) {
		Texture t1 = new Texture(filename);
		t1.getTextureData().prepare();
		Pixmap p1 = t1.getTextureData().consumePixmap();

		Pixmap basePixmap = new Pixmap(w, h, p1.getFormat());

		// Left
		/*basePixmap.drawPixmap(p1, 0, 0, 
				0, 0, rect.left, h);*/
		
		// Middle
		basePixmap.drawPixmap(p1, rect.left, rect.bottom, rect.width(), rect.height(),
				rect.left, rect.bottom, w-rect.width(), h-rect.height());
		
		// todo - rest
		
		
		Texture newTex = new Texture(basePixmap);

		//todo basePixmap.dispose();
		//todo t1.dispose();
		
		return new Sprite(newTex);
	}


	public Sprite getImage_OLD(int w, int h) {
		Texture t1 = new Texture("coin_01.png");
		t1.getTextureData().prepare();
		Pixmap p1 = t1.getTextureData().consumePixmap();

		//Texture splashTexture = new Texture("texture1.png"); // Todo - Remember to dispose
		//baseTexture.getTextureData().prepare(); // The api-doc says this is needed
		//baseTexture.getTextureData().consumePixmap(); // Strange name, but gives the pixmap of the texture. Remember to dispose this also
		Pixmap basePixmap = new Pixmap(w, h, p1.getFormat());
		//Pixmap pixmap = new Pixmap(splashTexture.getWidth(), splashTexture.getHeight(), Format.RGBA8888); // Remember to dispose
		
		basePixmap.drawPixmap(p1, 0, 0);

		Texture t2 = new Texture("coin_06.png");
		t2.getTextureData().prepare();
		Pixmap p2 = t2.getTextureData().consumePixmap();
		basePixmap.drawPixmap(p2, 17, 0, 0, 0, w-17, 100);
		
		Texture newTex = new Texture(basePixmap);

		// These are not needed anymore
		basePixmap.dispose();
		//baseTexture.dispose();
		
		return new Sprite(newTex);
	}


}
