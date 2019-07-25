package com.scs.libgdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.Point;
import com.scs.awt.Rect;

public class Ninepatch {

	private Rect rect; // The insets!
	private String filename;
	//private Point imgSize;
	
	public Ninepatch(String _filename, Rect _rect) {
		filename = _filename;
		rect = _rect;
		//imgSize = _imgSize;
	}
	
	
	public Sprite getImage(int w, int h) {
		Texture t1 = new Texture(filename);
		t1.getTextureData().prepare();
		Pixmap p1 = t1.getTextureData().consumePixmap();

		Pixmap basePixmap = new Pixmap(w, h, p1.getFormat());

		// Left
		basePixmap.drawPixmap(p1, 0, 0, rect.left, t1.getHeight(), 
				0, 0, rect.left, h);
		
		// Top
		basePixmap.drawPixmap(p1, 0, t1.getHeight()-rect.top, t1.getWidth(), rect.top, 
				0, h-rect.top, w, rect.top);
		
		// Middle
		basePixmap.drawPixmap(p1, rect.left, rect.bottom, t1.getWidth()-rect.left-rect.right, t1.getHeight()-rect.bottom-rect.top,
				rect.left, h-rect.bottom, w-rect.left-rect.right, h-rect.bottom-rect.top);
		
		// Right
		basePixmap.drawPixmap(p1, t1.getWidth()-rect.right, 0, rect.right, t1.getHeight(), 
				w-rect.right, 0, rect.right, h);
		
		// Bottom
		basePixmap.drawPixmap(p1, 0, 0, t1.getWidth(), rect.bottom, 
				0, 0, w, rect.bottom);
		
		
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
