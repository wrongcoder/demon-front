package com.vdxp.demon_front.core.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vdxp.demon_front.core.Registry;

public class ClothUnit extends YourSideUnit {

	public ClothUnit(final Registry r, final TextureAtlas spritesAtlas, final int xTile, final int yTile) {
		super(r, 60, spritesAtlas, 3, 0.25f, xTile, yTile);
	}

}
