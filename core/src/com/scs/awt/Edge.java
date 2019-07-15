package com.scs.awt;

import com.mygdx.game.models.LineData;

public class Edge extends LineData {

	//private RectF bounds;

	public Edge(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);

		//bounds = new RectF(Math.min(x1, x2), Math.max(y1, y2), Math.max(x1, x2), Math.min(y1, y2));
	}

	
	public boolean intersectsRect(RectF r) {
		return intersectsRect(r.getX(), r.getY(), r.width(), r.height());
	}
	
	
	public boolean intersectsRect(float x, float y, float w, float h) {
		if (w <= 0 || h <= 0) {
			return false;
		}

		if (x1 >= x && x1 <= x + w && y1 >= y && y1 <= y + h)
			return true;
		if (x2 >= x && x2 <= x + w && y2 >= y && y2 <= y + h)
			return true;

		return (linesIntersect(this, new LineData(x, y, x, y+h))
				|| linesIntersect(this, new LineData(x, y+h, x+w, y+h))
				|| linesIntersect(this, new LineData(x+w, y, x+w, y+h))
				|| linesIntersect(this, new LineData(x, y, x+w, y)));
	}

	
	private static boolean linesIntersect(LineData pLine1, LineData pLine2) {
	    float
	        s1_x = pLine1.x2 - pLine1.x1,
	        s1_y = pLine1.y2 - pLine1.y1,

	        s2_x = pLine2.x2 - pLine2.x1,
	        s2_y = pLine2.y2 - pLine2.y1,

	        s = (-s1_y * (pLine1.x1 - pLine2.x1) + s1_x * (pLine1.y1 - pLine2.y1)) / (-s2_x * s1_y + s1_x * s2_y),
	        t = ( s2_x * (pLine1.y1 - pLine2.y1) - s2_y * (pLine1.x1 - pLine2.x1)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
	    	return true;
	    }

	    return false;
	}
}
