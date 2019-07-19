package com.mygdx.game.components;

import com.scs.awt.Edge;
import com.scs.awt.RectF;

public class PositionComponent {

	public RectF rect;
	public RectF prevPos;
	public Edge edge;
	
	public static PositionComponent ByCentre(float cx, float cy, float w, float h) {
		PositionComponent pos = new PositionComponent();
		pos.rect = new RectF(cx-(w/2), cy+(h/2), cx+(w/2), cy-(h/2));
		return pos;
		//pos = new RectF(cx, cy+h, cx+(w), cy);
	}


	public static PositionComponent ByBottomLeft(float cx, float cy, float w, float h) {
		PositionComponent pos = new PositionComponent();
		pos.rect = new RectF(cx, cy+h, cx+(w), cy);
		return pos;
	}

	
	public static PositionComponent FromEdge(float x1, float y1, float x2, float y2) {
		PositionComponent pos = new PositionComponent();
		pos.edge = new Edge(x1, y1, x2, y2);
		return pos;
	}

	
	private PositionComponent() {
		prevPos = new RectF();
	}

}
