package com.mygdx.game.models;

import java.util.LinkedList;
import java.util.List;

import com.scs.awt.Point;
import com.scs.basicecs.AbstractEntity;

public class LevelData {

	public int id;
	public String title;
	public Point mapGridSize = new Point();
	public List<AbstractEntity> entities = new LinkedList<AbstractEntity>(); 
	
}
