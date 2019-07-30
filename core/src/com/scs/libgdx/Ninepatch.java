package com.scs.libgdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.Rect;

public class Ninepatch {

	private Rect rect; // The insets!
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
		basePixmap.drawPixmap(p1, 0, 0, rect.left, t1.getHeight(), 
				0, 0, rect.left, h);
		
		// Top
		basePixmap.drawPixmap(p1, 0, t1.getHeight()-rect.top, t1.getWidth(), rect.top, 
				0, h-rect.top, w, rect.top);
		
		// Right
		basePixmap.drawPixmap(p1, t1.getWidth()-rect.right, 0, rect.right, t1.getHeight(), 
				w-rect.right, 0, rect.right, h);
		
		// Bottom
		basePixmap.drawPixmap(p1, 0, 0, t1.getWidth(), rect.bottom, 
				0, 0, w, rect.bottom);

		// Middle
		basePixmap.drawPixmap(p1, rect.left, rect.bottom, t1.getWidth()-rect.left-rect.right, t1.getHeight()-rect.bottom-rect.top,
				rect.left, rect.bottom, w-rect.left-rect.right, h-rect.bottom-rect.top);
		
		
		Texture newTex = new Texture(basePixmap);

		//todo basePixmap.dispose();
		//todo t1.dispose();
		
		return new Sprite(newTex);
	}


}
