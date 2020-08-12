package com.scs.libgdx;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.PostProcessing;
import com.mygdx.game.Settings;
import com.mygdx.game.SoundEffects;

public abstract class GenericGame extends ApplicationAdapter implements InputProcessor, ControllerListener {

	public OrthographicCamera camera;
	private Viewport viewport;
	private Music music;
	public SoundEffects sfx = new SoundEffects();
	protected SpriteBatch batch;
	protected List<Controller> controllersAdded = new ArrayList<Controller>();
	protected List<Controller> controllersRemoved = new ArrayList<Controller>();

	protected boolean paused = false;
	public boolean toggleFullscreen = true, currently_fullscreen = false;

	protected PostProcessing post;

	public GenericGame(boolean set_fullscreen) {
		currently_fullscreen = !set_fullscreen;
	}
	
	
	@Override
	public void create() {
		camera = new OrthographicCamera(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		viewport = new StretchViewport(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS, camera);

		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

		Controllers.addListener(this);

		if (Gdx.app.getType() != ApplicationType.WebGL) {
			post = new PostProcessing();
		}
	}


	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
			return;
		}
		
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			post.update(Gdx.graphics.getDeltaTime());
		}

		if (this.toggleFullscreen) {
			this.toggleFullscreen = false;
			if (currently_fullscreen) {
				Gdx.graphics.setWindowedMode(Settings.WINDOW_WIDTH_PIXELS, Settings.WINDOW_HEIGHT_PIXELS);
				batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
				currently_fullscreen = false;
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
				currently_fullscreen = true;
			}
		}
	}


	@Override
	public void dispose() {
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			post.dispose();
		}
		batch.dispose();
		this.sfx.dispose();
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


	protected void playMusic(String filename) {
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


	public static final void p(String s) {
		System.out.println(s);
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


}

