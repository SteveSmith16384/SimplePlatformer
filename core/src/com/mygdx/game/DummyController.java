package com.mygdx.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class DummyController implements Controller {

	public DummyController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean getButton(int buttonCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getAxis(int axisCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PovDirection getPov(int povCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSliderX(int sliderCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getSliderY(int sliderCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector3 getAccelerometer(int accelerometerCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccelerometerSensitivity(float sensitivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(ControllerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ControllerListener listener) {
		// TODO Auto-generated method stub
		
	}

}
