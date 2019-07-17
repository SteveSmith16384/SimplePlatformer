package com.mygdx.game;

import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.BlocksEndOfLevelComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.components.HarmedByMobComponent;
import com.mygdx.game.components.ImageData;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.KillByJumpingComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.mygdx.game.components.UserInputComponent;
import com.mygdx.game.helpers.AnimationFramesHelper;
import com.scs.basicecs.AbstractEntity;

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
		HarmedByMobComponent dbm = new HarmedByMobComponent();
		e.addComponent(dbm);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);

		return e;
	}


	public AbstractEntity createWall(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Wall");

		if (Settings.SHOW_GREY_BOXES) {
			ImageData imageData = new ImageData("grey_box.png", w, h);
			e.addComponent(imageData);
		}
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createHarmfulArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("HarmfulArea");

		if (Settings.SHOW_GREY_BOXES) {
			ImageData imageData = new ImageData("grey_box.png", w, h);
			e.addComponent(imageData);
		}
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		HarmOnContactComponent hoc = new HarmOnContactComponent();
		e.addComponent(hoc);

		return e;
	}


	public AbstractEntity createImage(String filename, int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Image_" + filename);

		ImageData imageData = new ImageData(filename, w, h);
		e.addComponent(imageData);
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}


	public AbstractEntity createLadderArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Ladder");

		if (Settings.SHOW_GREY_BOXES) {
			ImageData imageData = new ImageData("grey_box.png", w, h);
			e.addComponent(imageData);
		}
		PositionData pos = PositionData.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, true);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createFluidPlatform(int x, int y, float w) {
		AbstractEntity e = new AbstractEntity("FluidPlatform");

		if (Settings.SHOW_GREY_BOXES) {
			ImageData imageData = new ImageData("grey_box.png", w, 5);
			e.addComponent(imageData);
		}
		PositionData pos = PositionData.ByBottomLeft(x, y, w, 5);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createEdge(int x1, int y1, float x2, float y2) {
		AbstractEntity e = new AbstractEntity("Edge");

		PositionData pos = PositionData.FromEdge(x1, y1, x2, y2);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, false, true, false);
		e.addComponent(cc);

		return e;
	}


	public AbstractEntity createMob1(int cx, int cy) {
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
		BlocksEndOfLevelComponent beolc = new BlocksEndOfLevelComponent();
		e.addComponent(beolc);
		return e;
	}


	public AbstractEntity createCoin(int cx, int cy) {
		AbstractEntity e = new AbstractEntity("Coin");

		ImageData imageData = new ImageData("coin_01.png", Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(imageData);
		AnimationCycleComponent acc = AnimationFramesHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		PositionData pos = PositionData.ByCentre(cx, cy, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, false, false);
		e.addComponent(cc);
		CollectableComponent col = new CollectableComponent();
		e.addComponent(col);
		BlocksEndOfLevelComponent beolc = new BlocksEndOfLevelComponent();
		e.addComponent(beolc);
		return e;
	}

}