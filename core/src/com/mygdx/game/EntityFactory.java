package com.mygdx.game;

import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.GuiContainerComponent;
import com.mygdx.game.components.ImageData;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class EntityFactory {

	private BasicECS ecs;
	private MyGdxGame game;
	
	public EntityFactory(MyGdxGame _game, BasicECS _ecs) {
		game = _game;
		ecs = _ecs;
	}
	
	
	public AbstractEntity createPlayer(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Player");

		ImageData imageData = new ImageData("grey_box.png", Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		UserInputComponent uic = new UserInputComponent();
		e.addComponent(uic);
		JumpingComponent jc = new JumpingComponent();
		e.addComponent(jc);

		return e;
	}


	public AbstractEntity createWall(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity("Player");

		ImageData imageData = new ImageData("grey_box.png", w, h);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true);
		e.addComponent(cc);
		UserInputComponent uic = new UserInputComponent();
		e.addComponent(uic);

		return e;
	}


	public AbstractEntity createMob(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Mob");

		ImageData imageData = new ImageData("grey_box.png", Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent();
		e.addComponent(mob);
		return e;
	}


	public AbstractEntity createGuiMenu(float x, float y) {
		AbstractEntity e = new AbstractEntity("GuiContainer");
		
		GuiContainerComponent cont = new GuiContainerComponent();
		e.addComponent(cont);
		PositionData pos = PositionData.ByBottomLeft(x, y, 0, 0);
		e.addComponent(pos);

		return e;
	}
	
	
}