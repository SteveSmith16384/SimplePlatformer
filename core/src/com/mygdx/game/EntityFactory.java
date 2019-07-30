package com.mygdx.game;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.CollectableComponent.Type;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.KillByJumpingComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MoveOffScreenComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PreventsEndOfLevelComponent;
import com.mygdx.game.components.ScrollsAroundComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.mygdx.game.helpers.AnimationFramesHelper;
import com.mygdx.game.models.PlayerData;
import com.scs.awt.Rect;
import com.scs.basicecs.AbstractEntity;
import com.scs.libgdx.Ninepatch;

public class EntityFactory {
	
	private MyGdxGame game;
	
	public EntityFactory(MyGdxGame _game) {
		game = _game;
	}
	

	public AbstractEntity createPlayersAvatar(PlayerData player, Controller controller, int x, int y) {
		AbstractEntity e = new AbstractEntity("Player");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		PlayersAvatarComponent uic = new PlayersAvatarComponent(player, controller);
		e.addComponent(uic);
		JumpingComponent jc = new JumpingComponent();
		e.addComponent(jc);
		KillByJumpingComponent kbj = new KillByJumpingComponent();
		e.addComponent(kbj);
		CanCollectComponent ccc = new CanCollectComponent();
		e.addComponent(ccc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);

		game.animFrameHelper.createPlayersFrames(e, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createWall(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Wall");

		//if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		//}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createHarmfulArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("HarmfulArea");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, false);
		e.addComponent(cc);
		HarmOnContactComponent hoc = new HarmOnContactComponent();
		e.addComponent(hoc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);
		return e;
	}


	public AbstractEntity createTestImage(int x, int y, int w, int h, int zOrder) {
		AbstractEntity e = new AbstractEntity("TestImage");
		
		//Texture tex = new Texture("grey_box.png");
		Ninepatch np = new Ninepatch(null, null);
		Sprite sprite = np.getImage(w, h);
		sprite.setSize(w, h);

		ImageComponent imageData = new ImageComponent(sprite, zOrder);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}


	public AbstractEntity createImage(String filename, int x, int y, float w, float h, int zOrder) {
		AbstractEntity e = new AbstractEntity("Image_" + filename);

		ImageComponent imageData = new ImageComponent(filename, zOrder, w, h);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);

		return e;
	}


	public AbstractEntity createLadderArea(int x, int y, float w, float h) {
		AbstractEntity e = new AbstractEntity("Ladder");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, h);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, true, true);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createFluidPlatform(int x, int y, float w) {
		AbstractEntity e = new AbstractEntity("FluidPlatform");

		if (Settings.SHOW_GREY_BOXES) {
			ImageComponent imageData = new ImageComponent("grey_box.png", 0, w, Settings.PLAYER_SIZE);
			e.addComponent(imageData);
		}
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createPlatformType1(int x, int y, int w, int h) {
		AbstractEntity e = new AbstractEntity("PlatformImage1");

		Ninepatch ninepatch = new Ninepatch("platform1.png", new Rect(1, 8, 1, 3));
		
		ImageComponent imageData = new ImageComponent(ninepatch.getImage(w, h), -1);
		e.addComponent(imageData);
		
		PositionComponent pos = PositionComponent.ByTopLeft(x, y, w, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, true, false, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createEdge(int x1, int y1, float x2, float y2) {
		AbstractEntity e = new AbstractEntity("Edge");

		PositionComponent pos = PositionComponent.FromEdge(x1, y1, x2, y2);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(false, false, true, false);
		e.addComponent(cc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(true);
		e.addComponent(mdc);

		return e;
	}


	public AbstractEntity createMob1(int x, int y) {
		AbstractEntity e = new AbstractEntity("Mob");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, true, false, false); // Needs collideAsPlatform to be killed
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent();
		e.addComponent(mob);
		PreventsEndOfLevelComponent beolc = new PreventsEndOfLevelComponent();
		e.addComponent(beolc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.2f);
		e.addComponent(wac);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);

		game.animFrameHelper.createMob1Frames(e, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createMob_Cannonball(int x, int y) {
		AbstractEntity e = new AbstractEntity("Cannonball");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, true, false, false); // Needs collideAsPlatform to be killed
		e.addComponent(cc);
		MovementComponent mc = new MovementComponent(true);
		e.addComponent(mc);
		MobComponent mob = new MobComponent();
		e.addComponent(mob);
		//PreventsEndOfLevelComponent beolc = new PreventsEndOfLevelComponent();
		//e.addComponent(beolc);
		WalkingAnimationComponent wac = new WalkingAnimationComponent(.1f);
		e.addComponent(wac);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);

		game.animFrameHelper.createCannonballFrames(e, Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
		return e;
	}


	public AbstractEntity createFallingMob(AbstractEntity mob) {
		PositionComponent pos = (PositionComponent)mob.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)mob.getComponent(ImageComponent.class);	

		return this.createFallingGraphic(mob.toString(), pos.rect.left, pos.rect.bottom, img.sprite, pos.rect.width(), pos.rect.height());
	}


	public AbstractEntity createFallingGraphic(String name, float x, float y, Sprite image, float w, float h) {
		AbstractEntity e = new AbstractEntity("Falling" + name);

		ImageComponent imageData = new ImageComponent(image, 1);
		e.addComponent(imageData);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, w, h);
		e.addComponent(pos);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(0, -Settings.PLAYER_SPEED*5);
		e.addComponent(moc);
		return e;
	}


	public AbstractEntity createCoin(int x, int y) {
		AbstractEntity e = new AbstractEntity("Coin");

		ImageComponent imageData = new ImageComponent("grey_box.png", 1, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(imageData);
		AnimationCycleComponent acc = game.animFrameHelper.generateForCoin(Settings.COLLECTABLE_SIZE);
		e.addComponent(acc);
		PositionComponent pos = PositionComponent.ByBottomLeft(x, y, Settings.COLLECTABLE_SIZE, Settings.COLLECTABLE_SIZE);
		e.addComponent(pos);
		CollisionComponent cc = new CollisionComponent(true, false, false, false);
		e.addComponent(cc);
		CollectableComponent col = new CollectableComponent(Type.Coin);
		e.addComponent(col);
		PreventsEndOfLevelComponent beolc = new PreventsEndOfLevelComponent();
		e.addComponent(beolc);
		ScrollsAroundComponent mdc = new ScrollsAroundComponent(false);
		e.addComponent(mdc);
		return e;
	}


	public AbstractEntity createRisingCoin(AbstractEntity coin) {
		PositionComponent pos = (PositionComponent)coin.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)coin.getComponent(ImageComponent.class);
		
		AbstractEntity e = new AbstractEntity("RisingCoin");

		ImageComponent imageData;
		if (img.sprite != null) {
			imageData = new ImageComponent(img.sprite, 1);
		} else {
			imageData = new ImageComponent(img.imageFilename, 1, img.w, img.h);
		}
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByBottomLeft(pos.rect.left, pos.rect.bottom, pos.rect.width(), pos.rect.height());
		e.addComponent(pos2);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(Settings.PLAYER_SPEED*2, Settings.PLAYER_SPEED*2);
		e.addComponent(moc);

		return e;
	}	


	public AbstractEntity createDeadPlayer(AbstractEntity player) {
		PositionComponent pos = (PositionComponent)player.getComponent(PositionComponent.class);
		ImageComponent img = (ImageComponent)player.getComponent(ImageComponent.class);
		
		AbstractEntity e = new AbstractEntity("DeadPlayer");

		ImageComponent imageData = new ImageComponent(img.sprite, 1);
		e.addComponent(imageData);
		PositionComponent pos2 = PositionComponent.ByBottomLeft(pos.rect.left, pos.rect.bottom, pos.rect.width(), pos.rect.height());
		e.addComponent(pos2);
		MoveOffScreenComponent moc = new MoveOffScreenComponent(0, -Settings.PLAYER_SPEED*5);
		e.addComponent(moc);

		return e;
	}	


}
