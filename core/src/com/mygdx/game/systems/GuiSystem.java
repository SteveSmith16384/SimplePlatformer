package com.mygdx.game.systems;

import com.mygdx.game.Settings;
import com.mygdx.game.components.GuiContainerComponent;
import com.mygdx.game.components.PositionData;
import com.scs.awt.RectF;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class GuiSystem extends AbstractSystem {

	public GuiSystem(BasicECS ecs) {
		super(ecs);
	}

	/*
	public void repackIcons(AbstractEntity parent) {
		GuiContainerComponent cont = (GuiContainerComponent)parent.getComponent(GuiContainerComponent.class);
		PositionData parentPos = (PositionData)parent.getComponent(PositionData.class);
		int x = 0;
		for (AbstractEntity e : cont.entities) {
			PositionData pos = (PositionData)e.getComponent(PositionData.class);
			pos.rect = RectF.fromXYWH(parentPos.rect.left + x, parentPos.rect.bottom, Settings.DEFAULT_ICON_SIZE, Settings.DEFAULT_ICON_SIZE);
			x += Settings.DEFAULT_ICON_SIZE * 1.1f;
		}
	}
	*/
}
