package com.scs.awt;

public class PointF {

	public float x, y;

	public PointF() {
		this(0, 0);
	}


	public PointF(float x, float y) {
		this.x = x;
		this.y = y;
	}


	public float length() {
		return (float)Math.sqrt(x * x + y * y);
	}


	public void normalizeLocal() {
		float len = length();
		if (len != 0) {
			x /= len;
			y /= len;
		}
	}

}
