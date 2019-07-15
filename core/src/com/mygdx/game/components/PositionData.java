package com.mygdx.game.components;

import com.scs.awt.Edge;
import com.scs.awt.RectF;

public class PositionData {

	public RectF rect;
	public RectF prevPos;
	public Edge edge;
	
	public static PositionData ByCentre(float cx, float cy, float w, float h) {
		PositionData pos = new PositionData();
		pos.rect = new RectF(cx-(w/2), cy+(h/2), cx+(w/2), cy-(h/2));
		return pos;
		//pos = new RectF(cx, cy+h, cx+(w), cy);
	}


	public static PositionData ByBottomLeft(float cx, float cy, float w, float h) {
		PositionData pos = new PositionData();
		pos.rect = new RectF(cx, cy+h, cx+(w), cy);
		return pos;
	}

	
	public static PositionData FromEdge(float x1, float y1, float x2, float y2) {
		PositionData pos = new PositionData();
		pos.edge = new Edge(x1, y1, x2, y2);
		return pos;
	}

	
	private PositionData() {
		prevPos = new RectF();
	}

}
