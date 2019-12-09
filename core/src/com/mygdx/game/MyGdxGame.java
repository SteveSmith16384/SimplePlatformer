package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.mygdx.game.systems.PlayerMovementSystem;
import com.mygdx.game.systems.ProcessCollisionSystem;
import com.mygdx.game.systems.ProcessPlayersSystem;
import com.mygdx.game.systems.ScrollPlayAreaSystem;
import com.mygdx.game.systems.WalkingAnimationSystem;
import com.scs.basicecs.BasicECS;
import com.scs.libgdx.GenericGame;

public final class MyGdxGame extends GenericGame implements InputProcessor, ControllerListener {

	public BasicECS ecs;

	public BitmapFont font;
	public EntityFactory entityFactory;
	public GameData gameData;
	public AnimationFramesHelper animFrameHelper;
	public LevelGenerator levelGenerator;
	public int winnerImageId;
	public int gameStage = -1; // -1, 0, or 1 for before, during and after game
	private boolean nextStage = false;

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
	private MoveToOffScreenSystem moveToOffScreenSystem;
	private DrawInGameGuiSystem drawInGameGuiSystem;
	private ProcessPlayersSystem processPlayersSystem;
	private ScrollPlayAreaSystem scrollPlayAreaSystem;
	private DrawPreGameGuiSystem drawPreGameGuiSystem;
	private DrawPostGameGuiSystem drawPostGameGuiSystem;

	public HashMap<Controller, PlayerData> players = new HashMap<Controller, PlayerData>();
	public DummyController dummyController = new DummyController();

	@Override
	public void create() {
		super.create();
		
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
		players.put(dummyController, new PlayerData(null)); // Create keyboard player by default (they might not actually join though!)

		levelGenerator = new LevelGenerator(this.entityFactory, ecs);

		startPreGame();

		if (!Settings.RELEASE_MODE) {
			this.nextStage = true; // Auto-start game
		}
	}


	private void addPlayerForController(Controller controller) {
		if (this.players.containsKey(controller) == false) {
			PlayerData data = new PlayerData(controller);
			this.players.put(controller, data);
			//p("player created");
		}
	}


	public void startNextStage() {
		this.nextStage = true;
	}


	private void startPreGame() {
		this.playMusic("IntroLoop.wav");

		this.removeAllEntities();

		// Reset all player data
		for (PlayerData player : players.values()) {
			player.setInGame(false);
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

		/*if (!Settings.RELEASE_MODE) {
			if (this.players.size() > 0) {
				if (this.players.get(0).isInGame() == false) {
					this.players.get(0).setInGame(true); // Auto-add keyboard player
				}
			}
		}*/

		gameData = new GameData();

		this.removeAllEntities();

		levelGenerator.createLevel1();
	}


	private int getNumPlayersInGame() {
		int count = 0;
		for (PlayerData player : players.values()) {
			if (player.isInGame()) {
				count++;
			}
		}
		return count;
	}


	@Override
	public void render() {
		super.render();
		
		if (!paused) {
			if (nextStage) {
				nextStage = false;
				if (this.gameStage == -1 && this.getNumPlayersInGame() > 0) {
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

			ecs.addAndRemoveEntities();
			//ecs.processAllSystems();

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
				this.animSystem.process();			
			}

			// Start actual drawing
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batch.setProjectionMatrix(camera.combined);
			camera.update();

			batch.begin();
			if (Gdx.app.getType() != ApplicationType.WebGL) {
				post.begin();
			}
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
			if (Gdx.app.getType() != ApplicationType.WebGL) {
				post.end();
			}

			if (Settings.SHOW_OUTLINE) {
				batch.begin();
				this.drawingSystem.drawDebug(batch);
				batch.end();
			}
		}
	}


	private void checkNewOrRemovedControllers() {
		for (Controller c : this.controllersAdded) {
			this.addPlayerForController(c);
		}
		this.controllersAdded.clear();

		for (Controller c : this.controllersRemoved) {
			for (PlayerData player : players.values()) {
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
		super.dispose();
		
		removeAllEntities();

		if (font != null) {
			font.dispose();
		}
		this.drawingSystem.dispose();
		this.animFrameHelper.dispose();
	}


	private void removeAllEntities() {
		this.ecs.removeAllEntities();
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
	public boolean buttonDown(Controller controller, int buttonCode) {
		this.inputSystem.buttonDown(controller, buttonCode);
		return false;
	}


	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		this.inputSystem.buttonUp(controller, buttonCode);
		return false;
	}


	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		this.inputSystem.axisMoved(controller, axisCode, value);
		return false;
	}


}

