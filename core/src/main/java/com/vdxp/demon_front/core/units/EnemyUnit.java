package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class EnemyUnit extends Unit {

	public EnemyUnit(final TextureAtlas spritesAtlas, final float x, final float y) {
		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader2_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader2_2of2");
		animation = new Animation(0.8f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		setDimensions(x, y, frame1.getRegionWidth(), frame1.getRegionHeight());
	}

}
