package com.scs.awt;

/**
 * This class assumes origin is bottom-left
 * @author StephenCS
 *
 */
public class Rect {

	public int left, top, right, bottom;

	public Rect() {
	}


	public Rect(int l, int t, int r, int b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
	}


	public void set(int l, int t, int r, int b) {
		left = l;
		top = t;
		right = r;
		bottom = b;
	}


	public void set(Rect r) {
		left = r.left;
		top = r.top;
		right = r.right;
		bottom = r.bottom;
	}


	public static boolean intersects(Rect a, Rect b) {
		return a.left < b.right && b.left < a.right
				&& a.top > b.bottom && b.top > a.bottom; // Switch these if the origin is top-left, not bottom-left!
	}


	public boolean contains(int x, int y) {
		return left < right && top > bottom  // check for empty first
				&& x >= left && x < right && y <= top && y > bottom;
	}



	public final int getX() {
		return left;
	}


	public final int getY() {
		return bottom;
	}


	public final int centerX() {
		return (left + right) /2;
	}


	public final int centerY() {
		return (top + bottom) / 2;
	}


	public final boolean isEmpty() {
		return left >= right || top <= bottom;
	}

	/**
	 * @return the rectangle's width. This does not check for a valid rectangle
	 * (i.e. left <= right) so the result may be negative.
	 */
	public final int width() {
		return right - left;
	}

	
	/**
	 * @return the rectangle's height. This does not check for a valid rectangle
	 * (i.e. top <= bottom) so the result may be negative.
	 */
	public final int height() {
		return top - bottom;
	}
	
	
	@Override
	public String toString() {
		return "Rect:" + left + "," + top + ", " + right + "," + bottom;
	}

}
