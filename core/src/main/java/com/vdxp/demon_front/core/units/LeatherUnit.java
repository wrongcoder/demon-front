package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LeatherUnit extends YourSideUnit {

	public LeatherUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(spritesAtlas, 1, 0.25f, xTile, yTile);
	}

	@Override
	public float getHitPointsFraction() {
		return 0.95f;
	}

}
