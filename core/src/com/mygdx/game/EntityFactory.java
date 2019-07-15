package com.mygdx.game;

import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.DestroyedByMobComponent;
import com.mygdx.game.components.GuiContainerComponent;
import com.mygdx.game.components.ImageData;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.KillByJumpingComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class EntityFactory {

	/*private BasicECS ecs;
	private MyGdxGame game;
	
	public EntityFactory(MyGdxGame _game, BasicECS _ecs) {
		game = _game;
		ecs = _ecs;
	}*/
	
	
	public AbstractEntity createPlayer(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Player");

		ImageData imageData = new ImageData("grey_box.png", Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		UserInputComponent uic = new UserInputComponent();
		e.addComponent(uic);
		JumpingComponent jc = new JumpingComponent();
		e.addComponent(jc);
		KillByJumpingComponent kbj = new KillByJumpingComponent();
		e.addComponent(kbj);
		DestroyedByMobComponent dbm = new DestroyedByMobComponent();
		e.addComponent(dbm);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);
		
		return e;
	}


	public AbstractEntity createWall(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity("Wall");

		ImageData imageData = new ImageData("grey_box.png", w, h);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createLadderArea(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity("Ladder");

		ImageData imageData = new ImageData("grey_box.png", w, h);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, true);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createFluidPlatform(int x, int y, int w) {
		AbstractEntity e = new AbstractEntity("FluidPlatform");

		ImageData imageData = new ImageData("grey_box.png", w, 5);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByBottomLeft(x, y, w, 5);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createEdge(int x1, int y1, int x2, int y2) {
		AbstractEntity e = new AbstractEntity("Edge");

		//ImageData imageData = new ImageData("grey_box.png", w, 5);
		//e.addComponent(imageData);
		PositionData pos = PositionData.FromEdge(x1, y1, x2, y2);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, false, true, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createMob(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Mob");

		ImageData imageData = new ImageData("grey_box.png", Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, true, false, false); // Needs collideAsPlatform to be killed
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent();
		e.addComponent(mob);
		return e;
	}


	public AbstractEntity createCollectable(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Collectable");

		ImageData imageData = new ImageData("grey_box.png", Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, false, false);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		CollectableComponent col = new CollectableComponent();
		e.addComponent(col);
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