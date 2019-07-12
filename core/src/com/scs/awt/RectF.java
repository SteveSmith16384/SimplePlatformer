package com.scs.awt;

/**
 * This class assumes the origin is bottom-left (LibGDX)!  NOT top-left!
 * 
 * @author StephenCS
 *
 */
public class RectF {

	public float left, top, right, bottom;

	public RectF() {
	}

	
	public static RectF fromXYWH(float x, float y, float w, float h) {
		return new RectF(x, y+h, x+w, y);
	}

	public RectF(float l, float t, float r, float b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
		
		if (bottom > top) {
			throw new RuntimeException("Top and bottom wrong way round");
		}
		if (left > right) {
			throw new RuntimeException("Left and right wrong way round");
		}
	}


	public void set(float l, float t, float r, float b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
	}


	public void set(RectF r) {
		left = r.left;
		top = r.top;
		right = r.right;
		bottom = r.bottom;
	}


	public static boolean intersects(RectF a, RectF b) {
		return a.left < b.right && b.left < a.right
				&& a.top > b.bottom && b.top > a.bottom; // Switch these if the origin is top-left, not bottom-left!
	}


	public boolean intersects(RectF b) {
		return left < b.right && b.left < right
				&& top > b.bottom && b.top > bottom; // Switch these if the origin is top-left, not bottom-left!
	}


	public boolean contains(float x, float y) {
		return left < right && top > bottom  // check for empty first
				&& x >= left && x < right && y <= top && y > bottom;
	}



	public final float getX() {
		return left;
	}


	public final float getY() {
		return bottom;
	}


	public final float centerX() {
		return (left + right) * 0.5f;
	}


	public final float centerY() {
		return (top + bottom) * 0.5f;
	}


	public final boolean isEmpty() {
		return left >= right || top <= bottom;
	}

	/**
	 * @return the rectangle's width. This does not check for a valid rectangle
	 * (i.e. left <= right) so the result may be negative.
	 */
	public final float width() {
		return right - left;
	}

	
	/**
	 * @return the rectangle's height. This does not check for a valid rectangle
	 * (i.e. top <= bottom) so the result may be negative.
	 */
	public final float height() {
		return top - bottom;
	}
	
	
	@Override
	public String toString() {
		return "RectF:" + left + "," + top + ", " + right + "," + bottom;
	}
	
	
	public void move(float offx, float offy) {
		this.left += offx;
		this.right += offx;
		this.top += offy;
		this.bottom += offy;
	}

}
