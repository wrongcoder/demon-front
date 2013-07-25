package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Hero extends Unit {

	public Hero(final TextureAtlas spritesAtlas) {
		super(1000, 1000);

		final TextureAtlas.AtlasRegion frame1 = spritesAtlas.findRegion("invader1_1of2");
		final TextureAtlas.AtlasRegion frame2 = spritesAtlas.findRegion("invader1_2of2");
		animation = new Animation(0.25f, frame1, frame2);
		animation.setPlayMode(Animation.LOOP);

		drawOffsetX = frame1.getRegionWidth() / 2f;
		drawOffsetY = frame1.getRegionHeight() / 2f;
	}

	public float getDrawX() {
		return drawX;
	}

	public float getDrawY() {
		return drawY;
	}

}
