package com.mygdx.game;

public final class Settings {
	
	public static final String VERSION = "0.01";
	public static final boolean RELEASE_MODE = false;
	
	// Debug
	public static final boolean SHOW_GREY_BOXES = !RELEASE_MODE && false;
	public static final boolean USE_SIMPLE_ECS = !RELEASE_MODE && false;
	public static final boolean TURN_OFF_GRAVITY = !RELEASE_MODE && false;
	public static final boolean SHOW_OUTLINE = !RELEASE_MODE && false;

	// Settings
	public static final float PLAYER_SPEED = 100;//50;
	public static final float MOB_SPEED = 45;
	public static final float MAX_MOVEMENT = 20;//50;//150; // After adjusting for FPS
	public static final int PLATFORM_START_HEIGHT = 200; // From the top
	public static final int PLATFORM_SPACING = 100; // From the top

	public static final float PLAYER_SIZE = 30;
	public static final float COLLECTABLE_SIZE = 25;
	public static final float JUMP_FORCE = 300;
	
	public static final float GRAVITY = 7;
	
	public static final float SQ_SIZE = 40;
	
	public static final int WINDOW_WIDTH_PIXELS = RELEASE_MODE ? 1024 : 512;
	public static final int WINDOW_HEIGHT_PIXELS = (int)(WINDOW_WIDTH_PIXELS * .68);

	public static final int LOGICAL_WIDTH_PIXELS = 800;
	public static final int LOGICAL_HEIGHT_PIXELS = (int)(LOGICAL_WIDTH_PIXELS * .68);
	
	public static final String TITLE = RELEASE_MODE ? "Co-op Platformer" : "CP";
	
	private Settings() {
	}

}
