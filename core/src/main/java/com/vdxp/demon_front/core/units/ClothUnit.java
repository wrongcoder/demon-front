package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ClothUnit extends YourSideUnit {

	public ClothUnit(final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(60, spritesAtlas, 3, 0.25f, xTile, yTile);
	}

}
