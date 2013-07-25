package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Enemy extends Unit {

	public Enemy(final TextureAtlas spritesAtlas, final float x, final float y) {
		super(x, y);

		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader2_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader2_2of2");
		animation = new Animation(0.8f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		drawOffsetX = frame1.getRegionWidth() / 2f;
		drawOffsetY = frame1.getRegionHeight() / 2f;
	}

}
