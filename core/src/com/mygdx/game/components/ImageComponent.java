package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scs.awt.RectF;

public class ImageComponent {

	public final String imageFilename;
	public float w, h;
	public Sprite sprite;
	public int zOrder; // -1, - or 1

	public RectF atlasPosition; // Fill this in if it uses atlas

	public ImageComponent(String _filename, int _zOrder, float _w, float _h)  {
		this.imageFilename = _filename;
		zOrder = _zOrder;
		w = _w;
		h = _h;

		if (_filename == null || _filename.length() == 0) {
			throw new RuntimeException("Todo");
		}
	}


	public ImageComponent(Sprite _sprite, int _zOrder)  {
		this.sprite = _sprite;
		zOrder = _zOrder;
		w = -1;//_w;
		h = -1;//_h;
		imageFilename = "none";
		
		if (sprite == null) {
			throw new RuntimeException("No sprite");
		}
	}


	public ImageComponent(String _filename, int _zOrder, float _w, float _h, RectF _atlasPosition)  {
		this(_filename, _zOrder, _w, _h);

		atlasPosition = _atlasPosition;
	}


}
