package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.helpers.AnimationFramesHelper;
import com.mygdx.game.models.GameData;
import com.mygdx.game.models.PlayerData;
import com.mygdx.game.systems.AnimationCycleSystem;
import com.mygdx.game.systems.CollectorSystem;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.DrawInGameGuiSystem;
import com.mygdx.game.systems.DrawPostGameGuiSystem;
import com.mygdx.game.systems.DrawPreGameGuiSystem;
import com.mygdx.game.systems.DrawingSystem;
import com.mygdx.game.systems.InputSystem;
import com.mygdx.game.systems.MobAISystem;
import com.mygdx.game.systems.MoveToOffScreenSystem;
import com.mygdx.game.systems.MovementSystem;
import com.mygdx.game.systems.MovingPlatformSystem;
import com.mygdx.game.systems.PlayerMovementSystem;
import com.mygdx.game.systems.ProcessCollisionSystem;
import com.mygdx.game.systems.ProcessPlayersSystem;
import com.mygdx.game.systems.ScrollPlayAreaSystem;
import com.mygdx.game.systems.WalkingAnimationSystem;
import com.scs.basicecs.BasicECS;

public final class MyGdxGame extends ApplicationAdapter implements InputProcessor, ControllerListener {

	public BasicECS ecs;

	public OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	public BitmapFont font;
	public EntityFactory entityFactory;
	public GameData gameData;
	private boolean paused = false;
	private Music music;
	public SoundEffects sfx = new SoundEffects();
	public AnimationFramesHelper animFrameHelper;
	public LevelGenerator lvl;
	public int winnerImageId;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;
	public boolean toggleFullscreen = false, fullscreen = false;

	// Systems
	public InputSystem inputSystem;
	private DrawingSystem drawingSystem;
	public CollisionSystem collisionSystem;
	private MovementSystem movementSystem;
	private AnimationCycleSystem animSystem;
	private MobAISystem mobAiSystem;
	private PlayerMovementSystem playerMovementSystem;
	public ProcessCollisionSystem processCollisionSystem;
	public CollectorSystem collectorSystem;
	private WalkingAnimationSystem walkingAnimationSystem;
	//private MovingPlatformSystem movingPlatformSystem;
	private MoveToOffScreenSystem moveToOffScreenSystem;
	private DrawInGameGuiSystem drawInGameGuiSystem;
	private ProcessPlayersSystem processPlayersSystem;
	private ScrollPlayAreaSystem scrollPlayAreaSystem;
	private DrawPreGameGuiSystem drawPreGameGuiSystem;
	private DrawPostGameGuiSystem drawPostGameGuiSystem;

	public ArrayList<PlayerData> players = new ArrayList<PlayerData>();
	private List<Controller> controllersAdded = new ArrayList<Controller>();
	private List<Controller> controllersRemoved = new ArrayList<Controller>();


	@Override
	public void create() {
		camera = new OrthographicCamera(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new StretchViewport(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS, camera);

		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		font = new BitmapFont();
		font.getData().setScale(3);

		ecs = new BasicECS();
		entityFactory = new EntityFactory(this);
		animFrameHelper = new AnimationFramesHelper();

		// Systems
		this.inputSystem = new InputSystem(this, ecs);
		drawingSystem = new DrawingSystem(ecs, batch);
		collisionSystem = new CollisionSystem(ecs);
		movementSystem = new MovementSystem(this, ecs);
		animSystem = new AnimationCycleSystem(ecs);
		mobAiSystem = new MobAISystem(this, ecs);
		this.playerMovementSystem = new PlayerMovementSystem(this, ecs);
		processCollisionSystem = new ProcessCollisionSystem(this, ecs);
		this.collectorSystem = new CollectorSystem(this);
		this.walkingAnimationSystem = new WalkingAnimationSystem(ecs);
		//this.movingPlatformSystem = new MovingPlatformSystem(ecs);
		this.moveToOffScreenSystem = new MoveToOffScreenSystem(ecs);
		this.drawInGameGuiSystem = new DrawInGameGuiSystem(this, batch);
		this.processPlayersSystem = new ProcessPlayersSystem(this);
		this.scrollPlayAreaSystem = new ScrollPlayAreaSystem(this, ecs);
		this.drawPreGameGuiSystem = new DrawPreGameGuiSystem(this, batch);
		this.drawPostGameGuiSystem = new DrawPostGameGuiSystem(this, batch);

		// Add players for all connected controllers
		for (Controller controller : Controllers.getControllers()) {
			this.addPlayerForController(controller);
		}
		players.add(new PlayerData(null)); // Create keyboard player by default (they might not actually join though!)

		Controllers.addListener(this);

		lvl = new LevelGenerator(this.entityFactory, ecs);

		startPreGame();

		if (!Settings.RELEASE_MODE) {
			this.nextStage = true;
		}
	}


	private void addPlayerForController(Controller controller) {
		boolean playerFound = false;
		for (PlayerData player : players) {
			if (player.controller == controller) {
				playerFound = true;
				break;
			}
		}
		if (!playerFound) {
			//createPlayer(controller);
			PlayerData data = new PlayerData(controller);
			this.players.add(data);

			p("player created");
		}
	}


	/*	private void createPlayer(Controller controller) {
		PlayerData data = new PlayerData(controller);
		this.players.add(data);

		p("player created");
	}
	 */

	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("IntroLoop.wav");

		this.removeAllEntities();

		// Reset all player data
		for (PlayerData player : players) {
			player.setInGame(false);
		}
	}


	private void playMusic(String filename) {
		if (music != null) {
			music.dispose();
			music = null;
		}

		try {
			music = Gdx.audio.newMusic(Gdx.files.internal(filename));
			music.setLooping(true);
			music.setVolume(1f);
			music.play();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void startPostGame() {
		this.removeAllEntities();

		this.playMusic("VictoryMusic.wav");
	}


	public void setWinner(int imageId) {
		this.nextStage = true;
		this.winnerImageId = imageId;
	}


	private void startGame() {
		this.playMusic("8BitMetal.wav");

		if (!Settings.RELEASE_MODE) {
			if (this.players.size() > 0) {
				if (this.players.get(0).isInGame() == false) {
					this.players.get(0).setInGame(true); // Auto-add keyboard player
				}
			}
		}

		gameData = new GameData();

		this.removeAllEntities();

		lvl.createLevel1();
	}

	/*
	public void createPlayersAvatar(PlayerData player, Controller controller, LevelGenerator lvl) {
		int xPos = NumberFunctions.rnd(50,  Settings.LOGICAL_WIDTH_PIXELS-50);
		AbstractEntity avatar = this.entityFactory.createPlayersAvatar(player, controller, xPos, Settings.LOGICAL_HEIGHT_PIXELS);
		ecs.addEntity(avatar);

		player.avatar = avatar;
	}
	 */


	@Override
	public void render() {
		if (!paused) {
			if (nextStage) {
				nextStage = false;
				if (this.gameStage == -1) {
					this.gameStage = 0;
					this.startGame();
				} else if (this.gameStage == 0) {
					this.gameStage = 1;
					startPostGame();
				} else if (this.gameStage == 1) {
					this.gameStage = -1;
					startPreGame();
				}
			}

			ecs.process();

			checkNewOrRemovedControllers();

			this.inputSystem.process();

			if (this.gameStage == 0) {
				// loop through systems
				this.processPlayersSystem.process();
				this.moveToOffScreenSystem.process();
				this.playerMovementSystem.process();
				this.mobAiSystem.process();
				this.scrollPlayAreaSystem.process();
				this.walkingAnimationSystem.process(); // Must be before the movementsystem, as that clears the direction
				this.movementSystem.process();
				//this.movingPlatformSystem.process();
				this.animSystem.process();			
			}

			// Start actual drawing
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.setProjectionMatrix(camera.combined);
			camera.update();

			batch.begin();
			this.drawingSystem.process();
			if (this.gameStage == -1) {
				this.drawPreGameGuiSystem.process();
			} else if (this.gameStage == 0) {
				this.drawInGameGuiSystem.process();
			} else if (this.gameStage == 1) {
				this.drawPostGameGuiSystem.process();
				this.drawInGameGuiSystem.process();
			}
			batch.end();

			if (Settings.SHOW_OUTLINE) {
				batch.begin();
				this.drawingSystem.drawDebug(batch);
				batch.end();
			}
		}

		if (this.toggleFullscreen) {
			this.toggleFullscreen = false;
			if (fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				fullscreen = false;
			} else {
				DisplayMode m = null;
				for(DisplayMode mode: Gdx.graphics.getDisplayModes()) {
					if (m == null) {
						m = mode;
					} else {
						if (m.width < mode.width) {
							m = mode;
						}
					}
				}

				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				fullscreen = true;
			}
		}

	}


	private void checkNewOrRemovedControllers() {
		for (Controller c : this.controllersAdded) {
			this.addPlayerForController(c);
		}
		this.controllersAdded.clear();

		for (Controller c : this.controllersRemoved) {
			for (PlayerData player : players) {
				if (player.controller == c) {
					player.avatar.remove();
					player.quit = true;
					break;
				}
			}
		}
		this.controllersAdded.clear();
	}


	public void drawFont(Batch batch, String text, float x, float y) {
		font.draw(batch, text, x, y);
	}


	public void endOfLevel() {
		//p("End of level!");
	}


	@Override
	public void dispose() {
		removeAllEntities();

		batch.dispose();
		if (font != null) {
			font.dispose();
		}
		this.drawingSystem.dispose();
		this.animFrameHelper.dispose();
		this.sfx.dispose();
	}


	private void removeAllEntities() {
		this.ecs.removeAllEntities();
	}


	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();

		camera.viewportWidth = Settings.LOGICAL_WIDTH_PIXELS;
		camera.viewportHeight = Settings.LOGICAL_HEIGHT_PIXELS;
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

	}


	@Override
	public void pause() {
		p("Game paused");
		if (Settings.RELEASE_MODE) {
			paused = true;
		}
	}


	@Override
	public void resume() {
		p("Game resumed");
		paused = false;
	}


	//#############################


	@Override
	public boolean keyDown(int keycode) {
		this.inputSystem.keyDown(keycode);
		return true;
	}


	@Override
	public boolean keyUp(int keycode) {
		this.inputSystem.keyUp(keycode);
		return true;
	}


	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		return false;
	}


	public static final void p(String s) {
		System.out.println(s);
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}


	@Override
	public void connected(Controller controller) {
		this.controllersAdded.add(controller);
	}


	@Override
	public void disconnected(Controller controller) {
		this.controllersRemoved.add(controller);
	}


	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		return false;
	}


	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		return false;
	}


	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		return false;
	}


	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		return false;
	}


	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}


	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		return false;
	}


	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		return false;
	}

}

