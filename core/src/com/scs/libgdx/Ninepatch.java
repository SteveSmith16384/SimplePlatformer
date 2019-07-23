package com.scs.libgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.scs.awt.Rect;

public class Ninepatch {

	private Rect rect;
	private Texture tex;
	
	public Ninepatch(Texture t, Rect _rect) {
		tex = t;
		rect = _rect;
	}
	
	
	public Texture getImage(Rect size) {
		// todo
		Texture splashTexture = new Texture("texture1.png"); // Remember to dispose
		splashTexture.getTextureData().prepare(); // The api-doc says this is needed
		Pixmap splashpixmap = splashTexture.getTextureData().consumePixmap(); // Strange name, but gives the pixmap of the texture. Remember to dispose this also
		Pixmap pixmap = new Pixmap(splashTexture.getWidth(), splashTexture.getHeight(), Format.RGBA8888); // Remember to dispose
		// We want the center point coordinates of the image region as the circle origo is at the center and drawn by the radius
		int x = (int) (splashTexture.getWidth() / 2f);
		int y = (int) (splashTexture.getHeight() / 2f);
		int radius = (int) (splashTexture.getWidth() / 2f - 5); // -5 just to leave a small margin in my picture
		pixmap.setColor(Color.ORANGE);
		pixmap.fillCircle(x, y, radius);
		// Draws the texture on the background shape (orange circle)
		pixmap.drawPixmap(splashpixmap, 0, 0);
		// TADA! New combined texture
		Texture splashImage = new Texture(pixmap); // Not sure if needed, but may be needed to get disposed as well when it's no longer needed
		// These are not needed anymore
		pixmap.dispose();
		splashpixmap.dispose();
		splashTexture.dispose();
		
		return splashImage;
	}

}
