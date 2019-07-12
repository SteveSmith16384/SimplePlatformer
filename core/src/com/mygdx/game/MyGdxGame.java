package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.helpers.AnimationFramesHelper;
import com.mygdx.game.models.GameData;
import com.mygdx.game.systems.AnimationCycleSystem;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.DrawingSystem;
import com.mygdx.game.systems.EnemyMobSystem;
import com.mygdx.game.systems.GuiSystem;
import com.mygdx.game.systems.InputSystem;
import com.mygdx.game.systems.MovementSystem;
import com.mygdx.game.systems.PlayerMovementSystem;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public final class MyGdxGame extends ApplicationAdapter implements InputProcessor {

	public OrthographicCamera camera;
	private Viewport viewport;

	private BasicECS ecs;

	private SpriteBatch batch;
	public BitmapFont font;
	public EntityFactory entityFactory;
	public GameData gameData;
	private boolean paused = false;
	private Music music;
	public SoundEffects sfx = new SoundEffects();
	private ShapeRenderer shapeRenderer;
	public AnimationFramesHelper animFrameHelper;

	private int gameStage = -1; // -1, -, or 1
	private boolean nextStage = false;
	public boolean toggleFullscreen = false, fullscreen = false;

	// Systems
	public InputSystem inputSystem;
	private DrawingSystem drawingSystem;
	public CollisionSystem collisionSystem;
	private MovementSystem movementSystem;
	private GuiSystem guiSystem;
	private AnimationCycleSystem animSystem;
	private EnemyMobSystem enemyMobSystem;
	private PlayerMovementSystem playerMovementSystem;

	public AbstractEntity playersAvatar;

	@Override
	public void create() {
		camera = new OrthographicCamera(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new StretchViewport(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS, camera);

		shapeRenderer = new ShapeRenderer();

		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		font = new BitmapFont();
		font.getData().setScale(3);
		
		ecs = new BasicECS();
		entityFactory = new EntityFactory(this, ecs);
		animFrameHelper = new AnimationFramesHelper();
		
		// Systems
		this.inputSystem = new InputSystem(this, ecs);
		drawingSystem = new DrawingSystem(this, ecs, batch);
		collisionSystem = new CollisionSystem(ecs);
		movementSystem = new MovementSystem(ecs);
		guiSystem = new GuiSystem(ecs);
		animSystem = new AnimationCycleSystem(ecs);
		enemyMobSystem = new EnemyMobSystem(ecs);
		this.playerMovementSystem = new PlayerMovementSystem(this, ecs);
		
		startPreGame();

		if (!Settings.RELEASE_MODE) {
			this.startGame();
		}
	}


	private void startPreGame() {
		this.removeAllEntities();

		/*music = Gdx.audio.newMusic(Gdx.files.internal("kevin-macleod-hall-of-the-mountain-king.ogg"));
		music.setLooping(true);
		music.setVolume(1f);
		music.play();*/
		
		
	}


	private void startPostGame() {
		this.removeAllEntities();
	}


	private void startGame() {
		if (music != null) {
			music.dispose();
			music = null;
		}

		gameData = new GameData();
		
		this.removeAllEntities();
		
		// Create entities for game
		this.playersAvatar = this.entityFactory.createPlayer(50, 50);
		ecs.addEntity(this.playersAvatar);
		
		AbstractEntity floor = this.entityFactory.createWall(20, 20, Settings.LOGICAL_WIDTH_PIXELS-50, 20);
		ecs.addEntity(floor);
	}
	

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
			
			// loop through systems
			this.inputSystem.process();
			this.playerMovementSystem.process();
			this.enemyMobSystem.process();
			this.movementSystem.process();
			this.animSystem.process();
			
			// Start actual drawing
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			camera.update();

			batch.begin();
			
			this.drawingSystem.process();
			
			this.drawFont(batch, "Creds: " + this.gameData.creds, 20, 40);

			batch.end();

			/*batch.begin();
			// Any line drawing?
			batch.end();*/
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


	private void drawFont(Batch batch, String text, float x, float y) {
		font.draw(batch, text, x, y);
	}


	@Override
	public void dispose() {
		removeAllEntities();

		shapeRenderer.dispose();
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.inputSystem.touchDown(screenX, screenY, pointer, button);
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
	public boolean mouseMoved(int screenX, int screenY) {
		this.inputSystem.mouseMoved(screenX, screenY);
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	
	public static final void p(String s) {
		System.out.println(s);
	}
	
}

