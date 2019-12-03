package com.mygdx.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

/**
 * This is the "gamepad controller" for the keyboard player.
 *
 */
public class DummyController implements Controller {

	public DummyController() {
	}

	@Override
	public boolean getButton(int buttonCode) {
		return false;
	}

	@Override
	public float getAxis(int axisCode) {
		return 0;
	}

	@Override
	public PovDirection getPov(int povCode) {
		return null;
	}

	@Override
	public boolean getSliderX(int sliderCode) {
		return false;
	}

	@Override
	public boolean getSliderY(int sliderCode) {
		return false;
	}

	@Override
	public Vector3 getAccelerometer(int accelerometerCode) {
		return null;
	}

	@Override
	public void setAccelerometerSensitivity(float sensitivity) {
		
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void addListener(ControllerListener listener) {
		
	}

	@Override
	public void removeListener(ControllerListener listener) {
		
	}

}
