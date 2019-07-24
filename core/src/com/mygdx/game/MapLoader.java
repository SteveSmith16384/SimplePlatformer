package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.models.LevelData;
import com.scs.basicecs.AbstractEntity;

public class MapLoader {

	public LevelData levelData;
	private EntityFactory entityFactory;

	public MapLoader() {
		this.entityFactory = new EntityFactory();
	}


	public LevelData loadMapViaGdx(String filename) {
		//Gdx.app.log("MyTag", "Loading " + filename);

		FileHandle file = Gdx.files.internal(filename);
		String lines = file.readString();

		return this.readData(lines);
	}


	private LevelData readData(String lines) {
		levelData = new LevelData();
		int currentRow = 0;
		for(String line : lines.split("\\r?\\n")) {
			//Gdx.app.log("MyTag", "Read " + line);
			if (line.length() > 0 && line.startsWith("#") == false) {
				String token[] = line.split(",");
				if (token[0].equalsIgnoreCase("map_id")) {
					levelData.id = Integer.parseInt(token[1]);
				} else if (token[0].equalsIgnoreCase("title")) {
					levelData.title = token[1];
				} else if (token[0].equalsIgnoreCase("map_size")) {
					levelData.mapGridSize.x = Integer.parseInt(token[1]);
					levelData.mapGridSize.y = Integer.parseInt(token[2]);
					//levelData.mapData = new int[levelData.mapGridSize.x][levelData.mapGridSize.y];
					currentRow = levelData.mapGridSize.y-1;
					//Settings.SQ_SIZE = (float)Settings.LOGICAL_WIDTH_PIXELS / (float)levelData.mapGridSize.x;// levelData.backgroundImageWidth.x / levelData.mapGridSize.x;
				} else if (token[0].equalsIgnoreCase("row")) {
					if (currentRow >= 0) {
						//Settings.p("Adding row " + currentRow + ":" + line);
						for (int x=1 ; x<token.length ; x++) {
							String subtoken[] = token[x].split("~");
							for (int j=0 ; j<subtoken.length ; j++) {
								parseSubtoken(j, currentRow, subtoken[j]);
							}							
						}
						currentRow--;
					} else {
						MyGdxGame.p("Ignoring row:" + line);
					}
				} else {
					MyGdxGame.p("Row " + line);
					throw new RuntimeException("Unknown token: " + token[0]);
				}
			}
		}
		return levelData;
	}


	private void parseSubtoken(int mx, int my, String subtoken) {
		if (subtoken.length() > 0) {
		int px = (int)(mx * Settings.SQ_SIZE);
		int py = (int)(my * Settings.SQ_SIZE);

		String sub[] = subtoken.split("\\|");

		if (subtoken.startsWith("solid|")) {
			AbstractEntity e = this.entityFactory.createWall(px, py, Integer.parseInt(sub[1]) * Settings.SQ_SIZE, Integer.parseInt(sub[2]) * Settings.SQ_SIZE);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("fluid|")) {
			AbstractEntity e = this.entityFactory.createFluidPlatform(px, py, Integer.parseInt(sub[1]) * Settings.SQ_SIZE);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("ladder|")) {
			AbstractEntity e = this.entityFactory.createLadderArea(px, py, Integer.parseInt(sub[1]) * Settings.SQ_SIZE, Integer.parseInt(sub[2]) * Settings.SQ_SIZE);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("edge|")) {
			AbstractEntity e = this.entityFactory.createEdge(px, py, Integer.parseInt(sub[1]) * Settings.SQ_SIZE, Integer.parseInt(sub[2]) * Settings.SQ_SIZE);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("deadly|")) {
			//todo AbstractEntity e = this.entityFactory.cr.createLadderArea(px, py, Integer.parseInt(sub[1]), Integer.parseInt(sub[2]));
			//this.levelData.entities.add(e);
		} else if (subtoken.startsWith("mob1|")) {
			AbstractEntity e = this.entityFactory.createMob1(px, py);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("coin|")) {
			AbstractEntity e = this.entityFactory.createCoin(px, py);
			this.levelData.entities.add(e);
		} else if (subtoken.startsWith("image|")) {
			AbstractEntity e = this.entityFactory.createImage(sub[1], px, py, Integer.parseInt(sub[2]) * Settings.SQ_SIZE, Integer.parseInt(sub[3]) * Settings.SQ_SIZE, Integer.parseInt(sub[4]));
			this.levelData.entities.add(e);
		} else {
			throw new RuntimeException("Unknown token: " + subtoken);
		}
	}
	}


}
