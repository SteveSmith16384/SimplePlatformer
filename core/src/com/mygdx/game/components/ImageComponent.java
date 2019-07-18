package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.RectF;

public class ImageComponent {

	public final String imageFilename;
	public float w, h;
	public Sprite sprite;
	
	public RectF atlasPosition; // Fill this in if it uses atlas
	
	public ImageComponent(String _filename, float _w, float _h)  {
		this.imageFilename = _filename;
		w = _w;
		h = _h;
		
		if (_filename == null || _filename.length() == 0) {
			throw new RuntimeException("Todo");
		}
	}
	

	public ImageComponent(String _filename, float _w, float _h, RectF _atlasPosition)  {
		this(_filename, _w, _h);
		
		atlasPosition = _atlasPosition;
	}
	

}
