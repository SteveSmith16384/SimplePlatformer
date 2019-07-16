package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.models.LevelData;

public class MapLoader {

	public static final int SqType_InvSolid = 1;
	
	public LevelData loadMapViaGdx(String filename) {
		//Gdx.app.log("MyTag", "Loading " + filename);

		FileHandle file = Gdx.files.internal(filename);
		String lines = file.readString();

		return this.readData(lines);
	}


	private LevelData readData(String lines) {
		LevelData levelData = new LevelData();
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
					levelData.mapData = new int[levelData.mapGridSize.x][levelData.mapGridSize.y];
					currentRow = levelData.mapGridSize.y-1;
					//Settings.SQ_SIZE = (float)Settings.LOGICAL_WIDTH_PIXELS / (float)levelData.mapGridSize.x;// levelData.backgroundImageWidth.x / levelData.mapGridSize.x;
				} else if (token[0].equalsIgnoreCase("row")) {
					if (currentRow >= 0) {
						//Settings.p("Adding row " + currentRow + ":" + line);
						for (int x=0 ; x<levelData.mapGridSize.x ; x++) {
							try {
								levelData.mapData[x][currentRow] = Integer.parseInt(token[x+1]);
							} catch (ArrayIndexOutOfBoundsException ex) {
								ex.printStackTrace();
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


	public void decodeMapId(MyGdxGame game, int mapid, int code, int mx, int my) {
		float px = mx * Settings.SQ_SIZE;
		float py = my * Settings.SQ_SIZE;

		switch (code) {
		case SqType_InvSolid:
			//game.map.setMapSquare(new InvisibleBlockingSquare(game, px, py), mx, my);
			break;


		default:
			throw new RuntimeException("Unknown square code: " + code);
		}
	}


}
